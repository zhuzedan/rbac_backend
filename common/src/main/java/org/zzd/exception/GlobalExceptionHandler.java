package org.zzd.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zzd.result.ResponseResult;

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
     * @param ex: 异常
     * @return org.zzd.result.ResponseResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception ex) throws Exception {
        //针对于捕捉不到AccessDeniedHandler的情况，直接向上抛出
        if ("不允许访问".equals(ex.getMessage())) {
            throw ex;
        }
        return ResponseResult.error();
    }

    /**
     * @apiNote 指定异常
     * @date 2023/3/14 21:53
     * @param ex: 异常
     * @return org.zzd.result.ResponseResult
     */
    @ExceptionHandler(ResponseException.class)
    @ResponseBody
    public ResponseResult error(ResponseException ex) {
        ex.printStackTrace();
        return ResponseResult.error(ex.getCode(), ex.getMessage());
    }

}
