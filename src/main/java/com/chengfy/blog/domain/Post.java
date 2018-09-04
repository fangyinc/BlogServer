package com.chengfy.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Post extends BaseModel
{
    @Column(nullable = false, unique = true)
    private String title;

    @Lob
//    @Column(length = 16777216)
    private String content;

    // columnDefinition = "MEDIUMTEXT",
    @Lob
//    @Column(length = 16777216)
    private String contentHtml;

    // 不能使用desc
    @Column(columnDefinition = "TEXT")
    private String summary;

    private String img;

    // 是否可见, 可以将草稿设置为不可见
    private boolean visible = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
//    @JsonIgnore // 避免循环引用
    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "post_section", joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "section_id"))
    private Set<Section> sections = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
//    @JsonIgnore // 避免循环引用
    private Set<Comment> comments;
}
