package com.example.demo.post.Service;

import com.example.demo.common.Entity.PageBean;
import com.example.demo.common.Entity.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    PageBean page(Integer page, Integer pageSize);

    void addPost(Post post);
}
