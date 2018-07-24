package com.chengfy.blog.controller;


import com.chengfy.blog.controller.webentity.PostEntity;
import com.chengfy.blog.domain.Post;
import com.chengfy.blog.domain.User;
import com.chengfy.blog.service.PostService;
import com.chengfy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Secured({ROLE_WRITE_ARTICLES, ROLE_ADMINISTER})
    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> create(@RequestBody PostEntity postEntity){
        User user = getUserFromContext();
        Post post = postEntity.newPost();
        post.setUser(user);
        post = postService.create(post);
        return restPostOk(post, "Create post successfully");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Post post = postService.findById(id);
        if(post == null){
            return restNotFound("There is no post with id: " + id);
        }
        return restGetOk(post, "Found post successfully");
    }

    @Secured({ROLE_ADMINISTER, ROLE_WRITE_ARTICLES})
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody PostEntity postEntity){
        Post post = postService.findById(id);
        if(post == null){
            return restNotFound("There is no post with is: " + id);
        }
        User user = getUserFromContext();
        if(user == null || !(user.equals(post.getUser()) || userService.isAdminister(user))){
            return restUnauthorized("Unauthorized");
        }
        Post newPost = postEntity.newPost();
        newPost.setId(post.getId());
        newPost.setUser(user);
        newPost = postService.update(newPost);
        return restPostOk(newPost, "Update post successfully");
    }

    @Secured({ROLE_WRITE_ARTICLES, ROLE_ADMINISTER})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Post post = postService.findById(id);
        if(post == null){
            return restNotFound("There is no post with id: " + id);
        }
        User user = getUserFromContext();
        if(user == null || !(user.equals(post.getUser()) || userService.isAdminister(user))){
            return restUnauthorized("Unauthorized");
        }
        postService.deleteById(id);
        return restDeleteOk();
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = PAGE_SIZE) Integer size){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postService.findAll(pageable);
        return restGetOk(postPage, "get postPage successfully");
    }

    @GetMapping(value = {"/query/{q}"})
    public ResponseEntity<?> queryByContent(@PathVariable String q){
        List<Post> titlePosts = postService.findAllByTitle(q);
        List<Post> contentPosts = postService.findAllByContent(q);

        Set<Post> postSet = new HashSet<>(titlePosts);
        postSet.addAll(contentPosts);
        if(postSet.isEmpty()){
            return restNotFound("There is no post contain content or title : " + q);
        }
        return restGetOk(postSet, "query successfully");
    }

    @GetMapping(value = "/archive")
    public ResponseEntity<?> archive(){
        List<Object[]> objects = postService.findPostGroupByTime();
        return restGetOk(objects, "Got archive post successfully");
    }

    @GetMapping(value = "/archive/query")
    public ResponseEntity<?> findByDate(@DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date date){
        List<Post> posts = postService.findByYearAndMonth(date);
        if(posts.isEmpty()){
            return restNotFound("there is no post create at " + date.toString());
        }
        return restGetOk(posts, "Got posts successfully");
    }
}
