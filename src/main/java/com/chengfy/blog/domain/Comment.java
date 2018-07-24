package com.chengfy.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String siteUrl;

    @Column(columnDefinition = "TEXT")
    private String contentHtml;

    @OneToMany()
    private Set<Comment> replies = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore // 避免循环引用
    private Post post;

}
