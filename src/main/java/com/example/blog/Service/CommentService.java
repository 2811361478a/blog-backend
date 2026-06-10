package com.example.blog.service;

import com.example.blog.entity.Comment;
import com.example.blog.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    public Comment addComment(Long postId, Long userId, String content) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreateTime(LocalDateTime.now());
        commentMapper.insert(comment);
        return comment;
    }

    public List<Comment> getByPostId(Long postId) {
        return commentMapper.findByPostId(postId);
    }

    public int getCommentCount(Long postId) {
        return commentMapper.countByPostId(postId);
    }

    public boolean deleteComment(Long id) {
        commentMapper.delete(id);
        return true;
    }
}
