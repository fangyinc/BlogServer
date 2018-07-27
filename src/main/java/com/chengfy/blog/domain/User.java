package com.chengfy.blog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseModel
{
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    private Integer age;

    private String email;

    @Column(nullable = false)
    private String password;    // 此处应该存储的是hash值

    private String avatarImg;   // 头像

    private String backgroundImg;   // 背景头像

    private String about;           // 简介

    private String address;     // 地址

    private String signature;   // 签名

    private Date lastSeen;      // 上次登录

    private boolean enabled;     // jwt认证时添加

//    @Temporal(TemporalType.TIMESTAMP)
//    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "id")})
//    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore // 避免循环引用
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Section> sections = new HashSet<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}

