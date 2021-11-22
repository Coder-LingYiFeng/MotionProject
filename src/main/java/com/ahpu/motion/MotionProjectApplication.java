package com.ahpu.motion;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.ahpu.motion.mapper")
@SpringBootApplication
public class MotionProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(MotionProjectApplication.class, args);
    }

}
