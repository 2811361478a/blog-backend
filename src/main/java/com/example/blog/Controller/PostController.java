package com.example.blog.Controller;

import com.example.blog.Result;
import com.example.blog.Service.PostService;
import com.example.blog.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping ("/Post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public Result<Post> create(@RequestBody Post post){
        return Result.success(postService.create(post));
    }
    @GetMapping
    public Result<Page<Post>> getAll(@RequestParam(required = false) Integer status,@RequestParam(required = false) String keyword ,Pageable pageable){
        return Result.success(postService.getAll(status,keyword,pageable));
    }
    @PutMapping("/{id}")
    public Result<Post> update(@PathVariable Long id,@RequestBody Post post,@RequestParam Long userId){
        Post existing=postService.getById(id);
        if (existing==null){
            return Result.error("文章不存在");
        }
        if (!existing.getAuthorId().equals(userId)){
            return Result.error("只能修改自己的文章");
        }
        Post updated=postService.update(id,post);
        return Result.success(updated);
    }
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id,@RequestParam Long userId){
        Post existing=postService.getById(id);
        if (existing==null){
            return Result.error("文章不存在");
        }
        if (!existing.getAuthorId().equals(userId)){
            return Result.error("只能删除自己的文章");
        }
        postService.delete(id);
        return Result.success("删除成功");

    }
    @GetMapping("/{id}")
    public Result<Post> getById(@PathVariable Long id){
        Post post= postService.getById(id);
        if (post==null){
            return Result.error("文章不存在");
        }
        return Result.success(post);
    }
    @GetMapping("/user/{authorId}")
    public Result<Page<Post>> getByAuthorId(
            @PathVariable Long authorId,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Long currentUserId,
            Pageable pageable){
        return Result.success(postService.getByAuthorId(authorId,status,currentUserId,pageable));
    }
}
