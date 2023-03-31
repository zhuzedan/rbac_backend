package org.zzd.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zzd.result.ResponseResult;
import org.zzd.service.WeChatService;

/**
 * @author :zzd
 * @apiNote :微信接口
 * @date : 2023-03-29 22:39
 */
@RestController
@RequestMapping("/api/wechat")
@Api(tags = "微信服务")
public class WeChatController {
    @Autowired
    private WeChatService weChatService;

    @GetMapping("/login")
    public ResponseResult wxLogin(String code) {
        return weChatService.wxLogin(code);
    }
}
