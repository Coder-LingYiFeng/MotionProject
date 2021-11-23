package com.detection.motion.controller;

import com.detection.motion.bean.Device;
import com.detection.motion.bean.Sentence;
import com.detection.motion.service.DeviceService;
import com.detection.motion.service.SentenceService;
import com.detection.motion.utils.ParticipleUtil;
import com.detection.motion.utils.StatisticalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 词云数据接口
 */
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


    /**
     * 获取时间区间内词云信息及语句信息（可二次处理）
     * @param device 必要参数 startTime endTime createUserId name
     * @return 返回词云数据及原语句信息（用于二次处理）
     */
    @PostMapping("/getWordCloudByTimeSection")
    public Map<String,Object> getWordCloud(@RequestBody Device device){
        HashMap<String, Object> resMap = new HashMap<>();
        //参数获取及校验
        String startTime = device.getStartTime();
        String endTime = device.getEndTime();
//        System.out.println(startTime);
//        System.out.println(endTime);
//        System.out.println(device);
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

        //判断设备是否存在
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        ArrayList<Map<String, String>> resList = new ArrayList<>();
        if(deviceInfo==null){
            resMap.put("status","ERROR");
            resMap.put("message","无此设备");
            return resMap;
        }
        //获取设备id（sentence表关联此id）
        Integer deviceId = deviceInfo.getId();
//        System.out.println(deviceId);
        //获取语句信息
        ArrayList<Sentence> sentenceInfoList = sentenceService.selectSentenceBytimeSection(deviceId,startTime,endTime);
//        sentenceInfoList.forEach(System.out::println);
        if (sentenceInfoList.size()==0){
            resMap.put("status","ERROR");
            resMap.put("message","无数据，请调大时间差,并检查是否存在该设备相关语句信息");
            return resMap;
        }
        //拼接语句信息为字符串
        StringBuilder sb = new StringBuilder();
        sentenceInfoList.forEach(sentenceInfo -> sb.append(sentenceInfo.getSentence()));
        String sentences = sb.toString();
//        Map<String, Integer> participleRes = participleUtil.getParticipleRes(sentences);
        //对拼接后的语句信息进行分词及词频统计
        Map<String, Integer> participleRes = statisticalUtil.statisticalWords(participleUtil.getParticipleRes(sentences));
        //组成标准Echarts词云的数据格式
        participleRes.forEach((k, v) -> {
            HashMap<String, String> tempMap = new HashMap<>();
            tempMap.put("name", k);
            tempMap.put("value", String.valueOf(v));
            resList.add(tempMap);
        });
        resMap.put("status","OK");
        resMap.put("message","数据获取成功");
        //原始语句信息返回
        resMap.put("sentence",sentenceInfoList);
        System.out.println("resList.size() = " + resList.size());
        if (resList.size()!=0)
            resMap.put("data",resList);
        return resMap;
    }
}
