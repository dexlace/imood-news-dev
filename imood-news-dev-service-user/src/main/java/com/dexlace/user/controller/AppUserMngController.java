package com.dexlace.user.controller;

import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.user.AppUserMngControllerApi;
import com.dexlace.api.controller.user.UserControllerApi;
import com.dexlace.common.enums.UserStatus;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.model.bo.UpdateUserInfoBO;
import com.dexlace.model.entity.AppUser;
import com.dexlace.model.vo.AppUserVO;
import com.dexlace.model.vo.UserAccountInfoVO;
import com.dexlace.user.service.AppUserMngService;
import com.dexlace.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @Author: xiaogongbing
 * @Description:  用户管理
 * @Date: 2021/4/30
 */
@RestController
public class AppUserMngController extends BaseController implements AppUserMngControllerApi {

    final static Logger logger = LoggerFactory.getLogger(AppUserMngController.class);
    @Autowired
    public AppUserMngService appUserMngService;
    @Autowired
    public UserService userService;

    @Override
    public GraceIMOODJSONResult queryAll(String nickname, Integer status,
                                    Date startDate, Date endDate,
                                    Integer page, Integer pageSize) {

        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult gridResult = appUserMngService.queryAllUserList(nickname, status, startDate, endDate, page, pageSize);
        return GraceIMOODJSONResult.ok(gridResult);

    }



    @Override
    public GraceIMOODJSONResult userDetail(String userId) {
        AppUser user = userService.getUser(userId);
        return GraceIMOODJSONResult.ok(user);
    }


    @Override
    public GraceIMOODJSONResult freezeUserOrNot(String userId, Integer doStatus) {

        if (!UserStatus.isUserStatusValid(doStatus)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.USER_STATUS_ERROR);
        }

        appUserMngService.freezeUserOrNot(userId, doStatus);
        // 刷新用户状态：
        // 方式一：删除用户会话，以保障用户需要重新登录来刷新他的状态，
        // 方式二：查询最新用户信息，重新放入redis，这种方式不太好，因为会话信息应该要让用户自己去创建的，
        //        admin最好不要干预，这也是为什么很多网站的客服大多都会让你重新登录系统再去其他的操作，目的就是重置会话信息
        redis.del(REDIS_USER_INFO + ":" + userId);
        return GraceIMOODJSONResult.ok();
    }






}
