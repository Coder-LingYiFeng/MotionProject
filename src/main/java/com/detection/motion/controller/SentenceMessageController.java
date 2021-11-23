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


/**
 * 语句信息表的接口
 */
@RestController
@RequestMapping("/dataAPI")
public class SentenceMessageController {

    @Autowired
    DeviceService deviceService;
    @Autowired
    SentenceService sentenceService;

    /**
     * 获取设备的所有语句信息
     * @param device 必要参数 name createUserId
     * @return 返回所有语句信息
     */
    @PostMapping("/getAllSentenceMessageByDeviceId")
    public Status getAllSentenceMessageByDeviceId(@RequestBody Device device) {
        //参数获取及校验
        String name = device.getName();
        Integer createUserId = device.getCreateUserId();
        if (name == null || "".equals(name))
            return new Status("ERROR", "设备名为空", null);
        if (createUserId == null)
            return new Status("ERROR", "参数createUserId为空", null);
        //获取设备信息，判断设备是否存在
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        if (deviceInfo == null)
            return new Status("ERROR", "设备不存在", null);
        //获取设备id(sentence表关联此id)
        Integer deviceId = deviceInfo.getId();
        //获取该设备所有的语句信息
        ArrayList<Sentence> sentences = sentenceService.selectAllSentenceMessageByDeviceId(deviceId);
        return new Status("OK", "数据查询成功", sentences);
    }

    /**
     * 获取该设备最新24小时内的语句信息
     * @param device 必要参数 name createUserId
     * @return 状态信息及该设备最新24小时的语句信息
     */
    @PostMapping("getSentenceByDeviceIdOnOneday")
    public Status getSentenceByDeviceIdOnOneday(@RequestBody Device device) {
        //参数获取及校验
        String name = device.getName();
        Integer createUserId = device.getCreateUserId();
        if (name == null || "".equals(name))
            return new Status("ERROR", "设备名为空", null);
        if (createUserId == null)
            return new Status("ERROR", "参数createUsrId为空", null);
        //判断设备是否存在
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        if (deviceInfo == null)
            return new Status("ERROR", "设备不存在", null);
        //获取设备id
        Integer deviceId = deviceInfo.getId();

        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取当前日期
        Calendar calendar = Calendar.getInstance();
        Date currentTime  = calendar.getTime();
        //获取24小时前的日期
        calendar.add(Calendar.HOUR, -24);
        Date yesterdayCurrentTime = calendar.getTime();
        String yesterdayCurrentTimeStr = sdf.format(yesterdayCurrentTime);
        //获取从24小时前到现在的所有语句信息
        ArrayList<Sentence> sentencesOnOneDayList = sentenceService.getSentenceByDeviceIdOnOneday(deviceId, yesterdayCurrentTimeStr);
        System.out.println("sentencesOnOneDayList.size() = " + sentencesOnOneDayList.size());
        //保存时间段内的语句条数
        ArrayList<HashMap<Date, Integer>> resList = new ArrayList<>();
        //循环单位小时区间
        for (int j = 0; j < 24; j++) {
            //零时保存信息
            HashMap<Date, Integer> tempMap = new HashMap<>();
            //每次循环都设置初始时间为当前时间
            Calendar calendar_ = Calendar.getInstance();
            calendar_.setTime(currentTime);
            //将当前时间向前推j-24小时为查询开始时间（即startTime从24小时前到23小时前到22小时前以此类推）
            calendar_.add(Calendar.HOUR,j-24);
            Date startTime=calendar_.getTime();
            //初始化此时间区间语句数量为0
            tempMap.put(startTime,0);
//            System.out.println("startTime = " + startTime);
            //设置开始时间后的一小时为查询结束时间
            calendar_.add(Calendar.HOUR,1);
            Date endTime=calendar_.getTime();
//            System.out.println("endTime = " + endTime);

            //循环语句列表
            for (Sentence sentence : sentencesOnOneDayList) {
                //获取单条语句的时间信息
                Date sentenceTime = sentence.getDate();
                //判断时间是否在当前循环的一小时区间内，不存在则判断下一条数据
                if (!(sentenceTime.after(startTime) && sentenceTime.before(endTime)))
                    continue;
                //存在，则该时间区间内语句条数加1
                tempMap.put(startTime, tempMap.get(startTime) + 1);
//                System.out.println("addMap ==================+1 " + sentencesOnOneDayList.get(i).getId());
            }
//            System.out.println("+++++++++++++++++++++++++++++++++"+j);
            //每次时间区间的结果放到结果列表内
            resList.add(tempMap);
        }
        //返回信息
        HashMap<String,Object> resMap=new HashMap<>();
        resMap.put("AllSentenceOneDay",sentencesOnOneDayList);
        resMap.put("echatsData",resList);
        return new Status("OK", "最新24小时内的单位时间信息量", resMap);


    }
}
