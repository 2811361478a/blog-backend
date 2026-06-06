package com.example.blog.Service;

import com.example.blog.Repository.MessageRepository;
import com.example.blog.Repository.UserRepository;
import com.example.blog.entity.Message;
import com.example.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public Message send(Message message){
        message.setCreateTime(LocalDateTime.now());
        message.setIsRead(false);
        return messageRepository.save(message);
    }
    public List<Message> receive(Long userId){
        List<Message> messages=messageRepository.findByReceiverIdOrderByCreateTimeDesc(userId);
        for (Message msg:messages){
            fillNames(msg);
        }
        return messages;
    }
    public List<Message> getSend(Long userId){
        List<Message> messages=messageRepository.findBySenderIdOrderByCreateTimeDesc(userId);
        for (Message msg:messages){
            fillNames(msg);
        }
        return messages;
    }
    public List<Message> getConversation(Long userId1,Long userId2){
        List<Message> messages=messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByCreateTimeAsc(userId1,userId2,userId2,userId1);
        for (Message msg:messages){
            fillNames(msg);
        }
        return messages;
    }
    public void markAsRead(Long userId,Long senderId){
        List<Message> messages=messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByCreateTimeAsc(senderId,userId,userId,senderId);
        for (Message msg:messages){
            if (msg.getReceiverId().equals(userId)&&!msg.getIsRead()){
                msg.setIsRead(true);
                messageRepository.save(msg);
            }
        }
    }
    public Long getUnreadCount(Long userId){
        return messageRepository.countByReceiverIdAndIsRead(userId,false);
    }
    public void fillNames(Message msg){
        User sender=userRepository.findById(msg.getSenderId()).orElse(null);
        User receiver=userRepository.findById(msg.getReceiverId()).orElse(null);
        if (sender!=null){
            msg.setSenderName(sender.getUsername());
            msg.setSenderAvatar(sender.getAvatar());
        }
        if (receiver!=null){
            msg.setReceiverName(receiver.getUsername());
            msg.setReceiverAvatar(receiver.getAvatar());
        }
    }
}
