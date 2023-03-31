package org.zzd.controller;

import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zzd.annotation.Log;
import org.zzd.annotation.LoginLog;
import org.zzd.bean.LoginCodeEnum;
import org.zzd.bean.LoginProperties;
import org.zzd.dto.user.LoginDto;
import org.zzd.dto.user.LoginCaptchaDto;
import org.zzd.enums.BusinessType;
import org.zzd.enums.OperatorType;
import org.zzd.result.ResponseResult;
import org.zzd.service.SystemUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @Resource
    private LoginProperties loginProperties;

    // 登录
    @LoginLog
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginDto loginDto) {
        return systemUserService.login(loginDto);
    }

    @LoginLog
    @ApiOperation("用户登录（captcha）")
    @PostMapping("/loginCaptcha")
    public ResponseResult loginAndCaptcha(@RequestBody LoginCaptchaDto loginCaptchaDto,HttpServletRequest request) {
        return systemUserService.loginCaptcha(loginCaptchaDto,request);
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/captcha")
    public Object getCode(HttpServletRequest request, HttpServletResponse response) {
        Captcha captcha = loginProperties.getCaptcha();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        System.out.println("验证码text为"+captchaValue);
        // 保存到session
        request.getSession().setAttribute("captcha", captchaValue);
        // 验证码信息
        return captcha.toBase64();
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
