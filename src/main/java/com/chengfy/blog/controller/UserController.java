package com.chengfy.blog.controller;

import com.chengfy.blog.domain.User;
import com.chengfy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Secured({ROLE_ADMINISTER}) // 关闭普通用户注册接口
    @PostMapping(value = {"", "/"})
    public ResponseEntity<?>  create(@RequestBody User user){
        String passwordSalt = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(passwordSalt);
        User newUser = userService.create(user);
        return restPostOk(newUser, "created user successfully");
    }

    @PutMapping(value = "/{id}")
    @Secured({ROLE_ADMINISTER, ROLE_WRITE_ARTICLES, ROLE_VISITOR})
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody User user){
        User oldUser = userService.findById(id);
        if(oldUser == null){
            return restNotFound("User not found with id: " + id);
        }
        User loginUser = getUserFromContext();
        if(loginUser == null || !(loginUser.equals(oldUser) ||
                userService.isAdminister(loginUser))){
            return restUnauthorized("Unauthorized");
        }
        user.setId(id);
        user.setAuthorities(oldUser.getAuthorities());
        if(user.getPassword() != null && !user.getPassword().equals("")){
            String passwordSalt = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(passwordSalt);
            user.setLastPasswordResetDate(oldUser.getLastPasswordResetDate());
        }else {
            user.setPassword(oldUser.getPassword());
            user.setLastPasswordResetDate(oldUser.getLastPasswordResetDate());
        }
        User updatedUser = userService.update(user);
        return restPostOk(updatedUser, "modified user successfully");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        User user = userService.findById(id);
        if (user == null) {
            return restNotFound("User not found with id: " + id);
        }
        return restGetOk(user, "Got user successfully");
    }

    @Secured({ROLE_ADMINISTER})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return restNotFound("User not found with id: " + id);
        }
        userService.deleteById(id);
        return restDeleteOk();
    }

}
