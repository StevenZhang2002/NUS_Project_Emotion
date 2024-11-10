package com.example.demo.Controller;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Service.ProductService;
import com.example.demo.Utils.FileUploader;
import com.example.demo.Utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/products")
@Tag(name = "商品相关接口")
/**
 *
 * 缺少 查找商品
 */
public class ProductController {

    @Autowired
    private ProductService productService;

    // 使用泛型确保类型安全
    @Autowired
    private RedisTemplate<String, List<ProductDTO>> redisTemplate;

    @Autowired
    private FileUploader fileUploader;

    // 缓存的key
    private static final String PRODUCTS_CACHE_KEY = "all_products";



    @GetMapping
    @Operation(summary = "查询所有商品")
    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> productList = null;

        // 先尝试从缓存中获取
        try {
            productList = redisTemplate.opsForValue().get(PRODUCTS_CACHE_KEY);
            if (productList != null && !productList.isEmpty()) {
                System.out.println("从缓存中取出数据：" + productList);
                return productList; // 如果缓存中有数据，直接返回
            }
        } catch (Exception e) {
            // Redis 连接失败，输出日志，继续从数据库查询
            System.err.println("Redis 连接失败，继续查询数据库：" + e.getMessage());
        }

        updateRedis();

        return productList;
    }

    @GetMapping("/search")
    @Operation(summary = "根据关键词搜索商品")
    public List<ProductDTO> searchProducts(@RequestParam String keyword) throws IOException {
        // 使用 Elasticsearch 搜索商品
        return productService.searchProducts(keyword);
    }

    @PostMapping("/add")
    @Operation(summary = "新增商品并上传图片")
    public Result addProduct(@RequestParam("productName") String productName,
                             @RequestParam("productDescription") String productDescription,
                             @RequestParam("pointsCost") int pointsCost,
                             @RequestParam("stock") int stock,
                             @RequestParam("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return Result.error("文件不能为空");
            }

            // 上传图片到 MinIO
            String fileName = file.getOriginalFilename();
            String imageUrl = fileUploader.uploadFile(file.getInputStream(), fileName, file.getSize());

            if (imageUrl == null) {
                return Result.error("图片上传失败");
            }

            // 创建 ProductDTO 并设置属性，包括图片路径
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductName(productName);
            productDTO.setProductDescription(productDescription);
            productDTO.setPointsCost(pointsCost);
            productDTO.setStock(stock);
            productDTO.setImage(imageUrl);  // 将图片URL设置到商品信息中

            // 调用 service 保存商品信息
            productService.addProduct(productDTO);

            updateRedis();

            return Result.success("商品添加成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("商品添加失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品")
    public Result deleteProduct(@RequestParam int productId) {
        productService.deleteProduct(productId);

        updateRedis();

        return Result.success();
    }

    @PatchMapping("/update")
    @Operation(summary = "修改商品并上传图片")
    public Result updateProduct(@RequestParam(value = "productId",required = true) int productId,
                                @RequestParam(value = "productName", required = false) String productName,
                                @RequestParam(value = "productDescription", required = false) String productDescription,
                                @RequestParam(value = "pointsCost", required = false) Integer pointsCost,
                                @RequestParam(value = "stock", required = false) Integer stock,
                                @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            // 创建 ProductDTO 并设置更新的属性
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(productId);
            productDTO.setProductName(productName);
            productDTO.setProductDescription(productDescription);
            productDTO.setPointsCost(pointsCost);
            productDTO.setStock(stock);

            // 如果有文件上传，则先上传文件到 MinIO
            if (file != null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String imageUrl = fileUploader.uploadFile(file.getInputStream(), fileName, file.getSize());

                if (imageUrl == null) {
                    return Result.error("图片上传失败");
                }
                productDTO.setImage(imageUrl);  // 将生成的图片URL设置到 productDTO 中
            }

            // 调用 service 更新商品信息
            productService.updateProduct(productDTO);

            updateRedis();

            return Result.success("商品更新成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("商品更新失败: " + e.getMessage());
        }
    }

    private void updateRedis() {
        // 查询数据库
        List<ProductDTO> productList = productService.getAllProducts();

        // 查询完数据库后，将结果存入缓存
        if (productList != null && !productList.isEmpty()) {
            // 设置缓存过期时间
            redisTemplate.opsForValue().set(PRODUCTS_CACHE_KEY, productList, 15, TimeUnit.MINUTES);
        }
    }
}
