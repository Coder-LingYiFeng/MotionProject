package com.ahpu.motion.controller;

import com.ahpu.motion.bean.Device;
import com.ahpu.motion.bean.Sentence;
import com.ahpu.motion.service.DeviceService;
import com.ahpu.motion.service.SentenceService;
import com.ahpu.motion.utils.ParticipleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WordCloutController {
    @Autowired
    ParticipleUtil participleUtil;
    @Autowired
    DeviceService deviceService;
    @Autowired
    SentenceService sentenceService;


    @PostMapping("/getWordCloudByTimeSection")
    public ArrayList<Map<String, String>> getWordCloud(@RequestBody Device device){
        String startTime = device.getStartTime();
        String endTime = device.getEndTime();
//        System.out.println(startTime);
//        System.out.println(endTime);
//        System.out.println(device);
        Integer createUserId = device.getCreateUserId();
        String name = device.getName();
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        Integer deviceId = deviceInfo.getId();
        System.out.println(deviceId);
        ArrayList<Sentence> sentenceInfoList = sentenceService.selectSentenceBytimeSection(deviceId,startTime,endTime);
//        sentenceInfoList.forEach(System.out::println);
        StringBuilder sb=new StringBuilder();
        sentenceInfoList.forEach(sentenceInfo-> sb.append(sentenceInfo.getSentence()));
        String sentences = sb.toString();
        Map<String, Integer> participleRes = participleUtil.getParticipleRes(sentences);
        ArrayList<Map<String,String>> resList=new ArrayList<>();
        participleRes.forEach((k,v)->{
            HashMap<String, String> tempMap = new HashMap<>();
            tempMap.put("name",k);
            tempMap.put("value",String.valueOf(v));
            resList.add(tempMap);
        });
        return resList;

    }
}
