package com.ahpu.motion;

import com.ahpu.motion.config.MotionAPIConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@MapperScan("com.ahpu.motion.mapper")
@SpringBootApplication
public class MotionProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(MotionProjectApplication.class, args);
    }

}
