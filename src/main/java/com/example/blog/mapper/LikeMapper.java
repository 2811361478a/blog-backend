package com.example.blog.mapper;

import com.example.blog.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeMapper {
    Like findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
    int countByPostId(Long postId);
    void insert(Like like);
    void delete(@Param("userId") Long userId, @Param("postId") Long postId);
}
