package com.chengfy.blog.dao.repository;

import com.chengfy.blog.domain.Authority;
import com.chengfy.blog.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByPermission(Permission permission);
}
