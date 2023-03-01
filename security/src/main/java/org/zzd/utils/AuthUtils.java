package org.zzd.utils;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author :zzd
 * @date : 2023-02-15 14:25
 */
public class AuthUtils {
    public static Long getUserId() {
        return Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
