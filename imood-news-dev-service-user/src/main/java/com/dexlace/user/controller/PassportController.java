package com.dexlace.user.controller;

import com.dexlace.api.controller.BaseController;
import com.dexlace.api.controller.user.PassportControllerApi;
import com.dexlace.common.enums.UserStatus;
import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.IPUtil;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.SMSUtils;
import com.dexlace.model.bo.RegisterLoginBO;
import com.dexlace.model.entity.AppUser;
import com.dexlace.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/29
 */
@RestController
public class PassportController extends BaseController implements PassportControllerApi {
    @Autowired
    private SMSUtils smsUtils;
    @Autowired
    private UserService userService;
    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /**
     * 获取验证码
     * @param mobile
     * @param request
     * @return
     */
    @Override
    public GraceIMOODJSONResult getSMSCode(String mobile, HttpServletRequest request) {
        // 根据用户的ip来限制用户在60秒内只能获得一次验证吗。
        String userIP = IPUtil.getRequestIp(request);
        // value随意填写即可，因为仅仅只是用来锁住ip的，60秒过后，又能发送了
        // 也就是说，这个key存在，则当前ip用户无法发送验证码，直到key消失
        // 因为存在该用户的key啊，不会创建新的
        // 但是防刷限制还要去结合拦截器
        redis.setnx60s(MOBILE_SMSCODE + userIP, userIP);

        // 哈哈哈  这里只是做了隐私保护，对于前端演示而说，当前端输入123456时会转换为
        // 实际的电话号码15914463559  这里不做脱敏处理
//        if (mobile.equalsIgnoreCase("123456")) {
//            mobile = "15914463559";
//        }
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
        // 把验证码放入redis，并且有效时间为30分钟
        redis.set(MOBILE_SMSCODE + mobile, random, 30 * 60);
        // 没有钱买短信服务的注释下面的代码  然后去redis中看信息
//        smsUtils.sendSMS(mobile, random);
        return GraceIMOODJSONResult.ok();
    }

    /**
     * 登录
     * @param request
     * @param response
     * @param registerLoginBO
     * @param result
     * @return
     */
    public GraceIMOODJSONResult doLogin(HttpServletRequest request,
                                        HttpServletResponse response,
                                        RegisterLoginBO registerLoginBO,
                                        BindingResult result) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()) {
            Map<String, String> errorMap = getErrors(result);
            return GraceIMOODJSONResult.errorMap(errorMap);
        }

        // 获得前端传来的基本信息
        String smsCode = registerLoginBO.getSmsCode();
        String mobile = registerLoginBO.getMobile();

        // 0. 校验验证码是否匹配
        String redisSMSCode = redis.get(MOBILE_SMSCODE + mobile);
        if (StringUtils.isBlank(redisSMSCode) || !redisSMSCode.equalsIgnoreCase(smsCode)) {
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }

        // 1. 查询数据库，该用户是否注册过
        AppUser user = userService.queryMobileIsExist(mobile);
        if (user != null && user.getActiveStatus() == UserStatus.FROZEN.type) {
            // 2. 如果用户不为空并且状态已经冻结，则直接抛出异常，无法登录
            return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.USER_FROZEN);
        } else if (user == null) {
            // 3. 如果没有注册过，注册用户信息入库，用户状态未激活
            user = userService.createUser(mobile);
        }


        // 注册后或者登录之后的的token设置
        int userActiveStatus = user.getActiveStatus();
// 如果用户状态已经被封了，则不设置会话与cookie信息
        if (userActiveStatus != UserStatus.FROZEN.type) {
            // 3. 用户的分布式会话设置
            String uniqueToken = UUID.randomUUID().toString().trim();
            redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uniqueToken);
            redis.set(REDIS_USER_INFO + ":" + user.getId(), JsonUtils.objectToJson(user));

            // 4. 用户userId与token放入cookie
            setCookie(request, response, "utoken", uniqueToken, COOKIE_MONTH);
            setCookie(request, response, "uid", user.getId(), COOKIE_MONTH);
        }

// 5. 用户登录成功则需要删除已经使用过的短信验证码，该验证码只能匹配使用一次，过后作废
//        redis.del(MOBILE_SMSCODE + mobile);

// 6. 用户状态：未激活或者已激活，返回给前端，用于页面跳转  这里与前端的约定为用户的状态码
        return GraceIMOODJSONResult.ok(userActiveStatus);
    }

    @Override
    public GraceIMOODJSONResult logout(HttpServletRequest request, HttpServletResponse response, String userId) {


            // 1. 清除用户已登录的会话信息
            redis.del(REDIS_USER_TOKEN + ":" + userId);

            // 2. 清除用户userId与token的cookie
            setCookie(request, response, "utoken", "", COOKIE_DELETE);
            setCookie(request, response, "uid", "", COOKIE_DELETE);

            return GraceIMOODJSONResult.ok();


    }


}