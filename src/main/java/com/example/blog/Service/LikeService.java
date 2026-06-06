package com.example.blog.Service;

import com.example.blog.Repository.LikeRepository;
import com.example.blog.Repository.PostRepository;
import com.example.blog.Repository.UserRepository;
import com.example.blog.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;
    public boolean toggleLike(Long userId,Long postId){
        Like existing=likeRepository.findByUserIdAndPostId(userId,postId);
         if(existing!=null){
             likeRepository.delete(existing);
             return false;
         }
         Like like=new Like();
         like.setUserId(userId);
         like.setPostId(postId);
         like.setCreateTime(LocalDateTime.now());
         likeRepository.save(like);
         return true;
    }
    public boolean isLiked(Long userId,Long postId){
        return likeRepository.findByUserIdAndPostId(userId,postId) !=null;
    }
    public Long getLikeCount(Long postId){
        return likeRepository.countByPostId(postId);
    }
}
