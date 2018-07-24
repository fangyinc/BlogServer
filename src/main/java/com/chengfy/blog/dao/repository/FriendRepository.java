package com.chengfy.blog.dao.repository;

import com.chengfy.blog.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
