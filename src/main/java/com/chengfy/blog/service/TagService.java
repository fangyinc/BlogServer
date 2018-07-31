package com.chengfy.blog.service;

import com.chengfy.blog.dao.repository.TagRepository;
import com.chengfy.blog.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public Tag findOrCreateByName(String name){
        Tag tag = tagRepository.findByName(name);
        if(tag == null){
            tag = new Tag();
            tag.setName(name);
            tag.setCreateTime(new Date());
            tag.setLastModify(new Date());
            tag = tagRepository.save(tag);
        }
        return tag;
    }

    public Set<Tag> createBySet(Set<Tag> tags){
        Set<Tag> tagSet = new HashSet<>();
        for(Tag t: tags){
            tagSet.add(findOrCreateByName(t.getName()));
        }
        return tagSet;
    }

    public Tag findById(Long id){
        Optional<Tag> optionalTag = tagRepository.findById(id);
        return optionalTag.orElse(null);
    }

    public void deleteById(Long id){
        tagRepository.deleteById(id);
    }

    public List<Tag> findAll(){
//        return tagRepository.findAll();
        return tagRepository.findByPosts_Visible();
    }
}
