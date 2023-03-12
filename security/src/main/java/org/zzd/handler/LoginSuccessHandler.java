package org.zzd.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.zzd.constant.SecurityConstants;
import org.zzd.entity.SystemUser;
import org.zzd.pojo.LoginUser;
import org.zzd.utils.JwtTokenUtil;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author :zzd
 * @apiNote :认证成功处理器类
 * @date : 2023-03-08 13:08
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        SystemUser systemUser = loginUser.getUser();

        String token = jwtTokenUtil.generateToken(loginUser);
        Map<String,String> map = new HashMap();
        map.put("token",token);
        map.put("tokenHead", SecurityConstants.TOKEN_PREFIX);

        String result = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(result.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

    }
}
