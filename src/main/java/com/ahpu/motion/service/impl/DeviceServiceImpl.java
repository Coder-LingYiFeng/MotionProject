package com.ahpu.motion.service.impl;

import com.ahpu.motion.bean.Device;
import com.ahpu.motion.mapper.DeviceMapper;
import com.ahpu.motion.service.DeviceService;
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
    public List<Device> getAllByCreateUserId(Integer createUserId) {
        return deviceMapper.getAllByCreateUserId(createUserId);
    }

    @Override
    public List<Device> getAllDeviceInfo() {
        return deviceMapper.getAllDeviceInfo();
    }
}
