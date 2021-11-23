package com.detection.motion.service.impl;

import com.detection.motion.bean.DeviceGps;
import com.detection.motion.mapper.DeviceGpsMapper;
import com.detection.motion.service.DeviceGpsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 实现DeviceGpsService接口，调用DeviceGpsMapper方法实现数据库增删改查
 */
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

    @Override
    public DeviceGps getLastInfo(Integer deviceId) {
        return deviceGpsMapper.getLastInfo(deviceId);
    }
}