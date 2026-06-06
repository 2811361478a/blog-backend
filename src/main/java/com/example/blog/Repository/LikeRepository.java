package com.example.blog.Repository;

import com.example.blog.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
     Like findByUserIdAndPostId(Long userId,Long postId);
     Long countByPostId(Long postId);
}
