package com.chengfy.blog.service;

import com.chengfy.blog.dao.repository.SectionRepository;
import com.chengfy.blog.domain.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class SectionService {
    @Autowired
    SectionRepository sectionRepository;

    public Section findOrCreateByName(String name){
        Section section = sectionRepository.findByName(name);
        if(section == null){
            section = new Section();
            section.setName(name);
            section.setCreateTime(new Date());
            section.setLastModify(new Date());
            section = sectionRepository.save(section);
        }
        return section;
    }
    public Set<Section> createBySet(Set<Section> sections){
        Set<Section> sectionSet = new HashSet<>();
        for(Section s: sections){
            sectionSet.add(findOrCreateByName(s.getName()));
        }
        return sectionSet;
    }

    public List<Section> findAll(){
        return sectionRepository.findAll();
    }

    public Section findById(Long id){
        Optional<Section> optionalSection = sectionRepository.findById(id);
        return optionalSection.orElse(null);
    }

    public void deleteById(Long id){
        sectionRepository.deleteById(id);
    }
}
