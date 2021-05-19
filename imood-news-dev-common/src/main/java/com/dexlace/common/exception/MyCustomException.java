package com.dexlace.common.exception;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */


import com.dexlace.common.result.ResponseStatusEnum;

/**
 * 自定义异常
 * 目的：1. 统一异常处理和管理
 *      2. service与controller错误解耦，不会被service返回的类型而限制
 *
 * RuntimeException: 没有侵入性，如果继承Exception，则代码中需要使用try/catch
 */
public class MyCustomException extends RuntimeException {

    private ResponseStatusEnum responseStatus;

    public MyCustomException(ResponseStatusEnum responseStatus) {
        super("异常状态码: " +  responseStatus.status() + "; 异常信息: " + responseStatus.msg());
        this.responseStatus = responseStatus;
    }

    public ResponseStatusEnum getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatusEnum responseStatus) {
        this.responseStatus = responseStatus;
    }
}

