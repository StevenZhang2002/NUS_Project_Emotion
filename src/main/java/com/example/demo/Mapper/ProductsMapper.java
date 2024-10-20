package com.example.demo.Mapper;

import com.example.demo.DTO.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductsMapper {

    // 根据商品ID查询商品DTO
    @Select("select * from products where productId = #{productId}")
    ProductDTO selectProductDTOById(int productId);

    @Update("update products set stock = #{productsStock} where productId = #{productId}")
    void setProductsStock(int productsStock, int productId);
}
