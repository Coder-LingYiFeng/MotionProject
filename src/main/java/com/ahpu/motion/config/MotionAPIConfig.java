package com.ahpu.motion.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "motion.api")
public class MotionAPIConfig {
    private String appId;
    private String apiKey;
    private String secretKey;

}
