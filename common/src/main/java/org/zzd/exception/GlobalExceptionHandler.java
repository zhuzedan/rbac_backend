package org.zzd.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zzd.result.ResponseResult;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

/**
 * 全局异常处理类
 *
 * @author :zzd
 * @date : 2023-03-01 16:11
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @apiNote 全局异常
     * @date 2023/3/14 21:53
     * @param e: 异常
     * @return org.zzd.result.ResponseResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e){
        e.printStackTrace();
        System.out.println(e.getMessage());

        return ResponseResult.error();
    }

    /**
     * @apiNote 指定异常
     * @date 2023/3/14 21:53
     * @param e: 异常
     * @return org.zzd.result.ResponseResult
     */
    @ExceptionHandler(ResponseException.class)
    @ResponseBody
    public ResponseResult error(ResponseException e) {
        e.printStackTrace();
        return ResponseResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public void accessDeniedException(AccessDeniedException e) throws IOException {
        throw e;
    }
}
