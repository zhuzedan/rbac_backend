package org.zzd.service;

import org.zzd.dto.LoginDto;
import org.zzd.entity.SystemUser;
import org.zzd.result.ResponseResult;

/**
 * @author :zzd
 * @apiNote :用户表(SystemUser)表服务接口
 * @date : 2023-03-02 13:26
 */
public interface LoginService {

    SystemUser findUserByUsername(String username);

    ResponseResult login(LoginDto loginDto);

    ResponseResult getInfo();
}
