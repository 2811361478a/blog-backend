package com.example.blog.Controller;

import com.example.blog.Repository.UserRepository;
import com.example.blog.Result;
import com.example.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/Users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user){
        if (userRepository.existsByUsername(user.getUsername())){
            return Result.error("用户已存在");
        }
        return Result.success(userRepository.save(user));
    }
    @GetMapping
    public Result<List<User>> getAll(){
        return Result.success(userRepository.findAll());
    }
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        User u=userRepository.findByUsername(user.getUsername());
        if (u==null||!u.getPwd().equals(user.getPwd())){
            return Result.error("用户不存在或密码错误");
        }
        return Result.success(u);
    }
    @PutMapping("/{id}")
    public Result<User> update(@PathVariable Long id,@RequestBody User user){
        User u=userRepository.findById(id).orElse(null);
        if (u==null){
            return Result.error("没有此用户");
        }
        u.setUsername(user.getUsername());
        u.setPwd(user.getPwd());
        u.setAvatar(user.getAvatar());
        return Result.success(userRepository.save(u));
    }
    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable Long id){
        User u=userRepository.findById(id).orElse(null);
        if (u==null){
           return Result.error("用户不存在");
        }
        return Result.success(u);
    }
}
