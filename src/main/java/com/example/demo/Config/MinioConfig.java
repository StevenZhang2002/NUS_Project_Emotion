package com.example.demo.Config;

import com.example.demo.Utils.FileUploader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Bean
    public FileUploader fileUploader() {
        return new FileUploader();
    }
}
