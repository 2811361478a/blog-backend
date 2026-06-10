package com.example.blog.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Post {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private Long authorId;
    private Long categoryId;
    private Integer status;
    private Integer viewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 非数据库字段
    private String categoryName;
    private String authorName;
    private Long likeCount;
}
