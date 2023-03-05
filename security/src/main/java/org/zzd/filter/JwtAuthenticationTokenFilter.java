package org.zzd.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zzd.constant.SecurityConstants;
import org.zzd.entity.SystemUser;
import org.zzd.mapper.SystemUserMapper;
import org.zzd.pojo.LoginUser;
import org.zzd.utils.JwtTokenUtil;
import org.zzd.utils.ThreadLocalUtil;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author :zzd
 * @apiNote :OncePerRequestFilter只走一次，在请求前
 * @date : 2023-03-02 11:26
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1获取token  header的token
        String token = null;
        String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            token =  bearerToken.replace(SecurityConstants.TOKEN_PREFIX,"");
        }
        if (!StringUtils.hasText(bearerToken)) {
            //放行，让后面的过滤器执行
            filterChain.doFilter(request, response);
            return;
        }
        //2解析token
        String username;
        try {
            username = jwtTokenUtil.getUserNameFromToken(token);
        }catch (Exception e) {
            throw new RuntimeException("token不合法！");
        }

        //3获取username
        SystemUser systemUser = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("username", username));
        String name = systemUser.getUsername();
        if (Objects.isNull(systemUser)) {
            throw new RuntimeException("当前用户未登录！");
        }
        //获取用户权限信息
        LoginUser loginUser = new LoginUser(systemUser);
        ThreadLocalUtil.setUsername(loginUser.getUsername());
        //4封装Authentication
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(name, null, null);

        //5存入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //放行，让后面的过滤器执行
        filterChain.doFilter(request, response);
    }
}