package org.zzd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zzd.entity.SystemUser;
import org.zzd.result.ResponseResult;
import org.zzd.utils.PageHelper;

import java.util.HashMap;

/**
 * 用户表(SystemUser)表服务接口
 *
 * @author zzd
 * @since 2023-02-27 22:27:37
 */
public interface SystemUserService extends IService<SystemUser> {
    // 分页查询
    ResponseResult<PageHelper<SystemUser>> queryPage(HashMap params);
}

