package com.example.blog.Controller;

import com.example.blog.Result;
import com.example.blog.Service.LikeService;
import com.example.blog.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/{postId}")
    Result<Boolean> toggleLike(@PathVariable Long postId, @RequestParam Long userId){
        return Result.success(likeService.toggleLike(userId,postId));
    }

    @GetMapping("/{postId}/check")
    Result<Boolean> isLiked(@PathVariable Long postId,@RequestParam Long userId){
        return Result.success(likeService.isLiked(userId, postId));
    }
    @GetMapping("/{postId}/count")
    Result<Long> getCount(@PathVariable Long postId){
        return Result.success(likeService.getLikeCount(postId));
    }
}
