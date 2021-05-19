package com.dexlace.api.controller;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.PagedGridResult;
import com.dexlace.common.utils.RedisOperator;
import com.dexlace.model.entity.AdminUser;
import com.dexlace.model.vo.AppUserVO;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/29
 */
public class BaseController {
    public static final String MOBILE_SMSCODE = "mobile:smscode:";
    public static final Integer COOKIE_MONTH = 30*24*60*60;
    public static final String REDIS_USER_INFO = "redis_user_info";
    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token";
    public static final String ARTICLE_ALREADY_READ = "article_already_read";
    public static final String REDIS_ARTICLE_READ_COUNTS = "redis_article_read_counts";
    public static final Integer COOKIE_DELETE = 0;

    public static final Integer COMMON_START_PAGE = 1;
    public static final Integer COMMON_PAGE_SIZE = 5;



    @Value("${website.domain-name}")
    public String DOMAIN_NAME;

    @Autowired
    public RedisOperator redis;


    @Autowired
    private RestTemplate restTemplate;
    /**
     * 验证beanBO中的字段错误信息
     * @param result
     * @return
     */
    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();
            map.put(errorField, errorMsg);
        }
        return map;
    }




    public void setCookie(HttpServletRequest request,
                          HttpServletResponse response,
                          String cookieName,
                          String cookieValue,
                          Integer MaxAge) {
        try {
            cookieValue = URLEncoder.encode(cookieValue, "utf-8");
            setCookieValue(request, response, cookieName, cookieValue, MaxAge);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setCookieValue(HttpServletRequest request,
                               HttpServletResponse response,
                               String cookieName,
                               String cookieValue,
                               Integer MaxAge) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(MaxAge);
        cookie.setDomain(DOMAIN_NAME);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    public void deleteCookie(HttpServletRequest request,
                             HttpServletResponse response,
                             String cookieName) {
        try {
            String deleteValue = URLEncoder.encode("", "utf-8");
            setCookieValue(request, response, cookieName, deleteValue, COOKIE_DELETE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public Integer getCountsFromRedis(String key) {
        String countsStr = redis.get(key);
        if (StringUtils.isBlank(countsStr)) {
            countsStr = "0";
        }
        return Integer.valueOf(countsStr);
    }



    public List<AppUserVO> remoteCalBasicUserInfo(Set publishUserIdSet) {
        //发起restTemplate请求查询用户列表
        String userServerUrlExecute
                = "http://user.imoocnews.com:8003/user/queryByIds?userIds=" + JsonUtils.objectToJson(publishUserIdSet);
        ResponseEntity<GraceIMOODJSONResult> responseEntity
                = restTemplate.getForEntity(userServerUrlExecute, GraceIMOODJSONResult.class);
        GraceIMOODJSONResult bodyResult = responseEntity.getBody();
        List<AppUserVO> publishUserList = null;
        if (bodyResult.getStatus() == 200) {
            String userJson = JsonUtils.objectToJson(bodyResult.getData());
            publishUserList = JsonUtils.jsonToList(userJson, AppUserVO.class);
        }
        return publishUserList;
    }








}
