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
import org.springframework.stereotype.Service;
import org.zzd.constant.PageConstant;
import org.zzd.constant.SecurityConstants;
import org.zzd.dto.LoginDto;
import org.zzd.dto.UserInfoDto;
import org.zzd.entity.SystemMenu;
import org.zzd.entity.SystemUser;
import org.zzd.exception.ResponseException;
import org.zzd.mapper.SystemMenuMapper;
import org.zzd.mapper.SystemUserMapper;
import org.zzd.pojo.SecuritySystemUser;
import org.zzd.result.ResponseResult;
import org.zzd.result.ResultCodeEnum;
import org.zzd.service.SystemUserService;
import org.zzd.utils.*;
import org.zzd.vo.TokenVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    RedisCache redisCache;
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
        SecuritySystemUser user = new SecuritySystemUser(login);
        String token = jwtTokenUtil.generateToken(user);
        Map<String,Object> map = new HashMap();
        map.put("token",token);
        map.put("tokenHead", SecurityConstants.TOKEN_PREFIX);
        map.put("expireTime",jwtTokenUtil.getExpiredDateFromToken(token).getTime());
        //token值存入redis
        redisCache.setCacheObject("token_",token);
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
        SecuritySystemUser systemSecurityUser = getCurrentSecuritySystemUser();
        SystemUser systemUser = systemSecurityUser.getSystemUser();
        List<SystemMenu> menus = systemSecurityUser.getMenus();
        //获取角色权限编码字段
        Object[] perms = menus.stream().filter(Objects::nonNull).map(SystemMenu::getPerms).filter(StringUtils::isNotBlank).toArray();
        UserInfoDto userInfoDto = new UserInfoDto(systemUser.getId(),systemUser.getNickname(),systemUser.getAvatar(),systemUser.getDescription(),perms);
        return ResponseResult.success(userInfoDto);
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
     * @apiNote 获得当前的带detail的用户
     * @date 2023/3/15 10:51
     * @return org.zzd.pojo.SecuritySystemUser
     */
    public SecuritySystemUser getCurrentSecuritySystemUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResponseException(500,"用户信息查询失败");
        }
        return (SecuritySystemUser) authentication.getPrincipal();
    }

    /**
     * @apiNote 获得当前用户
     * @date 2023/3/12 19:03
     * @return org.zzd.entity.SystemUser
     */
    public SystemUser getCurrentSystemUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new ResponseException(500,"用户信息查询失败");
        }
        SecuritySystemUser systemUser = (SecuritySystemUser) authentication.getPrincipal();
        return systemUser.getSystemUser();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        SystemUser systemUser = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("username", username));

        //获取菜单列表
        List<SystemMenu> menuList = systemMenuMapper.getSystemUserMenuList(systemUser.getId());
        //获取权限集合
        List<String> perms = menuList.stream()
                .filter(Objects::nonNull)
                .map(SystemMenu::getPerms)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        String[] authoritiesArray = perms.toArray(new String[perms.size()]);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(authoritiesArray);
        return new SecuritySystemUser(systemUser,menuList,authorities);

    }

    @Override
    public ResponseResult refreshToken(HttpServletRequest request) {
        String token = null;
        String bearerToken = request.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.contains(bearerToken,SecurityConstants.TOKEN_PREFIX) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            token =  bearerToken.replace(SecurityConstants.TOKEN_PREFIX+" ","");
        }
        SecuritySystemUser systemSecurityUser = getCurrentSecuritySystemUser();
        String reToken = "";
        if (jwtTokenUtil.validateToken(token,systemSecurityUser)) {
            reToken = jwtTokenUtil.refreshToken(token);
        }
        Long expireTime = jwtTokenUtil.getExpiredDateFromToken(reToken).getTime();
        TokenVo tokenVo = new TokenVo(expireTime,reToken);
        redisCache.setCacheObject("token_",reToken);
        return ResponseResult.success(tokenVo);
    }
}

