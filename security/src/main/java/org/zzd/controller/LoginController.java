package org.zzd.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zzd.annotation.Log;
import org.zzd.dto.LoginDto;
import org.zzd.enums.BusinessType;
import org.zzd.enums.OperatorType;
import org.zzd.result.ResponseResult;
import org.zzd.service.LoginService;

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
    LoginService loginService;
    // 登录
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        return loginService.login(loginDto);
    }

    //获取用户信息
    @Log(title = "获取当前登录用户信息", businessType = BusinessType.SELECT, operatorType = OperatorType.MANAGE)
    @ApiOperation("用户信息")
    @GetMapping("/info")
    public ResponseResult getInfo() {
        return loginService.getInfo();
    }

    //用户退出登录
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ResponseResult logout() {
        return ResponseResult.success();
    }

}
