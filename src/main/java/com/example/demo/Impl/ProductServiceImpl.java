package com.example.demo.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.Mapper.ProductMapper;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    // 定时清空 Elasticsearch 索引中的所有文档，每15分钟执行一次
    @Scheduled(fixedRate = 15 * 60 * 1000)  // 15分钟
    public void clearElasticsearchDocuments() throws IOException {
        try {
            // 使用 deleteByQuery 删除索引中的所有文档
            elasticsearchClient.deleteByQuery(d -> d
                    .index("product_index")
                    .query(q -> q.matchAll(m -> m)) // 匹配所有文档
            );
            System.out.println("Elasticsearch 索引中的所有文档已清空。");
            importAllProductsToElasticsearch();
        } catch (Exception e) {
            System.err.println("清空 Elasticsearch 索引文档时出错：" + e.getMessage());
        }
    }


    // 检查 Elasticsearch 是否为空
    public boolean isElasticsearchEmpty() throws IOException {
        long count = elasticsearchClient.count(c -> c.index("product_index")).count();
        return count == 0;
    }

    // 查询所有商品
    @Override
    public List<ProductDTO> getAllProducts() {
        return productMapper.getAllProducts();
    }

    // 将商品数据导入 Elasticsearch
    public void importAllProductsToElasticsearch() throws IOException {
        List<ProductDTO> products = getAllProducts();  // 直接调用现有方法获取所有商品
        for (ProductDTO product : products) {
            // 构建每个商品的索引请求
            IndexRequest<ProductDTO> indexRequest = IndexRequest.of(i -> i
                    .index("product_index")  // 指定索引名
                    .id(product.getProductId().toString())  // 使用商品ID作为文档ID
                    .document(product)  // 将商品数据作为文档
            );
            elasticsearchClient.index(indexRequest);  // 将商品数据索引到 Elasticsearch
        }
        System.out.println("商品信息已导入到 Elasticsearch 索引库。");
    }

    // 根据关键词搜索商品
    public List<ProductDTO> searchProducts(String keyword) throws IOException {
        // 执行 Elasticsearch 搜索
        SearchResponse<ProductDTO> searchResponse = elasticsearchClient.search(s -> s
                .index("product_index")
                .query(q -> q
                        .multiMatch(m -> m
                                .fields("productName", "productDescription")  // 搜索的字段
                                .query(keyword)  // 搜索关键词
                        )
                ), ProductDTO.class);

        // 返回搜索结果
        return searchResponse.hits().hits().stream()
                .map(Hit::source)  // 提取每个文档的源数据
                .toList();
    }

}
