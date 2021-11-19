package com.ahpu.motion.service.impl;

import com.ahpu.motion.bean.DeviceGps;
import com.ahpu.motion.mapper.DeviceGpsMapper;
import com.ahpu.motion.service.DeviceGpsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DeviceGpsServiceImpl extends ServiceImpl<DeviceGpsMapper, DeviceGps> implements DeviceGpsService {

    @Autowired
    DeviceGpsMapper deviceGpsMapper;

    @Override
    public void insertDeviceGps(Integer deviceId, String longitude, String latitude) {
        deviceGpsMapper.insertDeviceGps(deviceId,longitude,latitude);
    }

    @Override
    public ArrayList<DeviceGps> selectDeviceGpsBytimeSection(Integer deviceId, String startTime, String endTime) {
        return deviceGpsMapper.selectDeviceGpsBytimeSection(deviceId,startTime,endTime);
    }
}