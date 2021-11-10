package com.ahpu.motion.controller;

import com.ahpu.motion.bean.Sentence;
import com.ahpu.motion.bean.Status;
import com.ahpu.motion.service.SentenceService;
import com.ahpu.motion.utils.MotionUtil;
import com.ahpu.motion.utils.PahoUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RaspberryPiController {

    @Autowired
    SentenceService sentenceService;

    @Autowired
    MotionUtil motionUtil;

    @Autowired
    PahoUtil pahoUtil;

    @PostMapping("/insertSentence")
    public Status insertSentence(@RequestBody Sentence piSentence) {
        Integer deviceId = piSentence.getDeviceId();
//        System.out.println("deviceId = " + deviceId);
        String sentence = piSentence.getSentence();
//        System.out.println("sentence = " + sentence);
        String mqttPub = piSentence.getMqttPub();
//        System.out.println("mqttPub = " + mqttPub);
        if ("".equals(mqttPub))
            return new Status("ERROR", "mqttPub为空", "");
        if ("".equals(sentence))
            return new Status("ERROR", "语句内容为空", "");
        if (deviceId == null)
            return new Status("ERROR", "设备id为空", "");
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
            return new Status("ERROR", "插入数据库失败，可能为外键约束错误，请检查数据库中deviceId关联的user('id')是否存在！", "");
        }
        pahoUtil.sendMessage(mqttPub,sentenceInfo);
        return new Status("OK", "插入数据库成功", sentenceInfo);
    }

}
