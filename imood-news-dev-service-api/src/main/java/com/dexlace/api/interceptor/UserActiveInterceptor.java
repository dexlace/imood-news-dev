package com.dexlace.api.interceptor;

import com.dexlace.common.enums.UserStatus;
import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.RedisOperator;
import com.dexlace.model.entity.AppUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/3
 */
/**
 * 用户激活状态检查拦截器
 * 发文章，修改文章等
 * 发评论，查看评论等
 * 查看我的粉丝等，这些媒体中心的功能必须用户激活后，才能进行，
 * 否则提示用户前往[账号设置]去修改信息
 */
public class UserActiveInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redis;

    public static final String REDIS_USER_INFO = "redis_user_info";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        // 1. 偷懒，可以从userController中拷贝过来
        String userJson = redis.get(REDIS_USER_INFO + ":" + userId);
        AppUser user = null;
        if (StringUtils.isNotBlank(userJson)) {
            user = JsonUtils.jsonToPojo(userJson, AppUser.class);
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
            return false;
        }

        // 如果不是激活状态则不能执行后续操作
        if (user.getActiveStatus() == null || user.getActiveStatus() != UserStatus.ACTIVE.type) {
            GraceException.display(ResponseStatusEnum.USER_INACTIVE_ERROR);
            return false;
        }

        return true;
    }
}
