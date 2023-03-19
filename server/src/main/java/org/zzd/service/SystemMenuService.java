package org.zzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zzd.entity.SystemMenu;
import org.zzd.result.ResponseResult;
import org.zzd.utils.PageHelper;

import java.util.HashMap;

/**
 * 菜单表(SystemMenu)表服务接口
 *
 * @author zzd
 * @since 2023-03-19 21:56:53
 */
public interface SystemMenuService extends IService<SystemMenu> {

    ResponseResult findNodes();

}

