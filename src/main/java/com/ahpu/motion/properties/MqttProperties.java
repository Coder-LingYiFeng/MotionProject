package com.ahpu.motion.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("mqtt")
public class MqttProperties {
    private String broker;
}
