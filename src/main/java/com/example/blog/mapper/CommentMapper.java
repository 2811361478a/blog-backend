package com.example.blog.mapper;

import com.example.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CommentMapper {
    List<Comment> findByPostId(Long postId);
    int countByPostId(Long postId);
    void insert(Comment comment);
    void delete(Long id);
}
