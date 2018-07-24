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
public class Category extends BaseModel
{
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * cascade: REMOVE
     */
    @OneToMany(cascade={CascadeType.REMOVE},mappedBy = "category")
    @JsonIgnore // 避免循环引用
    private Set<Post> posts = new HashSet<>();

}
