package com.detection.motion.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Mqtt的配置类
 */
@Component
@Data
@ConfigurationProperties("mqtt")
public class MqttProperties {
    private String broker;
}
