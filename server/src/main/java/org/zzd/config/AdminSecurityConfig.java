package org.zzd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.zzd.config.SecurityConfig;

/**
 * @author :zzd
 * @apiNote :后台管理系统securityConfig
 * @date : 2023-03-12 15:56
 */
@Configuration
@EnableWebSecurity
public class AdminSecurityConfig extends SecurityConfig {
}
