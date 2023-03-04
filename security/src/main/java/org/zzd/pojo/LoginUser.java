package org.zzd.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zzd.entity.SystemMenu;
import org.zzd.entity.SystemUser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author :zzd
 * @apiNote :封装用户信息
 * @date : 2023-03-02 10:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    private SystemUser user;

    List<String> permissions;

    public LoginUser(SystemUser user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    public LoginUser(SystemUser user) {
        this.user = user;
    }

    @JSONField(serialize = false)
    List<SimpleGrantedAuthority> authorities;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities!=null){
            return authorities;
        }

        authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
