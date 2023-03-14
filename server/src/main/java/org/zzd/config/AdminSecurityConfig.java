package org.zzd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.zzd.service.SystemUserService;

/**
 * @author :zzd
 * @apiNote :后台管理系统securityConfig
 * @date : 2023-03-12 15:56
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig {
    @Autowired
    @Lazy
    private SystemUserService systemUserService;

    /**
     * @apiNote 将认证交给springSecurity完成
     * @date 2023/3/12 18:37
     * @return org.springframework.security.core.userdetails.UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> systemUserService.loadUserByUsername(username);
    }
}
