package com.chengfy.blog.controller;

import com.chengfy.blog.domain.Friend;
import com.chengfy.blog.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friend")
@CrossOrigin("*")
public class FriendController extends BaseController {

    @Autowired
    private FriendService friendService;

    @Secured({ROLE_ADMINISTER})
    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> create(@RequestBody Friend friend) {
        Friend friend1 = friendService.create(friend);
        return restPostOk(friend1, "Created Friend successfully");
    }

    @Secured({ROLE_ADMINISTER})
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody Friend friend) {
        Friend friend1 = friendService.findById(id);
        if (friend1 == null) {
            return restNotFound("There is no friend with is: " + id);
        }
        friend.setId(friend1.getId());
        friend1 = friendService.update(friend);
        return restPostOk(friend1, "Update friend successfully");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Friend friend = friendService.findById(id);
        if (friend == null) {
            return restNotFound("There is no friend with id: " + id);
        }
        return restGetOk(friend, "Found friend successfully");
    }

    @Secured({ROLE_ADMINISTER})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Friend friend = friendService.findById(id);
        if (friend == null) {
            return restNotFound("There is no friend with id: " + id);
        }
        friendService.deleteById(id);
        return restDeleteOk();
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> findALl(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = PAGE_SIZE) Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Friend> friendPage = friendService.findAll(pageable);
        return restGetOk(friendPage, "get postPage successfully");
    }
}
