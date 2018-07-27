package com.chengfy.blog.service;

import com.chengfy.blog.dao.repository.AuthorityRepository;
import com.chengfy.blog.dao.repository.UserRepository;
import com.chengfy.blog.domain.Authority;
import com.chengfy.blog.domain.Permission;
import com.chengfy.blog.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public User create(User user){
        // 这里应该添加默认权限
        user.setCreateTime(new Date());
        user.setLastModify(new Date());
        user.setEnabled(true);
        user.setLastPasswordResetDate(new Date());
        // 新用户默认权限为 ROLE_VISITOR
        Authority authority = authorityRepository.findByPermission(Permission.ROLE_VISITOR);
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        user.setAuthorities(authoritySet);

        return userRepository.save(user);
    }
    public User update(User user){
        user.setEnabled(true);
        user.setLastModify(new Date());
        return userRepository.save(user);
    }

    public User findById(Long id){
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public boolean isAdminister(User user){
        return user.getAuthorities().stream().
                anyMatch(r -> r.getName().equals(Permission.ROLE_ADMINISTER.name()));
    }
}
