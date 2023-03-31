package org.zzd.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (WechatUser)表实体类
 *
 * @author zzd
 * @since 2023-03-30 09:12:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_wechat_user")
public class WechatUser implements Serializable {
    //主键id
    @TableId
    private Long id;
    //微信昵称
    private String nickname;
    //微信openid
    private String openId;
    //微信sessionKey
    private String sessionKey;
    //生成自定义登录状态
    private String skey;
    //用户id
    private Long userId;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标识
    private Integer isDeleted;

}

