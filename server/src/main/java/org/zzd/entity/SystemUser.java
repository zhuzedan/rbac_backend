package org.zzd.entity;

import java.util.Collection;
import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zzd.pojo.LoginUser;

/**
 * 用户表(SystemUser)表实体类
 *
 * @author zzd
 * @since 2023-02-27 22:27:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_user")
public class SystemUser extends LoginUser implements Serializable {
    //用户id
    @TableId
    private Long id;

    //用户名
    private String username;
    //密码
    private String password;
    //昵称
    private String nickname;
    //邮箱
    private String email;
    //手机
    private String phone;
    //头像地址
    private String headUrl;
    //描述
    private String description;
    //状态1启用0禁用
    private String enabled;
    //性别;1=男,2=女,3=未知
    private Integer gender;
    //生日
    private Date birthday;
    //登录ip
    private String loginIp;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;
    //是否前台app用户
    private Integer isAppuser;

}

