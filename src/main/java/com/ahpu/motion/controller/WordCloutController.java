package com.ahpu.motion.controller;

import com.ahpu.motion.utils.ParticipleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordCloutController {
    @Autowired
    ParticipleUtil participleUtil;

    @PostMapping("/getWordCloud")
    public String getWordCloud(){
        System.out.println(participleUtil.getParticipleRes("今天真开心！"));
        return "0";
    }
}
