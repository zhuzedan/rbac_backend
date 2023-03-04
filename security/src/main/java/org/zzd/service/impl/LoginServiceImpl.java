package org.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.zzd.utils.AuthUtils;
import org.zzd.utils.JwtTokenUtil;

import javax.annotation.Resource;
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
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private AuthUtils authUtils;

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
        String username = loginUser.getUser().getUsername();
        String token = jwtTokenUtil.generateToken(loginUser);
        Map<String,String> map = new HashMap();
        map.put("token",token);
        map.put("tokenHead", SecurityConstants.TOKEN_PREFIX);

        return ResponseResult.success("登录成功",map);
    }

    @Override
    public ResponseResult getInfo() {
        String username = authUtils.getCurrentUsername();
        SystemUser systemUser = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("username", username));
        systemUser.setPassword(null);
        Map<String,Object> map = new HashMap<>();
        map.put("userInfo",systemUser);
        return ResponseResult.success(map);
    }


}
