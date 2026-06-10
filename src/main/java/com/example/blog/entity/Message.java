package com.example.blog.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Message {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime createTime;
    private Boolean isRead;

    // 非数据库字段
    private String senderName;
    private String receiverName;
    private String senderAvatar;
    private String receiverAvatar;
}
