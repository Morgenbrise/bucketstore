package com.bucketstore.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // ✅ 필드 네이밍 전략 (대문자 스네이크)
        mapper.setPropertyNamingStrategy(new UpperSnakeCaseStrategy());
        // ✅ LocalDateTime 직렬화 지원
        mapper.registerModule(new JavaTimeModule());
        // ✅ 날짜를 ISO-8601 문자열로 출력하도록 설정
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
