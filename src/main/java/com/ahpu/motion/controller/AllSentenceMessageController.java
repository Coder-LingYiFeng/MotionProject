package com.ahpu.motion.controller;

import com.ahpu.motion.bean.Device;
import com.ahpu.motion.bean.Sentence;
import com.ahpu.motion.bean.Status;
import com.ahpu.motion.mapper.DeviceMapper;
import com.ahpu.motion.mapper.SentenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AllSentenceMessageController {

    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    SentenceMapper sentenceMapper;

    @PostMapping("/getAllSentenceMessageByDeviceId")
    public Status getAllSentenceMessageByDeviceId(@RequestBody Device device){
        String name = device.getName();
        Integer createUserId = device.getCreateUserId();
        if (name==null||"".equals(name))
            return new Status("ERROR","设备名为空",null);
        if (createUserId == null)
            return new Status("ERROR","设备ID为空",null);
        Device deviceInfo = deviceMapper.getDeviceByNameAndId(name, createUserId);
        if (deviceInfo==null)
            return new Status("ERROR","设备不存在",null);
        Integer deviceId = deviceInfo.getId();
        ArrayList<Sentence> sentences = sentenceMapper.selectAllSentenceMessageByDeviceId(deviceId);
        return new Status("OK","数据查询成功",sentences);
    }
}
