package org.zzd.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author :zzd
 * @apiNote :用户表实体类
 * @date : 2023-03-02 10:31
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_user")
public class SystemUser implements Serializable {
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
    //描述
    private String description;
    //状态1启用0禁用
    private Integer status;
    //性别;1=男,2=女,3=未知
    private String gender;
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
    //真实姓名
    private String realname;
    //头像地址
    private String avatar;
    //用户类型后台前台
    private Integer userType;
    //创建人
    private String createBy;
    //是否是移动端用户
    private Integer ifWxUser;

}
