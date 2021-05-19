package com.dexlace.common.exception;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */

import com.dexlace.common.result.ResponseStatusEnum;

/**
 * 优雅处理异常，统一封装
 */
public class GraceException {

    public static void display(ResponseStatusEnum responseStatus) {
        throw new MyCustomException(responseStatus);
    }

}


