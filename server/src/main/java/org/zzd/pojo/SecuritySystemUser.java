package org.zzd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.zzd.entity.SystemMenu;
import org.zzd.entity.SystemUser;

import java.util.Collection;
import java.util.List;

/**
 * @author :zzd
 * @apiNote :用户信息，用户权限信息
 * @date : 2023-03-02 10:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecuritySystemUser implements UserDetails {

    private SystemUser systemUser;

    //用户菜单列表

    @TableField(exist = false)
    private List<SystemMenu> menus;
    //用户权限列表

    List<GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return systemUser.getPassword();
    }

    @Override
    public String getUsername() {
        return systemUser.getUsername();
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
        return systemUser.getStatus()==1;
    }

    public SecuritySystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }
}
