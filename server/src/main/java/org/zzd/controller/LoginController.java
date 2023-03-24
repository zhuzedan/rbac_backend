package org.zzd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zzd.annotation.Log;
import org.zzd.annotation.LoginLog;
import org.zzd.dto.LoginDto;
import org.zzd.enums.BusinessType;
import org.zzd.enums.OperatorType;
import org.zzd.result.ResponseResult;
import org.zzd.service.SystemUserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :zzd
 * @apiNote :用户登录接口
 * @date : 2023-03-02 13:41
 */
@RestController
@Api(tags = "用户登录")
@RequestMapping("/api/systemUser")
public class LoginController {
    @Autowired
    SystemUserService systemUserService;
    // 登录
    @LoginLog
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        return systemUserService.login(loginDto);
    }

    //获取用户信息
    @Log(title = "获取当前登录用户信息", businessType = BusinessType.SELECT, operatorType = OperatorType.MANAGE)
    @ApiOperation("用户信息")
    @GetMapping("/info")
    public ResponseResult getInfo() {
        return systemUserService.getInfo();
    }

    //用户退出登录
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ResponseResult logout() {
        return ResponseResult.success();
    }

    @ApiOperation("刷新token")
    @PostMapping("/refreshToken")
    public ResponseResult refreshToken(HttpServletRequest request) {
        return systemUserService.refreshToken(request);
    }

}
