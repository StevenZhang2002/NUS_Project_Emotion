package com.example.demo.Mapper;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ProductMapper {
    @Select("SELECT * FROM products")
    List<ProductDTO> getAllProducts();


    @Select("SELECT * FROM products WHERE productId = #{prodcutId} ")
    Product getProductById(int productId);

    @Insert("INSERT INTO products (productName, productDescription, pointsCost, stock, image) " +
            "VALUES (#{productName}, #{productDescription}, #{pointsCost}, #{stock}, #{image})")
    void insertProduct(ProductDTO productDTO);
}
