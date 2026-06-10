package com.example.blog.controller;

import com.example.blog.Result;
import com.example.blog.service.MessageService;
import com.example.blog.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public Result<Message> send(@RequestBody Message message) {
        return Result.success(messageService.send(message));
    }

    @GetMapping("/receive/{userId}")
    public Result<List<Message>> receive(@PathVariable Long userId) {
        return Result.success(messageService.receive(userId));
    }

    @GetMapping("/send/{userId}")
    public Result<List<Message>> getSend(@PathVariable Long userId) {
        return Result.success(messageService.getSend(userId));
    }

    @GetMapping("/conversation/{userId1}/{userId2}")
    public Result<List<Message>> getConversation(@PathVariable Long userId1, @PathVariable Long userId2) {
        return Result.success(messageService.getConversation(userId1, userId2));
    }

    @PutMapping("/read/{userId}/{senderId}")
    public Result<String> markAsRead(@PathVariable Long userId, @PathVariable Long senderId) {
        messageService.markAsRead(userId, senderId);
        return Result.success("标记成功");
    }

    @GetMapping("/unread/{userId}")
    public Result<Integer> getUnreadCount(@PathVariable Long userId) {
        return Result.success(messageService.getUnreadCount(userId));
    }
}
