package com.chengfy.blog.controller.webentity;

import com.chengfy.blog.domain.Comment;
import lombok.Data;

@Data
public class CommentEntity {
    private Comment comment;
    private Comment reply;
}
