package com.chengfy.blog.controller;

import com.chengfy.blog.domain.Comment;
import com.chengfy.blog.domain.Post;
import com.chengfy.blog.service.CommentService;
import com.chengfy.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/comments")
@CrossOrigin(origins = "*")
public class CommentController extends BaseController {

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @PostMapping(value = {"/{postId}"})
    public ResponseEntity<?> create(@RequestBody Comment comment, @PathVariable Long postId){
        Post post = postService.findById(postId);
        if(post == null){
            return restBadRequest("评论的文章不能为空");
        }
        comment.setPost(post);
        comment = commentService.create(comment);
        return restPostOk(comment, "Create comment successfully");
    }

    @PostMapping(value = {"/reply/{id}"})
    public ResponseEntity<?> replyById(@RequestBody Comment reply, @PathVariable Long id){
        Comment comment = commentService.findById(id);
        if(comment == null){
            return restBadRequest("待回复文章为空");
        }
        Comment newReply = commentService.create(reply);

        Set<Comment> commentSet = comment.getReplies();
        commentSet.add(newReply);
        comment.setReplies(commentSet);
        comment = commentService.update(comment);
        return restPostOk(comment, "回复成功");
    }

    @Secured({ROLE_ADMINISTER})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Comment comment = commentService.findById(id);
        if(comment == null){
            return restNotFound("There is no comment with id: " + id);
        }
        commentService.deleteById(id);
        return restDeleteOk();
    }
}
