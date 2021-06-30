package com.dexlace.gateway.filter;

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import com.dexlace.common.utils.IPUtil;
import com.dexlace.common.utils.JsonUtils;
import com.dexlace.common.utils.RedisOperator;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/13
 */
public class BlackIpFilter extends ZuulFilter {

    /**
     * ip连续请求的次数
     */
    @Value("${blackIp.continueCounts}")
    private Integer continueCounts;

    /**
     * ip判断的时间间隔，单位：秒
     */
    @Value("${blackIp.timeInterval}")
    private Integer timeInterval;

    /**
     * 限制的时间，单位：秒
     */
    @Value("${blackIp.limitTimes}")
    private Integer limitTimes;




    @Autowired
    private RedisOperator redis;



    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("执行【IP黑名单】Zuul过滤器...");

        // 获得上下文对象requestContext
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 获得ip
        String ip = IPUtil.getRequestIp(request);

        /**
         * 需求：
         * 判断ip在10秒内请求的次数是否超过10次，
         * 如果超过，则限制访问15秒，15秒过后再放行
         */
        final String ipRedisKey = "zuul-ip:" + ip;
        final String ipRedisLimitKey = "zuul-ip-limit:" + ip;

        // 获得剩余的限制时间
        long limitLeftTime = redis.ttl(ipRedisLimitKey);
        // 如果剩余时间还存在，说明这个ip不能访问，继续等待
        if (limitLeftTime > 0) {
            stopRequest(requestContext);
            return null;
        }

        // 在redis中累加ip的请求访问次数
        long requestCounts = redis.increment(ipRedisKey, 1);

        // 从0开始计算请求次数，初期访问为1，则设置过期时间，也就是连续请求的间隔时间
        if (requestCounts == 1) {
            redis.expire(ipRedisKey, timeInterval);
        }

        // 如果还能取得到请求次数，说明用户连续请求的次数落在10秒内
        // 一旦请求次数超过了连续访问的次数，则需要限制这个ip了
        if (requestCounts > continueCounts) {
            // 限制ip访问一段时间
            redis.set(ipRedisLimitKey, ipRedisLimitKey, limitTimes);

            stopRequest(requestContext);
        }

        return null;
    }

    private void stopRequest(RequestContext requestContext){
        // 停止继续向下路由，禁止请求通信
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(200);
        String result = JsonUtils.objectToJson(
                GraceIMOODJSONResult.errorCustom(
                        ResponseStatusEnum.SYSTEM_ERROR_BLACK_IP));
        requestContext.setResponseBody(result);
        requestContext.getResponse().setCharacterEncoding("utf-8");
        requestContext.getResponse().setContentType(MediaType.APPLICATION_JSON_VALUE);
    }

}
