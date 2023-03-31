package org.zzd.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzd
 * @apiNote 登录带验证码版
 * @date 2023/3/31 15:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "登录对象带验证码")
public class LoginCaptchaDto {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码", required = true)
    private String captcha;
}
