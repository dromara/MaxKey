package com.unicom.sso.bigdata.cas.demo.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 可自定义获取用户信息
        return new User("admin", "admin", true, true, true, true,
                AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    }
}