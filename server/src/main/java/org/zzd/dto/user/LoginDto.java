package org.zzd.dto.user;

/**
 * @author :zzd
 * @apiNote :登录对象
 * @date : 2023-03-12 19:55
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :zzd
 * @apiNote :登录dto
 * @date : 2023-03-02 11:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "登录对象")
public class LoginDto implements Serializable {
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
