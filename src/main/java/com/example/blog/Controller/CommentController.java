package com.example.blog.controller;

import com.example.blog.Result;
import com.example.blog.service.CommentService;
import com.example.blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    public Result<Comment> addComment(@PathVariable Long postId, @RequestParam Long userId, @RequestBody String content) {
        return Result.success(commentService.addComment(postId, userId, content));
    }

    @GetMapping("/{postId}")
    public Result<List<Comment>> getByPostId(@PathVariable Long postId) {
        return Result.success(commentService.getByPostId(postId));
    }

    @GetMapping("/{postId}/count")
    public Result<Integer> getCommentCount(@PathVariable Long postId) {
        return Result.success(commentService.getCommentCount(postId));
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteComment(@PathVariable Long id) {
        if (commentService.deleteComment(id)) {
            return Result.success("删除成功");
        }
        return Result.error("评论不存在");
    }
}
