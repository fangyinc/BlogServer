package com.chengfy.blog.dao.repository;

import com.chengfy.blog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    /**
     * 查询所有的分类（其文章必须可见）
     * 内连接
     * @return
     */
    @Query("select c from Category c inner join Post p on p.category.id=c.id " +
            "AND p.visible=true group by c.id")
    List<Category> findByPosts_Visible();
}
