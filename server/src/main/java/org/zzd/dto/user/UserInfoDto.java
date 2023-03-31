package org.zzd.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author :zzd
 * @apiNote :用户登录信息
 * @date : 2023-03-15 10:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户信息对象")
public class UserInfoDto implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户名昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "介绍")
    private String description;

    @ApiModelProperty(value = "权限")
    private Object[] perms;

}
