package org.zzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zzd.entity.WechatUser;
import org.zzd.result.ResponseResult;

/**
 * @author :zzd
 * @apiNote :微信服务
 * @date : 2023-03-29 22:44
 */
public interface WeChatService  extends IService<WechatUser> {
    ResponseResult wxLogin(String code);
}
