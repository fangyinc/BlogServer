package com.chengfy.blog.dao.repository;

import com.chengfy.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Post findByTitle(String title);
    Page<Post> findByTags_Id(Long id, Pageable pageable);
    Page<Post> findBySections_Id(Long id, Pageable pageable);
    List<Post> findByContentHtmlContaining(String query);   // 模糊查询
    List<Post> findByTitleContaining(String title);

    /**
     * 得到归档信息
     * @return 归档信息 eg. [[2018, 6, 71], [2018, 7, 2]]
     */
    @Query(value="select year(p.createTime) as year, month(p.createTime) as month, "
            + "count(*) as count from Post p group by year(p.createTime),month(p.createTime)"
    )
    public List<Object[]> findPostGroupByTime();

    /**
     * 根据年月信息获取文章
     * @param yearmonth 日期
     * @return 文章
     */
    @Query("from Post p where date_format(p.createTime,'%Y%m')=date_format((:yearmonth),'%Y%m') "
            + "order by createTime desc")
    public List<Post> findByYearAndMonth(@Param("yearmonth")Date yearmonth);

}
