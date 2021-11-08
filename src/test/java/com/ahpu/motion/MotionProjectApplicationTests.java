package com.ahpu.motion;

import com.ahpu.motion.bean.User;
import com.ahpu.motion.mapper.UserMapper;
import com.ahpu.motion.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class MotionProjectApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        User user = userService.getUserByUserName("1234567");
        log.info(String.valueOf(user));
    }

}
