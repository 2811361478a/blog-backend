package com.example.blog.mapper;

import com.example.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findById(Long id);
    User findByUsername(String username);
    void insert(User user);
    void update(User user);
    void delete(Long id);
}
