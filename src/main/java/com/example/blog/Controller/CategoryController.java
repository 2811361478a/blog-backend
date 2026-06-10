package com.example.blog.controller;

import com.example.blog.Result;
import com.example.blog.service.CategoryService;
import com.example.blog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result<List<Category>> getAll() {
        return Result.success(categoryService.getAll());
    }

    @PutMapping("/{id}")
    public Result<Category> update(@PathVariable Long id, @RequestBody Category category) {
        Category updated = categoryService.update(id, category);
        if (updated == null) {
            return Result.error("分类不存在");
        }
        return Result.success(updated);
    }

    @PostMapping
    public Result<Category> create(@RequestBody Category category) {
        Category created = categoryService.create(category);
        if (created == null) {
            return Result.error("分类已存在");
        }
        return Result.success(created);
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id) {
        if (categoryService.delete(id)) {
            return Result.success("删除成功");
        }
        return Result.error("分类不存在");
    }
}
