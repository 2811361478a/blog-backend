package com.example.blog.Repository;

import com.example.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostRepository extends JpaRepository<Post,Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByStatus(Integer status,Pageable pageable);
    @Query("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%',:keyword,'%') OR p.content LIKE CONCAT('%',:keyword,'%')")
    Page<Post> searchByKeyWord(@Param("keyword") String keyword,Pageable pageable);
    Page<Post> findByAuthorId(Long authorId,Pageable pageable);
    Page<Post> findByAuthorIdAndStatus(Long authorId,Integer status,Pageable pageable);
}