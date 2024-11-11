package com.example.demo;

import com.example.demo.Controller.AddressController;
import com.example.demo.Entity.Address;
import com.example.demo.Service.AddressService;
import com.example.demo.Utils.Result;
import com.example.demo.Utils.ThreadLocalUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class AddressTests {

    private MockMvc mockMvc;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();

        // 模拟 ThreadLocalUtil.get() 返回的用户ID
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        ThreadLocalUtil.set(claims);
    }

    @Test
    public void testAddAddress_Success() throws Exception {
        // 模拟 AddressService 返回一个有效的地址ID
        Address address = new Address();
        address.setUserId(1);
        when(addressService.addAddress(address)).thenReturn(10);

        mockMvc.perform(post("/Address/addAddress")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value(10));
    }

    @Test
    public void testAddAddress_Failure() throws Exception {
        // 模拟 AddressService 返回 0 表示插入失败
        Address address = new Address();
        address.setUserId(1);
        when(addressService.addAddress(address)).thenReturn(0);

        mockMvc.perform(post("/Address/addAddress")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Something wrong"));
    }

    @Test
    public void testGetAddressById_Success() throws Exception {
        // 模拟 AddressService 返回一个有效的 Address 对象
        Address address = new Address();
        address.setAddressId(10);
        address.setUserId(1);
        when(addressService.getAddressById(10)).thenReturn(address);

        mockMvc.perform(get("/Address/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.addressId").value(10));
    }

    @Test
    public void testGetAddressById_Failure() throws Exception {
        // 模拟 AddressService 返回 null 表示无效地址
        when(addressService.getAddressById(999)).thenReturn(null);

        mockMvc.perform(get("/Address/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Invalid Address"));
    }

    @Test
    public void testDeleteAddress_Success() throws Exception {
        // 模拟 AddressService 返回一个有效的 Address 对象
        Address address = new Address();
        address.setAddressId(10);
        address.setUserId(1);
        when(addressService.getAddressById(10)).thenReturn(address);

        mockMvc.perform(delete("/Address/deleteAddress")
                        .param("addressId", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("Success"));
    }

    @Test
    public void testDeleteAddress_InvalidAddress() throws Exception {
        // 模拟 AddressService 返回 null 表示无效地址
        when(addressService.getAddressById(999)).thenReturn(null);

        mockMvc.perform(delete("/Address/deleteAddress")
                        .param("addressId", "999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Invalid Address"));
    }

    @Test
    public void testDeleteAddress_NotRelatedAccount() throws Exception {
        // 模拟 AddressService 返回一个 Address 对象，但 userId 不匹配
        Address address = new Address();
        address.setAddressId(10);
        address.setUserId(2); // 与当前用户的 userId 不匹配
        when(addressService.getAddressById(10)).thenReturn(address);

        mockMvc.perform(delete("/Address/deleteAddress")
                        .param("addressId", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("Not Related Account"));
    }

    @Test
    public void testGetAddressByUserId() throws Exception {
        // 模拟返回当前用户的地址列表
        Address address = new Address();
        address.setAddressId(10);
        address.setUserId(1);
        List<Address> addresses = Collections.singletonList(address);
        when(addressService.getAddressByUserId(1)).thenReturn(addresses);

        mockMvc.perform(get("/Address/adresses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data[0].addressId").value(10));
    }
}


