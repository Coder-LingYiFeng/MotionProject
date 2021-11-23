package com.detection.motion.controller;

import com.detection.motion.bean.Device;
import com.detection.motion.bean.Sentence;
import com.detection.motion.bean.Status;
import com.detection.motion.service.DeviceService;
import com.detection.motion.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/dataAPI")
public class SentenceMessageController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    SentenceService sentenceService;

    @PostMapping("/getAllSentenceMessageByDeviceId")
    public Status getAllSentenceMessageByDeviceId(@RequestBody Device device) {
        String name = device.getName();
        Integer createUserId = device.getCreateUserId();
        if (name == null || "".equals(name))
            return new Status("ERROR", "设备名为空", null);
        if (createUserId == null)
            return new Status("ERROR", "参数createUserId为空", null);
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        if (deviceInfo == null)
            return new Status("ERROR", "设备不存在", null);
        Integer deviceId = deviceInfo.getId();
        ArrayList<Sentence> sentences = sentenceService.selectAllSentenceMessageByDeviceId(deviceId);
        return new Status("OK", "数据查询成功", sentences);
    }

    @PostMapping("getSentenceByDeviceIdOnOneday")
    public Status getSentenceByDeviceIdOnOneday(@RequestBody Device device) {
        String name = device.getName();
        Integer createUserId = device.getCreateUserId();
        if (name == null || "".equals(name))
            return new Status("ERROR", "设备名为空", null);
        if (createUserId == null)
            return new Status("ERROR", "参数createUsrId为空", null);
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        if (deviceInfo == null)
            return new Status("ERROR", "设备不存在", null);
        Integer deviceId = deviceInfo.getId();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date currentTime  = calendar.getTime();
        calendar.add(Calendar.HOUR, -24);
        Date yesterdayCurrentTime = calendar.getTime();
        String yyesterdayCurrentTimeStr = sdf.format(yesterdayCurrentTime);
        ArrayList<Sentence> sentencesOnOneDayList = sentenceService.getSentenceByDeviceIdOnOneday(deviceId, yyesterdayCurrentTimeStr);
        System.out.println("sentencesOnOneDayList.size() = " + sentencesOnOneDayList.size());
        ArrayList<HashMap<Date, Integer>> resList = new ArrayList<>();


        for (int j = 0; j < 24; j++) {
            HashMap<Date, Integer> tempMap = new HashMap<>();
            Calendar calendar_ = Calendar.getInstance();
            calendar_.setTime(currentTime);
            calendar_.add(Calendar.HOUR,j-24);
            Date startTime=calendar_.getTime();
            tempMap.put(startTime,0);
//            System.out.println("startTime = " + startTime);
            calendar_.add(Calendar.HOUR,1);
            Date endTime=calendar_.getTime();
//            System.out.println("endTime = " + endTime);

            for (Sentence sentence : sentencesOnOneDayList) {
                Date sentenceTime = sentence.getDate();
                if (!(sentenceTime.after(startTime) && sentenceTime.before(endTime)))
                    continue;
                tempMap.put(startTime, tempMap.get(startTime) + 1);
//                System.out.println("addMap ==================+1 " + sentencesOnOneDayList.get(i).getId());
            }
//            System.out.println("+++++++++++++++++++++++++++++++++"+j);
            resList.add(tempMap);
        }
        HashMap<String,Object> resMap=new HashMap<>();
        resMap.put("AllSentenceOneDay",sentencesOnOneDayList);
        resMap.put("echatsData",resList);
        return new Status("OK", "最新24小时内的单位时间信息量", resMap);


    }
}
