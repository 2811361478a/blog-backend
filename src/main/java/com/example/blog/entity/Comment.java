package com.example.blog.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long userId;
    private Long postId;
    private String content;
    private LocalDateTime createTime;

    // 非数据库字段
    private String username;
}
