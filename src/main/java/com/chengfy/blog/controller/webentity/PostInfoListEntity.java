package com.chengfy.blog.controller.webentity;

import com.chengfy.blog.domain.BaseModel;
import com.chengfy.blog.domain.Category;
import com.chengfy.blog.domain.Section;
import com.chengfy.blog.domain.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class PostInfoListEntity<T extends BaseModel> {
    List<PostInfoEntity> info = null;

    public PostInfoListEntity(Collection<T> collection) {
        List<PostInfoEntity> entityList = new ArrayList<>();
        for (BaseModel b: collection) {
            if(b instanceof Category){
                Category category = (Category)b;
                entityList.add( new PostInfoEntity<Category>(category,
                        category.getPosts().stream().filter(post -> post.isVisible()).count()));
            }else if(b instanceof Tag){
                Tag tag = (Tag)b;
                entityList.add(new PostInfoEntity<Tag>(tag,
                        tag.getPosts().stream().filter(post -> post.isVisible()).count()));
            }else if(b instanceof Section){
                Section section = (Section)b;
                entityList.add(new PostInfoEntity<Section>(section,
                        section.getPosts().stream().filter(post -> post.isVisible()).count()));
            }
        }
        info = entityList;
    }
}
