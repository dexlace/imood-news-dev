package com.dexlace.admin.controller;

import com.dexlace.admin.service.AdminUserService;
import com.dexlace.api.controller.admin.AdminMngControllerApi;
import com.dexlace.api.controller.BaseController;
import com.dexlace.common.enums.FaceVerifyType;
import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.FaceVerifyUtils;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.bo.AdminLoginBO;
import com.dexlace.model.bo.NewAdminBO;
import com.dexlace.model.entity.AdminUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/4
 */
@RestController
public class AdminMngController extends BaseController implements AdminMngControllerApi {
    public static final Logger logger = LoggerFactory.getLogger(AdminMngController.class);

    @Autowired
    private AdminUserService adminUserService;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FaceVerifyUtils faceVerifyUtils;


    @Override
    public GraceIMOODJSONResult adminLogin(AdminLoginBO adminLoginBO,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

        // 查询用户是否存在
        AdminUser admin = adminUserService.queryAdminByUsername(adminLoginBO.getUsername());
        if (admin == null) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }

        // 判断
        boolean isPwdMatch = BCrypt.checkpw(adminLoginBO.getPassword(), admin.getPassword());
        if (isPwdMatch) {
            doLoginSettings(admin, request, response);
            return GraceIMOODJSONResult.ok();
        } else {
            // 密码不匹配
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_NOT_EXIT_ERROR);
        }
    }

    private void doLoginSettings(AdminUser admin,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // 生成token，保存到redis中
        String uniqueToken = UUID.randomUUID().toString().trim();
        redis.set(REDIS_ADMIN_TOKEN + ":" + admin.getId(), uniqueToken);
        // token与用户信息写入到cookie
        setCookie(request, response, "atoken", uniqueToken, COOKIE_MONTH);
        setCookie(request, response, "aid", admin.getId(), COOKIE_MONTH);
        setCookie(request, response, "aname", admin.getAdminName(), COOKIE_MONTH);
    }


    @Override
    public GraceIMOODJSONResult adminIsExist(String username) {
        // 验证管理人用户名必须唯一
        checkAdminExist(username);
        return GraceIMOODJSONResult.ok();
    }

    @Override
    public GraceIMOODJSONResult addNewAdmin(HttpServletRequest request, HttpServletResponse response, NewAdminBO newAdminBO) {


        // 0. TODO 验证BO中的用户名和密码不为空


        // 1. base64不为空，代表人脸登录，否则密码不能为空
        if (StringUtils.isBlank(newAdminBO.getImg64())) {
            if (StringUtils.isBlank(newAdminBO.getPassword()) || StringUtils.isBlank(newAdminBO.getConfirmPassword())) {
                return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
            }
        }

        // 2. 密码不为空，必须判断两次输入一致
        if (!StringUtils.isBlank(newAdminBO.getPassword())) {
            if (!newAdminBO.getPassword().equalsIgnoreCase(newAdminBO.getConfirmPassword())) {
                return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_PASSWORD_ERROR);
            }
        }

        // 3. 验证管理人用户名必须唯一
        checkAdminExist(newAdminBO.getUsername());

        // 4. 新增管理员
        adminUserService.createAdminUser(newAdminBO);

        return GraceIMOODJSONResult.ok();


    }


    private void checkAdminExist(String userName) {
        // 验证管理人用户名必须唯一
        AdminUser admin = adminUserService.queryAdminByUsername(userName);
        if (admin != null) {
            GraceException.display(ResponseStatusEnum.ADMIN_USERNAME_EXIST_ERROR);
        }
    }


    @Override
    public GraceIMOODJSONResult getAdminList(Integer page, Integer pageSize) {

        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = adminUserService.queryAdminList(page, pageSize);
        return GraceIMOODJSONResult.ok(gridResult);
    }


    @Override
    public GraceIMOODJSONResult adminLogout(String adminId, HttpServletRequest request, HttpServletResponse response) {

        redis.del(REDIS_ADMIN_TOKEN + ":" + adminId);
        deleteCookie(request, response, "atoken");
        deleteCookie(request, response, "aid");
        deleteCookie(request, response, "aname");

        return GraceIMOODJSONResult.ok();
    }

    @Override
    public GraceIMOODJSONResult adminFaceLogin(AdminLoginBO adminLoginBO, HttpServletRequest request, HttpServletResponse response) {


        // 0. 判断用户名和人脸信息不能为空
        // 这里的人脸登录不是用1：N的格式，而是1:1的对比模式，所以需要传用户名
        if (StringUtils.isBlank(adminLoginBO.getUsername())) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_USERNAME_NULL_ERROR);
        }
        // 前端会传来照片的base64消息
        String tempFace64 = adminLoginBO.getImg64();
        if (StringUtils.isBlank(tempFace64)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_FACE_NULL_ERROR);
        }

        // 1. 从数据库中查询admin信息，获得人脸faceId
        AdminUser admin = adminUserService.queryAdminByUsername(adminLoginBO.getUsername());
        String adminFaceId = admin.getFaceId();

        // 2. 请求文件服务，获取人脸的base64信息
        // 需要服务调用

        String fileServerUrlExecute = "http://files.imoocnews.com:8004/fs/readFace64InGridFS?faceId=" + adminFaceId;
        ResponseEntity<GraceIMOODJSONResult> resultEntity = restTemplate.getForEntity(fileServerUrlExecute, GraceIMOODJSONResult.class);
        GraceIMOODJSONResult graceJSONResult = resultEntity.getBody();
        String base64DB = (String) graceJSONResult.getData();
        logger.info("restTemplate远程调用获得的内容为：{}", base64DB);


        // 3. 调用阿里人脸识别只能AI接口，对比人脸实现登录
        boolean result = faceVerifyUtils.faceVerify(FaceVerifyType.BASE64.type,
                tempFace64,
                base64DB,
                60);

        if (!result) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.ADMIN_FACE_LOGIN_ERROR);
        }

// 4. 设置管理员信息到redis与cookie
        doLoginSettings(admin, request, response);


        return GraceIMOODJSONResult.ok();
    }


}
