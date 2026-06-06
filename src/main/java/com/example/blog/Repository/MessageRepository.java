package com.example.blog.Repository;

import com.example.blog.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findBySenderIdOrderByCreateTimeDesc(Long senderId);
    List<Message> findByReceiverIdOrderByCreateTimeDesc(Long receiverId);
    List<Message> findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByCreateTimeAsc(
            Long senderId1,Long receiverId1,Long senderId2,Long receiverId2);
    Long countByReceiverIdAndIsRead(Long receiverId,Boolean isRead);
}
