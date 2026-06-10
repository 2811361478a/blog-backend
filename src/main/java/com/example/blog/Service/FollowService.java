package com.example.blog.service;

import com.example.blog.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Result<String> follow(Long userId, Long targetId) {
        if (userId.equals(targetId)) {
            return Result.error("不能关注自己");
        }
        String followKey = "user:follow:" + userId;
        String fansKey = "user:fans:" + targetId;
        redisTemplate.opsForSet().add(followKey, targetId);
        redisTemplate.opsForSet().add(fansKey, userId);
        return Result.success("关注成功");
    }

    public Result<String> unfollow(Long userId, Long targetId) {
        String followKey = "user:follow:" + userId;
        String fansKey = "user:fans:" + targetId;
        redisTemplate.opsForSet().remove(followKey, targetId);
        redisTemplate.opsForSet().remove(fansKey, userId);
        return Result.success("取关成功");
    }

    public boolean isFollowing(Long userId, Long targetId) {
        String followKey = "user:follow:" + userId;
        return redisTemplate.opsForSet().isMember(followKey, targetId);
    }

    public Set<Object> getFollowing(Long userId) {
        String followKey = "user:follow:" + userId;
        return redisTemplate.opsForSet().members(followKey);
    }

    public Set<Object> getFollowers(Long userId) {
        String fansKey = "user:fans:" + userId;
        return redisTemplate.opsForSet().members(fansKey);
    }

    public Map<String, Long> getFollowCount(Long userId) {
        String followKey = "user:follow:" + userId;
        String fansKey = "user:fans:" + userId;
        Map<String, Long> map = new HashMap<>();
        map.put("followingCount", redisTemplate.opsForSet().size(followKey));
        map.put("followersCount", redisTemplate.opsForSet().size(fansKey));
        return map;
    }
}
