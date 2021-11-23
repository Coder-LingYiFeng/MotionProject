package com.detection.motion.service.impl;

import com.detection.motion.bean.Device;
import com.detection.motion.mapper.DeviceMapper;
import com.detection.motion.service.DeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public Device getDeviceByName(String name) {
        return deviceMapper.getDeviceByName(name);
    }

    @Override
    public Device getDeviceByNameAndId(String name, Integer createUserId) {
        return deviceMapper.getDeviceByNameAndId(name,createUserId);
    }

    @Override
    public boolean deleteByNameAndId(String name, Integer createUserId) {
        return deviceMapper.deleteByNameAndId(name,createUserId);
    }

    @Override
    public boolean updateDeviceById(Integer id, String name, Integer createUserId, String scribe, String mqttSub, String mqttPub) {
        return deviceMapper.updateDeviceById(id,name,createUserId,scribe,mqttSub,mqttPub);
    }


    @Override
    public List<Device> getAllByCreateUserId(Integer createUserId) {
        return deviceMapper.getAllByCreateUserId(createUserId);
    }

    @Override
    public List<Device> getAllDeviceInfo() {
        return deviceMapper.getAllDeviceInfo();
    }
}
