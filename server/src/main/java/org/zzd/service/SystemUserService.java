package org.zzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.zzd.dto.user.LoginDto;
import org.zzd.dto.user.CreateUserDto;
import org.zzd.dto.user.LoginCaptchaDto;
import org.zzd.entity.SystemUser;
import org.zzd.result.ResponseResult;
import org.zzd.utils.PageHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 用户表(SystemUser)表服务接口
 *
 * @author zzd
 * @since 2023-03-02 13:53:39
 */
public interface SystemUserService extends IService<SystemUser> {
    ResponseResult login(LoginDto loginDto);

    ResponseResult loginCaptcha(LoginCaptchaDto loginCaptchaDto, HttpServletRequest request);

    ResponseResult getInfo();

    // 分页查询
    ResponseResult<PageHelper<SystemUser>> queryPage(HashMap params);

    UserDetails loadUserByUsername(String username);

    ResponseResult refreshToken(HttpServletRequest request);

    ResponseResult insertSystemUser(CreateUserDto createUserDto);
}

