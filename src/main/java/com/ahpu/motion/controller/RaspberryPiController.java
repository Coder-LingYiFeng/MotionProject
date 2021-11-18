package com.ahpu.motion.controller;

import com.ahpu.motion.bean.Device;
import com.ahpu.motion.bean.DeviceGps;
import com.ahpu.motion.bean.Sentence;
import com.ahpu.motion.bean.Status;
import com.ahpu.motion.service.DeviceGpsService;
import com.ahpu.motion.service.DeviceService;
import com.ahpu.motion.service.SentenceService;
import com.ahpu.motion.utils.MotionUtil;
import com.ahpu.motion.utils.PahoUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/raspberryPiAPI")
public class RaspberryPiController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    SentenceService sentenceService;

    @Autowired
    MotionUtil motionUtil;

    @Autowired
    PahoUtil pahoUtil;

    @Autowired
    DeviceGpsService deviceGpsService;

    @PostMapping("/insertSentence")
    public Status insertSentence(@RequestBody Sentence piSentence) {
        Integer deviceId = piSentence.getDeviceId();
//        System.out.println("deviceId = " + deviceId);
        String sentence = piSentence.getSentence();
//        System.out.println("sentence = " + sentence);
        if (deviceId == null)
            return new Status("ERROR", "设备id为空", null);
        if (sentence==null||"".equals(sentence))
            return new Status("ERROR", "语句内容为空", null);
        Device deviceInfoById = deviceService.getById(deviceId);
        if (deviceInfoById==null)
            return new Status("ERROR", "插入数据库失败，无此设备", null);
        String mqttPub= deviceInfoById.getMqttPub();

        String motionStr = motionUtil.getResult(sentence).toString();
        JSONObject motionJson = JSONObject.parseObject(motionStr);
        JSONArray itemsArray = motionJson.getJSONArray("items");
        JSONObject itemJson = itemsArray.getJSONObject(0);
        Integer sentiment = itemJson.getInteger("sentiment");
        Double confidence = itemJson.getDouble("confidence");
        Double positiveProb = itemJson.getDouble("positive_prob");
        Double negativeProb = itemJson.getDouble("negative_prob");
        Sentence sentenceInfo = new Sentence(deviceId, sentence, sentiment, confidence, positiveProb, negativeProb);
        System.out.println("sentenceInfo = " + sentenceInfo);
        try {
            sentenceService.insertSentence(deviceId, sentence, sentiment, confidence, positiveProb, negativeProb);
        } catch (Exception e) {
            System.out.println("Exception = " + e);
            return new Status("ERROR", "插入数据库失败，无此设备", null);
        }
        HashMap<String,Object> sentenceInfoMap=new HashMap<>();
        sentenceInfoMap.put("deviceId", deviceId);
        sentenceInfoMap.put("sentence",sentence);
        sentenceInfoMap.put("sentiment", sentiment);
        sentenceInfoMap.put("confidence",confidence);
        sentenceInfoMap.put("positiveProb",positiveProb);
        sentenceInfoMap.put("negativeProb",negativeProb);
        sentenceInfoMap.put("type","sentenceInfo");
        pahoUtil.sendMessage(mqttPub,new JSONObject(sentenceInfoMap));
        return new Status("OK", "插入数据库成功", sentenceInfo);
    }

    @PostMapping("/insertGps")
    public Status insertGps(@RequestBody DeviceGps deviceGps){
        Integer deviceId = deviceGps.getDeviceId();
        Float latitude = deviceGps.getLatitude();
        Float longitude = deviceGps.getLongitude();
        if (deviceId == null)
            return new Status("ERROR","deviceId为空",null);
        if (latitude == null)
            return new Status("ERROR","latitude为空",null);
        if (longitude==null)
            return new Status("ERROR","longitude为空",null);
        try {
            deviceGpsService.insertDeviceGps(deviceId,latitude,longitude);
        }catch (Exception e){
            System.out.println(e);
            return new Status("ERROR", "插入数据库失败", null);
        }
        return new Status("OK","插入成功",deviceGps);
    }

}
