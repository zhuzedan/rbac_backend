package org.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zzd.entity.SystemUser;
import org.zzd.mapper.SystemUserMapper;
import org.zzd.pojo.LoginUser;
import org.zzd.service.LoginService;

import java.util.Objects;

/**
 * @author :zzd
 * @apiNote :通过用户名查数据库信息
 * @date : 2023-03-02 10:50
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = loginService.findUserByUsername(username);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(systemUser)){
            throw new RuntimeException("用户名错误");
        }
        //TODO 根据用户查询权限信息 添加到LoginUser中


        //封装成UserDetails对象返回
        return new LoginUser(systemUser);
    }
}

