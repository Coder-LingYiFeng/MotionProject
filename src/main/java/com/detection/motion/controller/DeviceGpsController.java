package com.detection.motion.controller;

import com.detection.motion.bean.Device;
import com.detection.motion.bean.DeviceGps;
import com.detection.motion.bean.Status;
import com.detection.motion.service.DeviceGpsService;
import com.detection.motion.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/dataAPI")
public class DeviceGpsController {


    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceGpsService deviceGpsService;

    @PostMapping("/getDeviceGpsByTimeSection")
    public Status getDeviceGpsByTimeSection(@RequestBody Device device) {
        String startTime = device.getStartTime();
        String endTime = device.getEndTime();
//        System.out.println(startTime);
//        System.out.println(endTime);
//        System.out.println(device);
        Integer createUserId = device.getCreateUserId();
        String name = device.getName();
        if (createUserId == null)
            return new Status("ERROR","createUserId为空",null);

        if (name == null || "".equals(name))
            return new Status("ERROR","设备名为空",null);

        if (startTime == null || "".equals(startTime))
            return new Status("ERROR","查询开始时间为空",null);

        if (endTime == null || "".equals(endTime))
            return new Status("ERROR","查询结束时间为空",null);

        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        if (deviceInfo == null)
            return new Status("ERROR","无此设备",null);

        Integer deviceId = deviceInfo.getId();
        ArrayList<DeviceGps> deviceGpsInfoList;
        try {
            deviceGpsInfoList = deviceGpsService.selectDeviceGpsBytimeSection(deviceId, startTime, endTime);
        }catch (Exception e){
            return new Status("ERROR","数据查询失败",null);
        }
        if (deviceGpsInfoList.size()==0)
            return new Status("ERROR","无数据，时间差过小",null);

        return new Status("OK","数据获取成功",deviceGpsInfoList);

    }

    @PostMapping("/getLastInfo")
    public Status getLastInfo(@RequestBody Device device){
        Integer deviceId = device.getId();
        if (deviceId == null)
            return new Status("ERROR","deviceId参数缺失",null);
        DeviceGps lastInfo = deviceGpsService.getLastInfo(deviceId);
        if (lastInfo==null)
            return new Status("ERROR","数据库无相关数据",null);
        return new Status("OK","数据获取成功",lastInfo);


    }
}
