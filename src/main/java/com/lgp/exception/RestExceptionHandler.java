package com.lgp.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * result统一异常处理
 *
 * @author lgp
 * @create 2018-04-30 20:58
 */

@RestControllerAdvice
public class RestExceptionHandler {

    protected static Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @ExceptionHandler
    public ApiResult runtimeExceptionHandler(Exception e){
        e.printStackTrace();
        return ApiResultGenerator.failResult(e.getMessage(), e);
    }
}
