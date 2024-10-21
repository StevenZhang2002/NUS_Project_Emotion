package com.example.demo.Service;

import com.example.demo.Entity.PageBean;
import com.example.demo.Entity.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    PageBean page(Integer page, Integer pageSize);

    void addPost(Post post);
}
