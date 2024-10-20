package com.example.demo.Mapper;

import com.example.demo.DTO.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM products")
    List<ProductDTO> getAllProducts();
}
