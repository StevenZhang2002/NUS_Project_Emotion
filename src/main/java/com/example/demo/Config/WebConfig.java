package com.example.demo.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebConfig {

    @Bean
    public OpenAPI springShopOpenApi() {
        return new OpenAPI()
                // 接口文档标题
                .info(new Info().title("Moodiary接口文档")
                        // 接口文档简介
                        .description("Moodiary后端开发接口文档")
                        // 接口文档版本
                        .version("1.0")
                        // 开发者联系方式
                        .contact(new Contact().name("ISS - 7")
                        ));

    }

}
