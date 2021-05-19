package com.dexlace.api.interceptor;

import com.dexlace.common.exception.GraceException;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/5/3
 */
public class BaseInterceptor {

    final static Logger logger= LoggerFactory.getLogger(BaseInterceptor.class);
    @Autowired
    private RedisOperator redis;

    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final String REDIS_ADMIN_TOKEN = "redis_admin_token";

    public boolean verifyUserIdToken(String id, String token, String redisKeyPrefix) {
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(token)) {
            String uniqueToken = redis.get(redisKeyPrefix + ":" + id);
            if (StringUtils.isBlank(uniqueToken)) {
                // 这里双重检查，token过期判断
                logger.info("uniqueToken: "+uniqueToken);
                GraceException.display(ResponseStatusEnum.UN_LOGIN);
                return false;
            } else {
                if (!uniqueToken.equals(token)) {
                    // 验证token对不对
                    GraceException.display(ResponseStatusEnum.TICKET_INVALID);
                    return false;
                }
            }
        } else {
            GraceException.display(ResponseStatusEnum.UN_LOGIN);
            return false;
        }

        /**
         * false: 请求被拦截，被驳回，验证出现问题
         * true: 请求在经过验证校验以后，是OK的，是可以放行的
         */

        // 这里双重检查，token过期判断
        logger.info("token验证成功: "+token);
        return true;
    }
}

