package com.ahpu.motion.controller;


import com.ahpu.motion.bean.Device;
import com.ahpu.motion.service.DeviceService;
import com.ahpu.motion.service.SentenceService;
import com.ahpu.motion.utils.StatisticalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MotionPieController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    SentenceService sentenceService;
    @Autowired
    StatisticalUtil statisticalUtil;

    @PostMapping("/getMotionPieByTimeSection")
    public Map<String, Object> getMotionPieByTimeSection(@RequestBody Device device){
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
            resMap.put("data",resList);
            return resMap;
        }
        Integer deviceId = deviceInfo.getId();
        System.out.println(deviceId);
        ArrayList<Integer> sentimentList = sentenceService.selectSentimentBytimeSection(deviceId, startTime, endTime);
        if (sentimentList.size()==0){
            resMap.put("status","ERROR");
            resMap.put("message","无数据，请调大时间差");
            resMap.put("data",resList);
            return resMap;
        }
        StringBuilder sb = new StringBuilder();
        sentimentList.forEach(sentiment-> sb.append(sentiment).append(" "));
        String sentimentStr = sb.toString();
        Map<String, Integer> sentimenRes = statisticalUtil.statisticalWords(sentimentStr);
        sentimenRes.forEach((k, v) -> {
            if (!"null".equals(k)){
                String key=null;
                HashMap<String, String> tempMap = new HashMap<>();
                switch (k) {
                    case "0":
                        key = "负向";
                        break;
                    case "1":
                        key = "中性";
                        break;
                    case "2":
                        key = "正向";
                        break;
                }
                tempMap.put("name", key);
                tempMap.put("value", String.valueOf(v));
                resList.add(tempMap);
            }
        });
        resMap.put("status","OK");
        resMap.put("message","数据获取成功");
        resMap.put("data",resList);
        return resMap;
    }
}
