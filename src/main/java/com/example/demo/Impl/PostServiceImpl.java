package com.example.demo.Impl;

import com.example.demo.DTO.PostDTO;
import com.example.demo.Mapper.PostMapper;
import com.example.demo.Entity.Post;
import com.example.demo.Entity.PageBean;
import com.example.demo.Service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public void addPost(Post post) {
        postMapper.insertPost(post);
    }

    @Override
    public PageBean page(Integer page, Integer pageSize) {
        // 1. 获取总记录数
        Long count = postMapper.count();

        // 2. 获取分页查询结果列表
        Integer start = (page - 1) * pageSize; // 计算起始索引 , 公式: (页码-1)*页大小
        List<PostDTO> postDTOList = postMapper.list(start, pageSize);
        System.out.println("Fetched PostDTO List: " + postDTOList);

        // 3. 封装PageBean对象
        PageBean pageBean = new PageBean(count, postDTOList);
        return pageBean;
    }
}
