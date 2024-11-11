package com.example.demo;

import com.example.demo.Controller.ProductController;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.Service.ProductService;
import com.example.demo.Utils.FileUploader;
import com.example.demo.Utils.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class ProductTests {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private FileUploader fileUploader;

    // 新增 RedisTemplate Mock
    @Mock
    private RedisTemplate<String, List<ProductDTO>> redisTemplate;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        // 设置 RedisTemplate 的 mock 行为
        when(redisTemplate.opsForValue()).thenReturn(mock(ValueOperations.class));
    }

    @Test
    public void testSearchProducts() throws Exception {
        List<ProductDTO> productList = new ArrayList<>();
        productList.add(new ProductDTO(1, "Product1", "Description1", 10, 100, "imageUrl1"));

        when(productService.searchProducts("Product")).thenReturn(productList);

        mockMvc.perform(get("/products/search")
                        .param("keyword", "Product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1))
                .andExpect(jsonPath("$[0].productName").value("Product1"));

        verify(productService, times(1)).searchProducts("Product");
    }

    @Test
    public void testAddProduct() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());

        when(fileUploader.uploadFile(any(), anyString(), anyLong())).thenReturn("imageUrl");
        doNothing().when(productService).addProduct(any(ProductDTO.class));

        mockMvc.perform(multipart("/products/add")
                        .file(file)
                        .param("productName", "NewProduct")
                        .param("productDescription", "NewDescription")
                        .param("pointsCost", "10")
                        .param("stock", "100")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("Success"));

        verify(productService, times(1)).addProduct(any(ProductDTO.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(anyInt());

        mockMvc.perform(delete("/products/delete")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));

        verify(productService, times(1)).deleteProduct(1);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());

        when(fileUploader.uploadFile(any(), anyString(), anyLong())).thenReturn("imageUrl");
        doNothing().when(productService).updateProduct(any(ProductDTO.class));

        mockMvc.perform(multipart("/products/update")
                        .file(file)
                        .param("productId", "1")
                        .param("productName", "UpdatedProduct")
                        .param("productDescription", "UpdatedDescription")
                        .param("pointsCost", "15")
                        .param("stock", "150")
                        .with(request -> { request.setMethod("PATCH"); return request; }) // 设置为 PATCH 请求
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("Success"));

        verify(productService, times(1)).updateProduct(any(ProductDTO.class));
    }
}