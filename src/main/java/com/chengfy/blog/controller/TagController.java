package com.chengfy.blog.controller;

import com.chengfy.blog.controller.webentity.PostInfoListEntity;
import com.chengfy.blog.domain.Post;
import com.chengfy.blog.domain.Tag;
import com.chengfy.blog.service.PostService;
import com.chengfy.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "*")
public class TagController extends BaseController{
    @Autowired
    TagService tagService;

    @Autowired
    PostService postService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> findAll(){
        List<Tag> tags = tagService.findAll();
        PostInfoListEntity<Tag> entity = new PostInfoListEntity<>(tags);
        return restGetOk(entity.getInfo(), "Got tags successfully");
    }

    @GetMapping(value = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable Long id){
        Tag tag = tagService.findById(id);
        if(tag == null){
            return restNotFound("There is no tag with id: " + id);
        }
        return restGetOk(tag, "Got tags successfully");
    }

    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<?> findPostsByTagId(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = PAGE_SIZE) Integer size){

        Tag tag = tagService.findById(id);
        if(tag == null){
            return restNotFound("There is no tag with id: " + id);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postService.findAllByTag(pageable, tag);
        return restGetOk(postPage, "got category`posts successfully");
    }

    @Secured({ROLE_ADMINISTER})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Tag tag = tagService.findById(id);
        if(tag == null){
            return restNotFound("There is no tag with id: " + id);
        }
        tagService.deleteById(id);
        return restDeleteOk();
    }
}
