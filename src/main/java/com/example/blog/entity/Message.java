package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime createTime;
    private Boolean isRead;
    @Transient
    private String senderName;
    @Transient
    private String receiverName;
    @Transient
    private String senderAvatar;
    @Transient
    private String receiverAvatar;
}
