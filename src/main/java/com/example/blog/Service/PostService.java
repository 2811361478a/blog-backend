package com.example.blog.service;

import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.mapper.CategoryMapper;
import com.example.blog.mapper.LikeMapper;
import com.example.blog.mapper.PostMapper;
import com.example.blog.mapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void addViewCount(Long id) {
        String key = "post:view:" + id;
        redisTemplate.opsForValue().increment(key);
    }

    public Long getViewCount(Long id) {
        String key = "post:view:" + id;
        Long count = (Long) redisTemplate.opsForValue().get(key);
        return count != null ? count : 0L;
    }

    public Post create(Post post) {
        post.setCreateTime(LocalDateTime.now());
        post.setUpdateTime(LocalDateTime.now());
        post.setViewCount(0);
        if (post.getStatus() == null) {
            post.setStatus(0);
        }
        postMapper.insert(post);
        return post;
    }

    public List<Post> getAll(Integer status, String keyword) {
        List<Post> posts;
        if (keyword != null && !keyword.isEmpty()) {
            posts = postMapper.searchByKeyWord(keyword);
        } else if (status != null) {
            posts = postMapper.findByStatus(status);
        } else {
            posts = postMapper.findByStatus(1);
        }
        fillPostDetails(posts);
        return posts;
    }

    public Post getById(Long id) {
        String key = "post:detail:" + id;
        String json = (String) redisTemplate.opsForValue().get(key);
        if (json != null) {
            try {
                return objectMapper.readValue(json, Post.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Post post = postMapper.findById(id);
        if (post != null) {
            fillPostDetail(post);
            try {
                String postJson = objectMapper.writeValueAsString(post);
                redisTemplate.opsForValue().set(key, postJson, 30, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return post;
    }

    public Post update(Long id, Post post) {
        Post existing = postMapper.findById(id);
        if (existing == null) {
            return null;
        }
        existing.setTitle(post.getTitle());
        existing.setContent(post.getContent());
        existing.setSummary(post.getSummary());
        existing.setStatus(post.getStatus());
        existing.setUpdateTime(LocalDateTime.now());
        postMapper.update(existing);
        redisTemplate.delete("post:detail:" + id);
        return existing;
    }

    public boolean delete(Long id) {
        Post post = postMapper.findById(id);
        if (post != null) {
            postMapper.delete(id);
            redisTemplate.delete("post:detail:" + id);
            return true;
        }
        return false;
    }

    public List<Post> getByAuthorId(Long authorId, Integer status, Long currentUserId) {
        List<Post> posts;
        if (currentUserId == null || !currentUserId.equals(authorId)) {
            posts = postMapper.findByAuthorIdAndStatus(authorId, 1);
        } else {
            if (status != null) {
                posts = postMapper.findByAuthorIdAndStatus(authorId, status);
            } else {
                posts = postMapper.findByAuthorId(authorId);
            }
        }
        fillPostDetails(posts);
        return posts;
    }

    private void fillPostDetails(List<Post> posts) {
        for (Post post : posts) {
            fillPostDetail(post);
        }
    }

    private void fillPostDetail(Post post) {
        User user = userMapper.findById(post.getAuthorId());
        if (user != null) {
            post.setAuthorName(user.getUsername());
        }
        if (post.getCategoryId() != null) {
            Category category = categoryMapper.findById(post.getCategoryId());
            if (category != null) {
                post.setCategoryName(category.getName());
            }
        }
        post.setLikeCount((long) likeMapper.countByPostId(post.getId()));
    }
}
