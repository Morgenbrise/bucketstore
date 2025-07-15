package com.bucketstore.common.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("버킷스토어 API 문서")
                        .version("v1.0.0")
                        .description("버킷스토어 주문/상품 API에 대한 명세서입니다.")
                );
    }
}