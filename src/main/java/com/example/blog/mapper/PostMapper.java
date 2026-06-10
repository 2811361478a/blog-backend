package com.example.blog.mapper;

import com.example.blog.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> findAll();
    List<Post> findByStatus(Integer status);
    List<Post> searchByKeyWord(@Param("keyword") String keyword);
    List<Post> findByAuthorId(Long authorId);
    List<Post> findByAuthorIdAndStatus(@Param("authorId") Long authorId, @Param("status") Integer status);
    Post findById(Long id);
    void insert(Post post);
    void update(Post post);
    void delete(Long id);
}
