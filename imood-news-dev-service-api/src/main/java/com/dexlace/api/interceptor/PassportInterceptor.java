package com.dexlace.api.interceptor;

import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.IPUtil;
import com.dexlace.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/29
 */
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redis;

    public static final String MOBILE_SMSCODE = "mobile:smscode:";

    /**
     * 拦截请求，在访问controller调用之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userIP = IPUtil.getRequestIp(request);

        boolean keyIsExist = redis.keyIsExist(MOBILE_SMSCODE + userIP);

        if (keyIsExist) {
            GraceException.display(ResponseStatusEnum.SMS_NEED_WAIT_ERROR);
            return false;
        }

        /**
         * false: 请求被拦截，被驳回，验证出现问题
         * true: 请求在经过验证校验以后，是OK的，是可以放行的
         */
        return true;
    }

    /**
     * 请求访问controller之后，渲染视图之前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 请求访问controller之后，渲染视图之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
