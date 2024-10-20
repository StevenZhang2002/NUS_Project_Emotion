package com.example.demo.Impl;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Mapper.ProductMapper;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.getAllProducts();
    }
}
