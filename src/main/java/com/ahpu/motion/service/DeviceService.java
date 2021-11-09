package com.ahpu.motion.service;

import com.ahpu.motion.bean.Device;
import com.ahpu.motion.mapper.DeviceMapper;
import com.ahpu.motion.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface DeviceService extends IService<Device> {
    Device getDeviceByName(String name);

    Device getDeviceByNameAndId(String name,Integer createUserId);

    boolean deleteByNameAndId(String name,Integer createUserId);

    List<Device> getAllByCreateUserId(Integer createUserId);


}