package com.example.demo.Service;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();

    List<ProductDTO> searchProducts(String keyword) throws IOException;

    void importAllProductsToElasticsearch() throws IOException;

    Product getProductById(int productId);

    void addProduct(ProductDTO productDTO);
}
