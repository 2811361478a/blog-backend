package com.example.blog.Service;

import com.example.blog.Repository.CategoryRepository;
import com.example.blog.entity.Category;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryRepository.findAllByOrderByNameAsc();
    }
    public Category create(Category category) {
        Category existing=categoryRepository.findByName(category.getName());
        if (existing!=null){
            return null;
        }
        category.setCreateTime(LocalDateTime.now());
        return categoryRepository.save(category);
    }
    public Category update(Long id,Category category){
        Category existing=categoryRepository.findById(id).orElse(null);
        if (existing==null){
            return null;
        }
        existing.setName(category.getName());
        return categoryRepository.save(existing);
    }
    public boolean delete(Long id){
        if (categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @PostConstruct
    public void initDefaultCategory(){
        String[] defaults={"生活", "科技", "军事", "新闻"};
        for (String name:defaults){
            if (categoryRepository.findByName(name)==null){
                Category category=new Category();
                category.setName(name);
                this.create(category);
            }
        }
    }
}
