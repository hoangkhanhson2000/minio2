package com.example.minio2.config;

import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MinioConfig {

    private final MinioClient minioClient;

    private final MinioConfigurationProperties minioConfigurationProperties;


    public MinioConfig(MinioClient minioClient, MinioConfigurationProperties minioConfigurationProperties) {
        this.minioClient = minioClient;
        this.minioConfigurationProperties = minioConfigurationProperties;
    }

    @Bean
    @Primary
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .credentials(minioConfigurationProperties.getAccessKey(), minioConfigurationProperties.getSecretKey())
                .endpoint(minioConfigurationProperties.getUrl())
                .build();
    }

}