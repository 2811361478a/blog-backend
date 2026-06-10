package com.example.blog.mapper;

import com.example.blog.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MessageMapper {
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findBySenderId(Long senderId);
    List<Message> findConversation(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
    void insert(Message message);
    void markAsRead(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);
    int countUnread(Long receiverId);
}
