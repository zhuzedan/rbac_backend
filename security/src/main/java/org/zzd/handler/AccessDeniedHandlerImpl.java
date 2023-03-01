package org.zzd.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.zzd.result.ResponseResult;
import org.zzd.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :zzd
 * @date : 2023-02-19 11:17
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException){
        //给前端ResponseResult 的json
        ResponseResult responseResult = ResponseResult.error(HttpStatus.FORBIDDEN.value(), "您权限不足！");
        String json = JSON.toJSONString(responseResult);
        WebUtils.renderString(response,json);
    }
}