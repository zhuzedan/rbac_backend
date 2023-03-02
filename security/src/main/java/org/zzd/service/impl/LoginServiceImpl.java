package org.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.zzd.constant.SecurityConstants;
import org.zzd.dto.LoginDto;
import org.zzd.entity.SystemUser;
import org.zzd.exception.ResponseException;
import org.zzd.mapper.SystemUserMapper;
import org.zzd.pojo.LoginUser;
import org.zzd.result.ResponseResult;
import org.zzd.result.ResultCodeEnum;
import org.zzd.service.LoginService;
import org.zzd.utils.JwtTokenUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author :zzd
 * @apiNote :登录服务实现类
 * @date : 2023-03-02 13:32
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    SystemUserMapper systemUserMapper;

    @Override
    public ResponseResult login(LoginDto loginDto) {
        //使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //校验失败了
        if(Objects.isNull(authenticate)){
            throw new ResponseException(ResultCodeEnum.LOGIN_ERROR.getCode(), ResultCodeEnum.LOGIN_ERROR.getMessage());
        }
        //生成自己jwt给前端
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String id = loginUser.getUser().getId().toString();
        String jwt = JwtTokenUtil.createJWT(id);
        Map<String,String> map = new HashMap();
        map.put("token",jwt);
        map.put("tokenHead", SecurityConstants.TOKEN_PREFIX);

        return ResponseResult.success("登录成功",map);
    }

    @Override
    public ResponseResult getInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SystemUser loginUser = (SystemUser) authentication.getPrincipal();
        SystemUser systemUser = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("id", loginUser.getId()));
        systemUser.setPassword(null);
        Map<String,Object> map = new HashMap<>();
        map.put("userInfo",systemUser);
        return ResponseResult.success(map);
    }
}
