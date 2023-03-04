package org.zzd.entity;

import java.util.ArrayList;
import java.util.Date;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 菜单表(SystemMenu)表实体类
 *
 * @author zzd
 * @since 2023-03-04 15:13:58
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_system_menu")
public class SystemMenu implements Serializable {
    //编号
    @TableId
    private Long id;

    //所属上级
    private Long parentId;
    //名称
    private String name;
    //类型(0:目录,1:菜单,2:按钮)
    private Integer type;
    //路由地址
    private String path;
    //组件路径
    private String component;
    //权限标识
    private String perms;
    //图标
    private String icon;
    //排序
    private Integer sortValue;
    //状态(0:禁止,1:正常)
    private Integer status;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //删除标记（0:可用 1:已删除）
    private Integer isDeleted;

    //子菜单列表
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private List<SystemMenu> children = new ArrayList<SystemMenu>();

    //用于在前端判断是目录or菜单or按钮
    @TableField(exist = false)
    private String value;

    //是否展开
    @TableField(exist = false)
    private boolean open;

}

