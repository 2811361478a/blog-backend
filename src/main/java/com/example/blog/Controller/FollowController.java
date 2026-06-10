package com.example.blog.controller;

import com.example.blog.Result;
import com.example.blog.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    @PostMapping("/{userId}/follow/{targetId}")
    public Result<String> follow(@PathVariable Long userId, @PathVariable Long targetId) {
        return followService.follow(userId, targetId);
    }

    @DeleteMapping("/{userId}/follow/{targetId}")
    public Result<String> unfollow(@PathVariable Long userId, @PathVariable Long targetId) {
        return followService.unfollow(userId, targetId);
    }

    @GetMapping("/{userId}/isFollowing/{targetId}")
    public Result<Boolean> isFollowing(@PathVariable Long userId, @PathVariable Long targetId) {
        return Result.success(followService.isFollowing(userId, targetId));
    }

    @GetMapping("/{userId}/following")
    public Result<Set<Object>> getFollowing(@PathVariable Long userId) {
        return Result.success(followService.getFollowing(userId));
    }

    @GetMapping("/{userId}/followers")
    public Result<Set<Object>> getFollowers(@PathVariable Long userId) {
        return Result.success(followService.getFollowers(userId));
    }

    @GetMapping("/{userId}/followCount")
    public Result<Map<String, Long>> getFollowCount(@PathVariable Long userId) {
        return Result.success(followService.getFollowCount(userId));
    }
}
