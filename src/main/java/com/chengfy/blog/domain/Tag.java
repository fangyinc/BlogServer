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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tag extends BaseModel
{
    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore // 避免循环引用
//    @JoinTable(name = "post_tag", joinColumns = @JoinColumn(name = "tag_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();
}
