package com.chengfy.blog.service;

import com.chengfy.blog.dao.repository.CategoryRepository;
import com.chengfy.blog.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findOrCreateByName(String name){
        Category category = categoryRepository.findByName(name);
        if(category == null){
            category = new Category();
            category.setName(name);
            category.setCreateTime(new Date());
            category.setLastModify(new Date());
            category = categoryRepository.save(category);
        }
        return category;
    }

    public Category findById(Long id){
        Optional<Category> optional = categoryRepository.findById(id);
        return optional.orElse(null);
    }

    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
}
