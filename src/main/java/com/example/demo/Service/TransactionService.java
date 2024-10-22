package com.example.demo.Service;

import com.example.demo.Entity.PageBean;

public interface TransactionService {

    PageBean page(int userId, Integer page, Integer pageSize);

}
