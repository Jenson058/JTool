package com.jenson.tool.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SystemUtil {

    /**
     * 获取登录用户的信息，通过 JwtAuthFilter 中的 setAuth 方法中的 UsernamePasswordAuthenticationToken 参数获取信息
     * @return 用户信息字符串
     */
    public static String getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication))
            if (authentication instanceof UsernamePasswordAuthenticationToken)
                return authentication.getName();
        return null;
    }

}
