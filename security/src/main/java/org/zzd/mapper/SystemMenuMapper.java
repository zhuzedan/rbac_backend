package org.zzd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.zzd.entity.SystemMenu;

import java.util.List;

/**
 * 菜单表(SystemMenu)表数据库访问层
 *
 * @author zzd
 * @since 2023-03-04 15:13:58
 */
public interface SystemMenuMapper extends BaseMapper<SystemMenu> {


    List<String> findSystemMenuListByUserId(Long userId);
}

