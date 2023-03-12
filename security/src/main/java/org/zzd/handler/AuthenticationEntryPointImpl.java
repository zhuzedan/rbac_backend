package org.zzd.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.zzd.result.ResponseResult;
import org.zzd.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :zzd
 * @apiNote : 没有登录的响应
 * @date : 2023-02-19 11:19
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        //给前端ResponseResult 的json
        ResponseResult responseResult = ResponseResult.error(HttpStatus.UNAUTHORIZED.value(), "登陆认证失败了，请重新登陆！");
        String json = JSON.toJSONString(responseResult);
        WebUtils.renderString(response,json);
    }
}
