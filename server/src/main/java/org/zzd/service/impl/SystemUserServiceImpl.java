package org.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.zzd.constant.PageConstant;
import org.zzd.pojo.LoginUser;
import org.zzd.result.ResponseResult;
import org.zzd.service.LoginService;
import org.zzd.utils.PageHelper;
import org.zzd.entity.SystemUser;
import org.zzd.service.SystemUserService;
import org.zzd.mapper.SystemUserMapper;

import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 用户表(SystemUser)表服务实现类
 *
 * @author zzd
 * @since 2023-02-27 22:27:37
 */
@Service("systemUserService")
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService, LoginService {

    @Override
    public ResponseResult<PageHelper<SystemUser>> queryPage(HashMap params) {
        int pageNum = Integer.parseInt((String) params.get(PageConstant.PAGE_NUM));
        int pageSize = Integer.parseInt((String) params.get(PageConstant.PAGE_SIZE));
        Page<SystemUser> page = new Page(pageNum, pageSize);

        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 起始日期
        if(!StringUtils.isBlank((CharSequence) params.get("startCreateTime"))){
            lambdaQueryWrapper.ge(SystemUser::getCreateTime,params.get("startCreateTime"));
        }
        // 结束日期
        if(!StringUtils.isBlank((CharSequence) params.get("endCreateTime"))){
            lambdaQueryWrapper.le(SystemUser::getCreateTime,params.get("endCreateTime"));
        }

        IPage<SystemUser> iPage = this.page(page, lambdaQueryWrapper);
        return ResponseResult.success(PageHelper.restPage(iPage));
    }

    @Override
    public LoginUser findUserByUserName(String username) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<SystemUser>();
        queryWrapper.eq("username",username);
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(username);
        return loginUser;
    }
}

