package org.zzd.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zzd.exception.ResponseException;
import org.zzd.pojo.LoginUser;
import org.zzd.result.ResultCodeEnum;

import javax.annotation.Resource;

/**
 * @author :zzd
 * @date : 2023-02-26 16:42
 */
@Service
public class LoginServiceImpl implements UserDetailsService {
    @Resource
    private LoginService loginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = loginService.findUserByUserName(username);
        if (StringUtils.isEmpty(username)) {
            throw new ResponseException(ResultCodeEnum.NO_USERNAME.getCode(), ResultCodeEnum.NO_USERNAME.getMessage());

        }
        return loginUser;
    }
}
