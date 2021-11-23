package com.detection.motion.service;

import com.detection.motion.bean.Device;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DeviceService extends IService<Device> {
    Device getDeviceByName(String name);

    Device getDeviceByNameAndId(String name,Integer createUserId);

    boolean deleteByNameAndId(String name,Integer createUserId);

    boolean updateDeviceById(Integer id,String name,Integer createUserId,String scribe,String mqttSub,String mqttPub);

    List<Device> getAllByCreateUserId(Integer createUserId);

    List<Device> getAllDeviceInfo();

}
