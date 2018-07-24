package com.chengfy.blog.controller;

import com.chengfy.blog.controller.webentity.PostInfoListEntity;
import com.chengfy.blog.domain.Post;
import com.chengfy.blog.domain.Section;
import com.chengfy.blog.service.PostService;
import com.chengfy.blog.service.SectionService;
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
@RequestMapping(value = "/sections")
@CrossOrigin(origins = "*")
public class SectionController extends BaseController {

    @Autowired
    private SectionService sectionService;

    @Autowired
    private PostService postService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> findAll(){
        List<Section> sections = sectionService.findAll();
        PostInfoListEntity<Section> entity = new PostInfoListEntity<>(sections);
        return restGetOk(entity.getInfo(), "Got sections successfully");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Section section = sectionService.findById(id);
        if(section == null){
            return restNotFound("There is no section with id: " + id);
        }
        return restGetOk(section, "Got section successfully");
    }

    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<?> findPostsBySectionId(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = PAGE_SIZE) Integer size){
        Section section = sectionService.findById(id);
        if(section == null){
            return restNotFound("There is no section with id: " + id);
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postPage = postService.findAllBySection(pageable, section);
        return restGetOk(postPage, "got section`s posts successfully");
    }

    @Secured({ROLE_ADMINISTER})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Section section = sectionService.findById(id);
        if(section == null){
            return restNotFound("There is no section with id: " + id);
        }
        sectionService.deleteById(id);
        return restDeleteOk();
    }
}
