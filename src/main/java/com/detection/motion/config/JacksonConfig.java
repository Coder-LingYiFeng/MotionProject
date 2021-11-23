package com.detection.motion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

@Configuration
public class JacksonConfig{

    @Bean
    ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder()
                .timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        jackson2ObjectMapperBuilder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return jackson2ObjectMapperBuilder.build();
    }
}
