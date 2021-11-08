package com.ahpu.motion.controller;

import com.ahpu.motion.utils.MotionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MotionAPIController {

    @Autowired
    MotionUtil motionUtil;

    @GetMapping("/getMotionResult")
    public void get(){
        String text = "今天玩的真开心";
        for (int i = 0; i < 50; i++) {
            System.out.println(motionUtil.getResult(text));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
