package com.detection.motion.controller;

import com.detection.motion.bean.Device;
import com.detection.motion.bean.DeviceGps;
import com.detection.motion.bean.Sentence;
import com.detection.motion.bean.Status;
import com.detection.motion.service.DeviceGpsService;
import com.detection.motion.service.DeviceService;
import com.detection.motion.service.SentenceService;
import com.detection.motion.utils.MotionUtil;
import com.detection.motion.utils.PahoUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 提供给树莓派的接口
 */
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

    /**
     * 向数据库插入语句信息
     * @param piSentence 必要参数 deviceId sentence
     * @return 状态信息及语句分析得到的结果信息
     */
    @PostMapping("/insertSentence")
    public Status insertSentence(@RequestBody Sentence piSentence) {
        //参数获取及校验
        Integer deviceId = piSentence.getDeviceId();
//        System.out.println("deviceId = " + deviceId);
        String sentence = piSentence.getSentence();
//        System.out.println("sentence = " + sentence);
        if (deviceId == null)
            return new Status("ERROR", "设备id为空", null);
        if (sentence==null||"".equals(sentence))
            return new Status("ERROR", "语句内容为空", null);
        //获取设备信息，判断是否存在该设备
        Device deviceInfoById = deviceService.getById(deviceId);
        if (deviceInfoById==null)
            return new Status("ERROR", "插入数据库失败，无此设备", null);
        //获取mqttpub参数用于mqtt通信
        String mqttPub= deviceInfoById.getMqttPub();

        //获取情感分析的结果
        String motionStr = motionUtil.getResult(sentence).toString();
        //转成json处理
        JSONObject motionJson = JSONObject.parseObject(motionStr);
        //取josn内的数据
        JSONArray itemsArray = motionJson.getJSONArray("items");
        JSONObject itemJson = itemsArray.getJSONObject(0);
        Integer sentiment = itemJson.getInteger("sentiment");
        Double confidence = itemJson.getDouble("confidence");
        Double positiveProb = itemJson.getDouble("positive_prob");
        Double negativeProb = itemJson.getDouble("negative_prob");
        //创建一个sentence类型的对象用于返回信息
        Sentence sentenceInfo = new Sentence(deviceId, sentence, sentiment, confidence, positiveProb, negativeProb);
        System.out.println("sentenceInfo = " + sentenceInfo);
        try {
            //插入数据库
            sentenceService.insertSentence(deviceId, sentence, sentiment, confidence, positiveProb, negativeProb);
        } catch (Exception e) {
            System.out.println("Exception = " + e);
            return new Status("ERROR", "插入数据库失败，无此设备", null);
        }
        //构造Map用于保存信息转换为json通过mqtt通知前端更新数据
        HashMap<String,Object> sentenceInfoMap=new HashMap<>();
        sentenceInfoMap.put("deviceId", deviceId);
        sentenceInfoMap.put("sentence",sentence);
        sentenceInfoMap.put("sentiment", sentiment);
        sentenceInfoMap.put("confidence",confidence);
        sentenceInfoMap.put("positiveProb",positiveProb);
        sentenceInfoMap.put("negativeProb",negativeProb);
        sentenceInfoMap.put("type","sentenceInfo");
        //使用mqtt服务器通知前端
        pahoUtil.sendMessage(mqttPub,new JSONObject(sentenceInfoMap));
        return new Status("OK", "插入数据库成功", sentenceInfo);
    }

    /**
     * 插入设备GPS信息
     * @param deviceGps 必要参数 deviceId latitude longitude
     * @return 状态信息及GPS信息
     */
    @PostMapping("/insertGps")
    public Status insertGps(@RequestBody DeviceGps deviceGps){
        //参数获取及校验
        Integer deviceId = deviceGps.getDeviceId();
        String latitude = deviceGps.getLatitude();
        String longitude = deviceGps.getLongitude();
        if (deviceId == null)
            return new Status("ERROR","deviceId为空",null);
        if (latitude == null)
            return new Status("ERROR","latitude为空",null);
        if (longitude==null)
            return new Status("ERROR","longitude为空",null);
        try {
            //插入数据库
            deviceGpsService.insertDeviceGps(deviceId,longitude,latitude);
        }catch (Exception e){
            return new Status("ERROR", "插入数据库失败", null);
        }
        return new Status("OK","插入成功",deviceGps);
    }

}
