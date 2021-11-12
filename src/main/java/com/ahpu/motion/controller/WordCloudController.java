package com.ahpu.motion.controller;

import com.ahpu.motion.bean.Device;
import com.ahpu.motion.bean.Sentence;
import com.ahpu.motion.service.DeviceService;
import com.ahpu.motion.service.SentenceService;
import com.ahpu.motion.utils.ParticipleUtil;
import com.ahpu.motion.utils.StatisticalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/dataAPI")
public class WordCloudController {
    @Autowired
    ParticipleUtil participleUtil;
    @Autowired
    DeviceService deviceService;
    @Autowired
    SentenceService sentenceService;
    @Autowired
    StatisticalUtil statisticalUtil;


    @PostMapping("/getWordCloudByTimeSection")
    public Map<String,Object> getWordCloud(@RequestBody Device device){
        HashMap<String, Object> resMap = new HashMap<>();
        String startTime = device.getStartTime();
        String endTime = device.getEndTime();
//        System.out.println(startTime);
//        System.out.println(endTime);
//        System.out.println(device);
        Integer createUserId = device.getCreateUserId();
        String name = device.getName();
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        ArrayList<Map<String, String>> resList = new ArrayList<>();
        if(deviceInfo==null){
            resMap.put("status","ERROR");
            resMap.put("message","无此设备");
            return resMap;
        }
        Integer deviceId = deviceInfo.getId();
//        System.out.println(deviceId);
        ArrayList<Sentence> sentenceInfoList = sentenceService.selectSentenceBytimeSection(deviceId,startTime,endTime);
//        sentenceInfoList.forEach(System.out::println);
        if (sentenceInfoList.size()==0){
            resMap.put("status","ERROR");
            resMap.put("message","无数据，请调大时间差,并检查是否存在该设备相关语句信息");
            return resMap;
        }
        StringBuilder sb = new StringBuilder();
        sentenceInfoList.forEach(sentenceInfo -> sb.append(sentenceInfo.getSentence()));
        String sentences = sb.toString();
//        Map<String, Integer> participleRes = participleUtil.getParticipleRes(sentences);
        Map<String, Integer> participleRes = statisticalUtil.statisticalWords(participleUtil.getParticipleRes(sentences));
        participleRes.forEach((k, v) -> {
            HashMap<String, String> tempMap = new HashMap<>();
            tempMap.put("name", k);
            tempMap.put("value", String.valueOf(v));
            resList.add(tempMap);
        });
        resMap.put("status","OK");
        resMap.put("message","数据获取成功");
        System.out.println("resList.size() = " + resList.size());
        if (resList.size()!=0)
            resMap.put("data",resList);
        return resMap;
    }
}
