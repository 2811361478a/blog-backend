package com.example.blog.service;

import com.example.blog.Result;
import com.example.blog.entity.User;
import com.example.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public Result<User> register(User user) {
        User existing = userMapper.findByUsername(user.getUsername());
        if (existing != null) {
            return Result.error("用户已存在");
        }
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
        return Result.success(user);
    }

    public Result<User> login(User user) {
        User u = userMapper.findByUsername(user.getUsername());
        if (u == null || !u.getPwd().equals(user.getPwd())) {
            return Result.error("用户不存在或密码错误");
        }
        return Result.success(u);
    }

    public List<User> getAll() {
        return userMapper.findAll();
    }

    public Result<User> findById(Long id) {
        User u = userMapper.findById(id);
        if (u == null) {
            return Result.error("用户不存在");
        }
        return Result.success(u);
    }

    public Result<User> update(Long id, User user) {
        User u = userMapper.findById(id);
        if (u == null) {
            return Result.error("没有此用户");
        }
        u.setUsername(user.getUsername());
        u.setPwd(user.getPwd());
        u.setAvatar(user.getAvatar());
        userMapper.update(u);
        return Result.success(u);
    }
}
