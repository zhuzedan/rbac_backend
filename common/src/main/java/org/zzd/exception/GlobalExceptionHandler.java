package org.zzd.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zzd.result.ResponseResult;

/**
 * 全局异常处理类
 * @author :zzd
 * @date : 2023-03-01 16:11
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e){
        e.printStackTrace();
        return ResponseResult.error();
    }

    /**
     * 指定异常
     * @param e
     * @return
     */
    @ExceptionHandler(ResponseException.class)
    @ResponseBody
    public ResponseResult error(ResponseException e){
        e.printStackTrace();
        return ResponseResult.error(e.getCode(),e.getMessage());
    }

}
