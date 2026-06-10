package com.example.blog.mapper;

import com.example.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findAll();
    Category findById(Long id);
    Category findByName(String name);
    void insert(Category category);
    void update(Category category);
    void delete(Long id);
}
