package com.example.blog.service;

import com.example.blog.entity.Like;
import com.example.blog.mapper.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeService {
    @Autowired
    private LikeMapper likeMapper;

    public boolean toggleLike(Long userId, Long postId) {
        Like existing = likeMapper.findByUserIdAndPostId(userId, postId);
        if (existing != null) {
            likeMapper.delete(userId, postId);
            return false;
        }
        Like like = new Like();
        like.setUserId(userId);
        like.setPostId(postId);
        like.setCreateTime(LocalDateTime.now());
        likeMapper.insert(like);
        return true;
    }

    public boolean isLiked(Long userId, Long postId) {
        return likeMapper.findByUserIdAndPostId(userId, postId) != null;
    }

    public int getLikeCount(Long postId) {
        return likeMapper.countByPostId(postId);
    }
}
