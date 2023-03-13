package org.zzd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zzd.constant.PageConstant;
import org.zzd.constant.SecurityConstants;
import org.zzd.dto.LoginDto;
import org.zzd.entity.SystemMenu;
import org.zzd.exception.ResponseException;
import org.zzd.mapper.SystemMenuMapper;
import org.zzd.mapper.SystemUserMapper;
import org.zzd.pojo.SecuritySystemUser;
import org.zzd.result.ResponseResult;
import org.zzd.result.ResultCodeEnum;
import org.zzd.utils.AuthUtils;
import org.zzd.utils.JwtTokenUtil;
import org.zzd.utils.PageHelper;
import org.zzd.entity.SystemUser;
import org.zzd.service.SystemUserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.NameNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(SystemUser)表服务实现类
 *
 * @author zzd
 * @since 2023-03-02 13:53:39
 */
@Service("systemUserService")
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {
    @Resource
    private AuthUtils authUtils;

    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemMenuMapper systemMenuMapper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;


    /**
     * @apiNote 后台用户登录
     * @date 2023/3/12 21:21
     * @param loginDto: 登录参数
     * @return org.zzd.result.ResponseResult
     */
    @Override
    public ResponseResult login(LoginDto loginDto) throws ResponseException{
        SystemUser login = doLogin(loginDto.getUsername(),loginDto.getPassword());

        String token = jwtTokenUtil.generateToken(login.getUsername());
        Map<String,String> map = new HashMap();
        map.put("token",token);
        map.put("tokenHead", SecurityConstants.TOKEN_PREFIX);

        return ResponseResult.success("登录成功",map);
    }

    public SystemUser doLogin(String username, String password) {

        SystemUser systemUser;
        UserDetails userDetails;
        try {
            userDetails = loadUserByUsername(username);
            systemUser = ((SecuritySystemUser)userDetails).getSystemUser();
        } catch (Exception e) {
            throw new ResponseException(ResultCodeEnum.LOGIN_ERROR.getCode(), ResultCodeEnum.LOGIN_ERROR.getMessage());
        }
        if (!passwordEncoder.matches(password,systemUser.getPassword())) {
            throw new ResponseException(ResultCodeEnum.PASSWORD_ERROR.getCode(),ResultCodeEnum.PASSWORD_ERROR.getMessage());
        }
        if(!userDetails.isEnabled()){
            throw new ResponseException(ResultCodeEnum.ACCOUNT_STOP.getCode(),ResultCodeEnum.ACCOUNT_STOP.getMessage());
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return systemUser;
    }

    /**
     * @apiNote 获取用户信息
     * @date 2023/3/12 21:21
     * @return org.zzd.result.ResponseResult
     */
    @Override
    public ResponseResult getInfo() {
        String username = AuthUtils.getCurrentUsername();
        SystemUser systemUser = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("username", username));
        systemUser.setPassword(null);
        Map<String,Object> map = new HashMap<>();
        map.put("userInfo",systemUser);
        return ResponseResult.success(map);
    }

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

    /**
     * @apiNote 获得当前用户
     * @date 2023/3/12 19:03
     * @return org.zzd.entity.SystemUser
     */
    public SystemUser getSystemUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuritySystemUser systemUser = (SecuritySystemUser) authentication.getPrincipal();
        return systemUser.getSystemUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        SystemUser systemUser = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("username", username));

        //获取菜单列表
        List<SystemMenu> menuList = systemMenuMapper.getSystemUserMenuList(systemUser.getId());
        //获取权限集合
        List<String> perms = menuList.stream().filter(Objects::nonNull).map(SystemMenu::getPerms).filter(Objects::nonNull).collect(Collectors.toList());
        String[] authoritiesArray = perms.toArray(new String[perms.size()]);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authoritiesArray);

        return new SecuritySystemUser(systemUser,menuList,authorities);

    }
}

