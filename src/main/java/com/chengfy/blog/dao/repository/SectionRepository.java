package com.chengfy.blog.dao.repository;

import com.chengfy.blog.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Section findByName(String name);
}
