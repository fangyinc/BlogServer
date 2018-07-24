package com.chengfy.blog.controller.webentity;

import com.chengfy.blog.domain.*;
import lombok.Data;

import java.util.Set;

/**
 * 接受RequestBody的参数 并返回新的post
 * 这里需要注意一个问题 Set中的值是不允许重复的，所以BaseMode重写的hashCode方法
 * 需要保证在没有数据库数据的情况下也能正常使用
 */
@Data
public class PostEntity {
    private Post post;
    private Category category;
    private Set<Tag> tags;
    private Set<Section> sections;

    public Post newPost() {
        post.setCategory(category);
        post.setTags(tags);
        post.setSections(sections);
        return post;
    }
}
