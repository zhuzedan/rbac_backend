package org.zzd.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author :zzd
 * @apiNote :创建用户
 * @date : 2023-03-31 9:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "性别;1=男,2=女,3=未知")
    private String gender;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @ApiModelProperty(value = "头像地址")
    private String avatar;

    @ApiModelProperty(value = "是否是移动端用户")
    private Integer ifWxUser;


}
