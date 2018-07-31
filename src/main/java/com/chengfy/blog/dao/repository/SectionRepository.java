package com.chengfy.blog.dao.repository;

import com.chengfy.blog.domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    Section findByName(String name);

    @Query("select s from Section s join s.posts p join p.sections ps on ps.id = s.id " +
            "AND p.visible=true group by s.id")
    List<Section> findByPosts_Visible();
}
