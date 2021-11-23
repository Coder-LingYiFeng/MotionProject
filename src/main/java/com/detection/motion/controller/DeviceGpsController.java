package com.detection.motion.controller;

import com.detection.motion.bean.Device;
import com.detection.motion.bean.DeviceGps;
import com.detection.motion.bean.Status;
import com.detection.motion.service.DeviceGpsService;
import com.detection.motion.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * 设备GPS信息的接口
 */
@RestController
@RequestMapping("/dataAPI")
public class DeviceGpsController {


    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceGpsService deviceGpsService;

    /**
     * 时间区间内获取设备的GPS信息
     * @param device 必要参数 startTime endTime createUserId name
     * @return 返回该时间区间内设备的GPS信息
     */
    @PostMapping("/getDeviceGpsByTimeSection")
    public Status getDeviceGpsByTimeSection(@RequestBody Device device) {
        //获取前端传来的参数
        String startTime = device.getStartTime();
        String endTime = device.getEndTime();
//        System.out.println(startTime);
//        System.out.println(endTime);
//        System.out.println(device);
        Integer createUserId = device.getCreateUserId();
        String name = device.getName();
        //校验参数合法性
        if (createUserId == null)
            return new Status("ERROR","createUserId为空",null);

        if (name == null || "".equals(name))
            return new Status("ERROR","设备名为空",null);

        if (startTime == null || "".equals(startTime))
            return new Status("ERROR","查询开始时间为空",null);

        if (endTime == null || "".equals(endTime))
            return new Status("ERROR","查询结束时间为空",null);

        //查询该用户是否用于该设备
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
        if (deviceInfo == null)
            return new Status("ERROR","无此设备",null);

        //获取设备的deviceId信息
        Integer deviceId = deviceInfo.getId();
        ArrayList<DeviceGps> deviceGpsInfoList;
        try {
            //获取该设备在startTime到endTime区间内的GPS信息
            deviceGpsInfoList = deviceGpsService.selectDeviceGpsBytimeSection(deviceId, startTime, endTime);
        }catch (Exception e){
            return new Status("ERROR","数据查询失败",null);
        }
        if (deviceGpsInfoList.size()==0)
            return new Status("ERROR","无数据，时间差过小",null);

        return new Status("OK","数据获取成功",deviceGpsInfoList);

    }

    /**
     * 获取设备最后一次GPS信息
     * @param device 必要参数deviceId
     * @return 设备最后一次GPS信息
     */
    @PostMapping("/getLastInfo")
    public Status getLastInfo(@RequestBody Device device){
        //从前端获取参数
        Integer deviceId = device.getId();
        //校验参数合法性
        if (deviceId == null)
            return new Status("ERROR","deviceId参数缺失",null);
        //获取该设备最后一次的GPS信息
        DeviceGps lastInfo = deviceGpsService.getLastInfo(deviceId);
        if (lastInfo==null)
            return new Status("ERROR","数据库无相关数据",null);
        return new Status("OK","数据获取成功",lastInfo);


    }
}
