package com.chengfy.blog.controller;

import com.chengfy.blog.controller.webentity.PostInfoListEntity;
import com.chengfy.blog.domain.Category;
import com.chengfy.blog.domain.Post;
import com.chengfy.blog.service.CategoryService;
import com.chengfy.blog.service.PostService;
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
@RequestMapping(value = "/cates")
@CrossOrigin(origins = "*")
public class CategoryController extends BaseController{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> findAll(){
        List<Category> categories = categoryService.findAll();
        PostInfoListEntity<Category> listEntity = new PostInfoListEntity<>(categories);
        return restGetOk(listEntity.getInfo(), "get all categories successfully");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Category category = categoryService.findById(id);
        if(category == null){
            return restNotFound("There is no category with id : " + id);
        }
        return restGetOk(category, "get category successfully");
    }

    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<?> findPostsByCateId(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = PAGE_SIZE) Integer size){
        Category category = categoryService.findById(id);
        if(category == null){
            return restNotFound("There is no category with id: " + id);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postService.findAllByCategory(pageable, category);
        return restGetOk(postPage, "got category`s posts successfully");
    }

    @Secured({ROLE_ADMINISTER})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Category category = categoryService.findById(id);
        if(category == null){
            return restNotFound("There is no category with id: " + id);
        }
        categoryService.deleteById(id);
        return restDeleteOk();
    }
}
