package com.chengfy.blog.service;

import com.chengfy.blog.dao.repository.PostRepository;
import com.chengfy.blog.domain.Category;
import com.chengfy.blog.domain.Post;
import com.chengfy.blog.domain.Section;
import com.chengfy.blog.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private SectionService sectionService;

    /**
     * 新建文章 post必须包含专栏、分类、标签和用户等关联信息
     * @param post  文章
     * @return  创建后的文章
     */
    public Post create(Post post) {
        post.setCreateTime(new Date());
        post.setLastModify(new Date());
        post.setCategory(categoryService.findOrCreateByName(post.getCategory().getName()));
        post.setTags(tagService.createBySet(post.getTags()));
        post.setSections(sectionService.createBySet(post.getSections()));
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        Optional<Post> optional = postRepository.findById(id);
        return optional.orElse(null);
    }

    public Post update(Post post){
        post.setLastModify(new Date());
        post.setCategory(categoryService.findOrCreateByName(post.getCategory().getName()));
        post.setTags(tagService.createBySet(post.getTags()));
        post.setSections(sectionService.createBySet(post.getSections()));
        return postRepository.save(post);
    }

    public void deleteById(Long id){
        postRepository.deleteById(id);
    }

    /**
     * 查询所有的可见文章
     * @param pageable
     * @return
     */
    public Page<Post> findAll(Pageable pageable){
        return postRepository.findByVisible(true, pageable);
    }

    // 不可见文章(草稿)
    public Page<Post> findAllInvisible(Pageable pageable) {
        return postRepository.findByVisible(false, pageable);
    }

    public Page<Post> findAllByCategory(Pageable pageable, Category category){
//        Specification<Post> specification = (Root<Post> root, CriteriaQuery<?> criteriaQuery,
//                                             CriteriaBuilder criteriaBuilder)->{
//            Predicate _categoryId = criteriaBuilder.equal(root.get("category"), category);
//            return criteriaBuilder.and(_categoryId);
//        };
//        return postRepository.findAll(specification, pageable);
        return postRepository.findByCategoryAndVisible(category, true, pageable);
    }

    public Page<Post> findAllByTag(Pageable pageable, Tag tag){
        return postRepository.findByTags_IdAndVisible(tag.getId(), true, pageable);
    }

    public Page<Post> findAllBySection(Pageable pageable, Section section){
        return postRepository.findBySections_IdAndVisible(section.getId(), true, pageable);
    }

    public List<Post> findAllByContent(String queryStr){
        return postRepository.findByVisibleAndContentHtmlContaining(true, queryStr);    // 模糊查询
    }
    public List<Post> findAllByTitle(String title){
        return postRepository.findByVisibleAndTitleContaining(true, title);
    }

    public List<Object[]> findPostGroupByTime(){
        return postRepository.findPostGroupByTime();
    }

    public List<Post> findByYearAndMonth(Date date){
        return postRepository.findByYearAndMonth(date);
    }
}
