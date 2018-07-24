package com.chengfy.blog.service;

import com.chengfy.blog.dao.repository.FriendRepository;
import com.chengfy.blog.domain.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendRepository friendRepository;

    public Friend create(Friend friend){
        friend.setCreateTime(new Date());
        friend.setLastModify(new Date());
        return friendRepository.save(friend);
    }

    public Friend update(Friend friend){
        friend.setLastModify(new Date());
        return friendRepository.save(friend);
    }

    public Friend findById(Long id){
        Optional<Friend> optionalFriend =  friendRepository.findById(id);
        return optionalFriend.orElse(null);
    }

    public void deleteById(Long id){
        friendRepository.deleteById(id);
    }

    public Page<Friend> findAll(Pageable pageable){
        return friendRepository.findAll(pageable);
    }

}
