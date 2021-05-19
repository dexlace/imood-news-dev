package com.dexlace.api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/3
 */
public class UserTokenInterceptor extends BaseInterceptor implements HandlerInterceptor{
    final static Logger logger= LoggerFactory.getLogger(UserTokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        logger.info("userId: "+userId+";"+"userToken: "+userToken);

        // 判断是否放行
        boolean run = verifyUserIdToken(userId, userToken, REDIS_USER_TOKEN);
        logger.info(String.valueOf(run));
        return run;
    }


}
