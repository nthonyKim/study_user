package com.study.user.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class SecurityUser extends User {
    private static final String ROLE_PREFIX = "ROLE_";

    public SecurityUser(String userId, String authGroupId) {
        super(userId, userId, List.of(new SimpleGrantedAuthority(ROLE_PREFIX + authGroupId)));
    }
}
