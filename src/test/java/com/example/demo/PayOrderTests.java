package com.example.demo;


import com.example.demo.Controller.PayOrderController;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.PointDTO;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.Entity.Product;
import com.example.demo.Entity.User;
import com.example.demo.Service.PayOrderService;
import com.example.demo.Service.PointsService;
import com.example.demo.Service.ProductService;
import com.example.demo.Service.UserService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class PayOrderTests {

    private MockMvc mockMvc;

    @Mock
    private PayOrderService payOrderService;

    @Mock
    private ProductService productService;

    @Mock
    private PointsService pointsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PayOrderController payOrderController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(payOrderController).build();

        // 模拟 ThreadLocalUtil.get() 返回的用户ID
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        ThreadLocalUtil.set(claims);
    }

    @Test
    public void testCreatePayOrder_OutOfStock() throws Exception {
        Integer productId = 1;
        Integer quantity = 10;
        int addressId = 123;

        Product product = new Product();
        product.setProductId(productId);
        product.setStock(5); // 库存不足

        doReturn(product).when(productService).getProductById(productId);

        mockMvc.perform(post("/pay/create")
                        .param("productId", productId.toString())
                        .param("quantity", quantity.toString())
                        .param("addressId", String.valueOf(addressId))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Out of Stock"));
    }

    @Test
    public void testCreatePayOrder_NoEnoughPoints() throws Exception {
        Integer productId = 1;
        Integer quantity = 2;
        int addressId = 123;

        Product product = new Product();
        product.setProductId(productId);
        product.setStock(10);
        product.setPointsCost(10);

        PointDTO pointDTO = new PointDTO();
        pointDTO.setPointsBalance(5); // 积分不足

        doReturn(product).when(productService).getProductById(productId);
        when(pointsService.getPoints(1)).thenReturn(pointDTO);

        mockMvc.perform(post("/pay/create")
                        .param("productId", productId.toString())
                        .param("quantity", quantity.toString())
                        .param("addressId", String.valueOf(addressId))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("No Enough Points"));
    }

    @Test
    public void testCreatePayOrder_InvalidProduct() throws Exception {
        Integer productId = 1;
        Integer quantity = 2;
        int addressId = 123;

        when(productService.getProductById(productId)).thenReturn(null); // 无效商品

        mockMvc.perform(post("/pay/create")
                        .param("productId", productId.toString())
                        .param("quantity", quantity.toString())
                        .param("addressId", String.valueOf(addressId))
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Invalid Product"));
    }
}