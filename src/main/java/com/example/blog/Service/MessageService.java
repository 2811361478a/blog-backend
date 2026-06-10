package com.example.blog.service;

import com.example.blog.entity.Message;
import com.example.blog.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public Message send(Message message) {
        message.setCreateTime(LocalDateTime.now());
        message.setIsRead(false);
        messageMapper.insert(message);
        return message;
    }

    public List<Message> receive(Long userId) {
        return messageMapper.findByReceiverId(userId);
    }

    public List<Message> getSend(Long userId) {
        return messageMapper.findBySenderId(userId);
    }

    public List<Message> getConversation(Long userId1, Long userId2) {
        return messageMapper.findConversation(userId1, userId2);
    }

    public void markAsRead(Long userId, Long senderId) {
        messageMapper.markAsRead(userId, senderId);
    }

    public int getUnreadCount(Long userId) {
        return messageMapper.countUnread(userId);
    }
}
