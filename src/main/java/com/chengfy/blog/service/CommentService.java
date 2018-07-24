package com.chengfy.blog.service;

import com.chengfy.blog.dao.repository.CommentRepository;
import com.chengfy.blog.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Comment create(Comment comment){
        comment.setCreateTime(new Date());
        comment.setLastModify(new Date());
        return commentRepository.save(comment);
    }

    public Comment update(Comment comment){
        comment.setLastModify(new Date());
        return commentRepository.save(comment);
    }

    public Comment findById(Long id){
        Optional<Comment> optionalTag = commentRepository.findById(id);
        return optionalTag.orElse(null);
    }

    public void deleteById(Long id){
        commentRepository.deleteById(id);
    }
}
