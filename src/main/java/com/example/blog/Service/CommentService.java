package com.example.blog.Service;

import com.example.blog.Repository.CommentRepository;
import com.example.blog.Repository.UserRepository;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    public Comment addComment(Long postId,Long userId,String content){
        Comment comment=new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setContent(content);
        comment.setCreateTime(LocalDateTime.now());
        return  commentRepository.save(comment);
    }
    public List<Comment> getByPostId(Long postId){
        List<Comment> comments=commentRepository.findByPostId(postId);
        for (Comment comment:comments){
            User user=userRepository.findById(comment.getUserId()).orElse(null);
            if (user!=null){
                comment.setUsername(user.getUsername());
            }
        }
        return comments;
    }
    public Long getCommentCount(Long postId){
        return commentRepository.countByPostId(postId);
    }
    public boolean deleteComment(Long id){
        if (commentRepository.existsById(id)){
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
