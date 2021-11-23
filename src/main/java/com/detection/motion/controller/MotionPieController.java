package com.detection.motion.controller;


import com.detection.motion.bean.Device;
import com.detection.motion.service.DeviceService;
import com.detection.motion.service.SentenceService;
import com.detection.motion.utils.StatisticalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 情感分析饼图的数据
 */
@RestController
@RequestMapping("/dataAPI")
public class MotionPieController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    SentenceService sentenceService;
    @Autowired
    StatisticalUtil statisticalUtil;

    /**
     * 获取时间区间内情感分析饼图的数据
     * @param device 必要参数 startTime endTime createUserId name
     * @return 饼图数据
     */
    @PostMapping("/getMotionPieByTimeSection")
    public Map<String, Object> getMotionPieByTimeSection(@RequestBody Device device){
        HashMap<String, Object> resMap = new HashMap<>();
        //参数获取及校验
        String startTime = device.getStartTime();
        String endTime = device.getEndTime();
        Integer createUserId = device.getCreateUserId();
        String name = device.getName();
        if(createUserId == null){
            resMap.put("status","ERROR");
            resMap.put("message","createUserId为空");
            return resMap;
        }
        if(name==null||"".equals(name)){
            resMap.put("status","ERROR");
            resMap.put("message","设备名为空");
            return resMap;
        }
        if(startTime==null||"".equals(startTime)){
            resMap.put("status","ERROR");
            resMap.put("message","查询开始时间为空");
            return resMap;
        }
        if(endTime==null||"".equals(endTime)){
            resMap.put("status","ERROR");
            resMap.put("message","查询结束时间为空");
            return resMap;
        }
        //获取设备信息
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        ArrayList<Map<String, String>> resList = new ArrayList<>();
        if(deviceInfo==null){
            resMap.put("status","ERROR");
            resMap.put("message","无此设备");
            return resMap;
        }
        //获取设备id（sentence表关联此id）
        Integer deviceId = deviceInfo.getId();
        //获取此设备在该时间区间内的语句信息
        ArrayList<Integer> sentimentList = sentenceService.selectSentimentBytimeSection(deviceId, startTime, endTime);
        //无数据
        if (sentimentList.size()==0){
            resMap.put("status","ERROR");
            resMap.put("message","无数据，请调大时间差");
            return resMap;
        }
        //拼接所有情感极性结果
        StringBuilder sb = new StringBuilder();
        sentimentList.forEach(sentiment-> sb.append(sentiment).append(" "));
        String sentimentStr = sb.toString();
        //统计词频
        Map<String, Integer> sentimenRes = statisticalUtil.statisticalWords(sentimentStr);
        //遍历Map给数字结果的情感极性替换成正常标签
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
                //组成标准Echarts的name，value数据格式
                tempMap.put("name", key);
                tempMap.put("value", String.valueOf(v));
                resList.add(tempMap);
            }
        });
        resMap.put("status","OK");
        resMap.put("message","数据获取成功");
        if (resList.size()!=0)
            resMap.put("data",resList);
        return resMap;
    }
}
