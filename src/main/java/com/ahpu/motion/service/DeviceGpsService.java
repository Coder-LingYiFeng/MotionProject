package com.ahpu.motion.service;

import com.ahpu.motion.bean.DeviceGps;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

public interface DeviceGpsService extends IService<DeviceGps> {
    void insertDeviceGps(Integer deviceId,String longitude,String latitude);

    ArrayList<DeviceGps> selectDeviceGpsBytimeSection(Integer deviceId, String startTime, String endTime);

    DeviceGps getLastInfo(Integer deviceId);

}