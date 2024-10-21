package com.example.demo.Config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        // 创建凭证提供者，配置用户名和密码
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "elastic"));  // 设置用户名和密码

        // 创建 RestClientBuilder 并设置身份验证
        RestClientBuilder builder = RestClient.builder(new HttpHost("47.129.197.205", 30920, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        // 创建 RestClientTransport，并使用 JacksonJsonpMapper 处理 JSON
        RestClientTransport transport = new RestClientTransport(builder.build(), new JacksonJsonpMapper());

        // 返回配置后的 ElasticsearchClient
        return new ElasticsearchClient(transport);
    }
}



