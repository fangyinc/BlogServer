package com.chengfy.blog.controller.webentity;

import com.chengfy.blog.domain.BaseModel;

import lombok.Data;

@Data
public class PostInfoEntity<T extends BaseModel> {
    private T info;     // Category, Tag, Section
    private Long postCount;

    public PostInfoEntity(T info, Long postCount) {
        this.info = info;
        this.postCount = postCount;
    }
}
