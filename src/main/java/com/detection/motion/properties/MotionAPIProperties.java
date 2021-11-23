package com.detection.motion.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MotionAPI的配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "motion.api")
public class MotionAPIProperties {
    private String appId;
    private String apiKey;
    private String secretKey;
}
