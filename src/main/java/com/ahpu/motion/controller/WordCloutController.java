package com.ahpu.motion.controller;

import com.ahpu.motion.bean.Device;
import com.ahpu.motion.utils.ParticipleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WordCloutController {
    @Autowired
    ParticipleUtil participleUtil;

    @PostMapping("/getWordCloud")
    public Map<String, Integer> getWordCloud(Device device){
        Integer createUserId = device.getCreateUserId();

        return participleUtil.getParticipleRes("今天真开心！");

    }
}
