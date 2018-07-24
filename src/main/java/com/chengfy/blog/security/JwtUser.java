package com.chengfy.blog.security;

import com.chengfy.blog.domain.Authority;
import com.chengfy.blog.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by stephan on 20.03.16.
 */
@Data
public class JwtUser implements UserDetails {

    private final Long id;

    private final String name;

    private final String username;

    private final Integer age;

    private final String email;

    private final String password;    // 此处应该存储的是hash值

    private final String avatarImg;   // 头像

    private final String backgroundImg;   // 背景头像

    private final String about;           // 简介

    private final String address;     // 地址

    private final String signature;   // 签名

    private final Date lastSeen;      // 上次登录

    private final boolean enabled;

    private final Date lastPasswordResetDate;

    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String name, String username, Integer age, String email,
                   String password, String avatarImg, String backgroundImg, String about,
                   String address, String signature, Date lastSeen,
                   Collection<? extends GrantedAuthority> authorities,
                   boolean enabled, Date lastPasswordResetDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.age = age;
        this.email = email;
        this.password = password;
        this.avatarImg = avatarImg;
        this.backgroundImg = backgroundImg;
        this.about = about;
        this.address = address;
        this.signature = signature;
        this.lastSeen = lastSeen;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public User getUser(){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatarImg(avatarImg);
        user.setBackgroundImg(backgroundImg);
        user.setAbout(about);
        user.setAddress(address);
        user.setSignature(signature);
        user.setLastSeen(lastSeen);
        user.setEnabled(enabled);
        user.setLastPasswordResetDate(lastPasswordResetDate);
        // get names of authorities
        Set<Authority> authoritySet =
                authorities.stream().map(r -> {
                    Authority temp = new Authority();
                    temp.setName(((GrantedAuthority) r).getAuthority());
                    return temp;
                }).collect(Collectors.toSet());
        user.setAuthorities(authoritySet);
        return user;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
