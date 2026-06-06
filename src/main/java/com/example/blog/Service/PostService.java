package com.example.blog.Service;

import com.example.blog.Repository.CategoryRepository;
import com.example.blog.Repository.LikeRepository;
import com.example.blog.Repository.PostRepository;
import com.example.blog.Repository.UserRepository;
import com.example.blog.entity.Category;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    public Post create(Post post){
        post.setCreateTime(LocalDateTime.now());
        post.setUpdateTime(LocalDateTime.now());
        post.setViewCount(0);
        if (post.getStatus()==null){
            post.setStatus(0);
        }
        return postRepository.save(post);
    }
    public Page<Post> getAll(Integer status,String keyword, Pageable pageable){
        Page<Post> posts;
        if (keyword!=null&&!keyword.isEmpty()){
            posts=postRepository.searchByKeyWord(keyword, pageable);
        }
        else if (status != null){
             posts=postRepository.findByStatus(status,pageable);
        }else {
             posts=postRepository.findByStatus(1,pageable);
        }
        for (Post post:posts){
            User user=userRepository.findById(post.getAuthorId()).orElse(null);
            if (user!=null){
                post.setAuthorName(user.getUsername());
            }
            if (post.getCategoryId()!=null){
                Category category=categoryRepository.findById(post.getCategoryId()).orElse(null);
                if (category!=null){
                    post.setCategoryName(category.getName());
                }
            }
            post.setLikeCount(likeRepository.countByPostId(post.getId()));
        }
        return posts;
    }
    public Post getById(Long id){
        Post post=postRepository.findById(id).orElse(null);
        if (post!=null){
            User user=userRepository.findById(post.getAuthorId()).orElse(null);
            if (user!=null){
                post.setAuthorName(user.getUsername());
            }
            if (post.getCategoryId()!=null){
                Category category=categoryRepository.findById(post.getCategoryId()).orElse(null);
                if (category!=null){
                    post.setCategoryName(category.getName());
                }
            }
            post.setLikeCount(likeRepository.countByPostId(post.getId()));
        }
        return post;
    }
    public Post update(Long id,Post post){
        Post existing=postRepository.findById(id).orElse(null);
        if (existing==null){
            return null;
        }
        existing.setTitle(post.getTitle());
        existing.setContent(post.getContent());
        existing.setSummary(post.getSummary());
        existing.setStatus(post.getStatus());
        existing.setUpdateTime(LocalDateTime.now());
        return postRepository.save(existing);
    }
    public boolean delete(Long id){
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public Page<Post> getByAuthorId(Long authorId,Integer status,Long currentUserId,Pageable pageable){
        Page<Post> posts;
        if (currentUserId==null||!currentUserId.equals(authorId)){
            posts = postRepository.findByAuthorIdAndStatus(authorId, 1, pageable);
        }else {
            if (status!=null){
                posts=postRepository.findByAuthorIdAndStatus(authorId,status,pageable);
            }else {
                posts=postRepository.findByAuthorId(authorId,pageable);
            }
        }
        for (Post post:posts){
            User user=userRepository.findById(authorId).orElse(null);
            if (user!=null){
                post.setAuthorName(user.getUsername());
            }
            if (post.getCategoryId()!=null){
                Category category=categoryRepository.findById(post.getCategoryId()).orElse(null);
                if (category!=null){
                    post.setCategoryName(category.getName());
                }
            }
            post.setLikeCount(likeRepository.countByPostId(post.getId()));
        }
        return posts;
    }
}
