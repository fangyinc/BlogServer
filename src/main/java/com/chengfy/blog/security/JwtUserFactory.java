package com.chengfy.blog.security;

import com.chengfy.blog.domain.Authority;
import com.chengfy.blog.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(),
                user.getName(),
                user.getUsername(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getAvatarImg(),
                user.getBackgroundImg(),
                user.getAbout(),
                user.getAddress(),
                user.getSignature(),
                user.getLastSeen(),
                mapToGrantedRoles(user.getAuthorities()),
                user.isEnabled(),
                user.getLastPasswordResetDate());
    }

    private static List<GrantedAuthority> mapToGrantedRoles(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getPermission().name()))
                .collect(Collectors.toList());
    }
}
