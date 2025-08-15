package com.udan.ubsp.common.exception;

import com.udan.ubsp.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description 全局异常捕获
 * @Author TOM FORD
 * @Date 2025-07-12 16:13:39
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<Void> handle(Exception e) {
        log.error(e.getMessage());
        return Result.fail();
    }

    @ExceptionHandler(UBSPException.class)
    public Result<Void> handle(UBSPException e) {
        String message = e.getMessage();
        Integer code = e.getCode();
        log.error(message);
        return Result.fail(code, message);
    }
}
