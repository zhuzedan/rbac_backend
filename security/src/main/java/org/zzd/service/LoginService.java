package org.zzd.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.zzd.pojo.LoginUser;

/**
 * @author :zzd
 * @date : 2023-02-26 21:20
 */
public interface LoginService {
    LoginUser findUserByUserName(String username);
}
