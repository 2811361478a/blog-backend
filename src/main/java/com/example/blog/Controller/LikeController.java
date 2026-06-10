package com.example.blog.controller;

import com.example.blog.Result;
import com.example.blog.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/{postId}")
    public Result<Boolean> toggleLike(@PathVariable Long postId, @RequestParam Long userId) {
        return Result.success(likeService.toggleLike(userId, postId));
    }

    @GetMapping("/{postId}/check")
    public Result<Boolean> isLiked(@PathVariable Long postId, @RequestParam Long userId) {
        return Result.success(likeService.isLiked(userId, postId));
    }

    @GetMapping("/{postId}/count")
    public Result<Integer> getCount(@PathVariable Long postId) {
        return Result.success(likeService.getLikeCount(postId));
    }
}
