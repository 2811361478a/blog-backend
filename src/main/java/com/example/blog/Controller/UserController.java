package com.example.blog.controller;

import com.example.blog.Result;
import com.example.blog.service.UserService;
import com.example.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping
    public Result<List<User>> getAll() {
        return Result.success(userService.getAll());
    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user) {
        return userService.login(user);
    }

    @PutMapping("/{id}")
    public Result<User> update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }
}
