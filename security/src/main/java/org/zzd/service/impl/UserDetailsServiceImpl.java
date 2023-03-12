package org.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.zzd.entity.SystemUser;
import org.zzd.exception.ResponseException;
import org.zzd.mapper.SystemMenuMapper;
import org.zzd.mapper.SystemUserMapper;
import org.zzd.pojo.LoginUser;
import org.zzd.utils.ApiUtils;

import java.util.List;

/**
 * @author :zzd
 * @apiNote :通过用户名查数据库信息,用户认证处理器类
 * @date : 2023-03-02 10:50
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<SystemUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemUser::getUsername,username);
        SystemUser systemUser;
        //如果查询不到数据就通过抛出异常来给出提示
        try {
            systemUser= systemUserMapper.selectOne(wrapper);
        }catch (Exception e) {
            throw new ResponseException(400,"用户名或密码错误");
        }
        //根据用户查询权限信息 添加到LoginUser中
        List<String> perms = systemMenuMapper.findSystemMenuListByUserId(systemUser.getId());
        //封装成UserDetails对象返回
        return new LoginUser(systemUser,perms);
    }
}

