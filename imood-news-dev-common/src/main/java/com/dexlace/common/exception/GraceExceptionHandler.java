package com.dexlace.common.exception;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/4/30
 */

import com.dexlace.common.result.GraceIMOODJSONResult;
import com.dexlace.common.result.ResponseStatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 统一异常拦截处理
 * 可以针对异常类型进行补货处理，然后返回信息到页面
 */
@ControllerAdvice
public class GraceExceptionHandler {

    final static Logger logger = LoggerFactory.getLogger(GraceExceptionHandler.class);

    /**
     * 只要抛出MyCustomException，就会被此方法拦截到，随后可以自定义处理
     * @param e
     * @return
     */
    @ExceptionHandler(MyCustomException.class)
    @ResponseBody
    public GraceIMOODJSONResult returnMyException(MyCustomException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        return GraceIMOODJSONResult.exception(e.getResponseStatus());
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public GraceIMOODJSONResult returnMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return GraceIMOODJSONResult.errorCustom(ResponseStatusEnum.FILE_MAX_SIZE_ERROR);
    }
}



