package com.dexlace.api.interceptor;

import com.dexlace.common.utils.IPUtil;
import com.dexlace.common.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/15
 */
public class ArticleReadInterceptor implements HandlerInterceptor {


    public static final String ARTICLE_ALREADY_READ = "article_already_read";
    @Autowired
    private RedisOperator redis;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String articleId=request.getParameter("articleId");
        String userIP = IPUtil.getRequestIp(request);
        boolean keyIsExist = redis.keyIsExist(ARTICLE_ALREADY_READ + ":"+articleId+":" + userIP);

        if (keyIsExist) {
            return false;
        }

        return true;
    }
}
