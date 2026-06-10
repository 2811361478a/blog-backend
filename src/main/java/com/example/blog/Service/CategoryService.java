package com.example.blog.service;

import com.example.blog.entity.Category;
import com.example.blog.mapper.CategoryMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAll() {
        return categoryMapper.findAll();
    }

    public Category create(Category category) {
        Category existing = categoryMapper.findByName(category.getName());
        if (existing != null) {
            return null;
        }
        category.setCreateTime(LocalDateTime.now());
        categoryMapper.insert(category);
        return category;
    }

    public Category update(Long id, Category category) {
        Category existing = categoryMapper.findById(id);
        if (existing == null) {
            return null;
        }
        existing.setName(category.getName());
        categoryMapper.update(existing);
        return existing;
    }

    public boolean delete(Long id) {
        Category category = categoryMapper.findById(id);
        if (category != null) {
            categoryMapper.delete(id);
            return true;
        }
        return false;
    }

    @PostConstruct
    public void initDefaultCategory() {
        String[] defaults = {"生活", "科技", "军事", "新闻"};
        for (String name : defaults) {
            if (categoryMapper.findByName(name) == null) {
                Category category = new Category();
                category.setName(name);
                this.create(category);
            }
        }
    }
}
