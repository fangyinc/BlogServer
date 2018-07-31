package com.chengfy.blog.dao.repository;

import com.chengfy.blog.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    /**
     * 多对多内连接 cool!!!!
     * @return
     */
    @Query("select t from Tag t join t.posts p join p.tags pt on pt.id = t.id " +
            "AND p.visible=true group by t.id")
    List<Tag> findByPosts_Visible();
}
