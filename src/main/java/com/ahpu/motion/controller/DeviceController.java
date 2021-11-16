package com.ahpu.motion.controller;

import com.ahpu.motion.bean.Device;
import com.ahpu.motion.bean.Status;
import com.ahpu.motion.bean.User;
import com.ahpu.motion.service.DeviceService;
import com.ahpu.motion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/deviceRecord")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    @PostMapping("/addDevice")
    public Status addDevice(@RequestBody Device device){
        String name = device.getName();
        Integer createUserId = device.getCreateUserId();
        String scribe = device.getScribe();
        String mqttPub = device.getMqttPub();
        String mqttSub = device.getMqttSub();
        if (userService.getById(createUserId)==null)
            return new Status("ERROR","设备管理者不存在",null);
        if (name==null||"".equals(name))
            return new Status("ERROR","设备名为空",null);
        if (createUserId == null)
            return new Status("ERROR","设备ID为空",null);
        if (mqttPub == null||"".equals(mqttPub))
            return new Status("ERROR","设备mqttPub为空",null);
        if (mqttSub == null||"".equals(mqttSub))
            return new Status("ERROR","设备mqttSub为空",null);

        Device deviceInfo = deviceService.getDeviceByNameAndId(name,createUserId);
        if (deviceInfo!=null)
            return new Status("ERROR","设备名已存在",null);
        HashSet<String> mqttPubSet=new HashSet<>();
        HashSet<String> mqttSubSet=new HashSet<>();

        List<Device> allDeviceInfo = deviceService.getAllDeviceInfo();
        allDeviceInfo.forEach(info->{
            mqttPubSet.add(info.getMqttPub());
            mqttSubSet.add(info.getMqttSub());
        });
        if (!mqttPubSet.add(mqttPub))
            return new Status("ERROR","mqttPub已存在",null);
        if (!mqttSubSet.add(mqttSub))
            return new Status("ERROR","mqttSub已存在",null);
        deviceService.save(device);
        return new Status("OK","设备添加成功",device);
    }

    @PostMapping("/deleteDevice")
    public Status deleteDevice(@RequestBody Device device){
        String name = device.getName();
//        System.out.println("name = " + name);
        Integer createUserId = device.getCreateUserId();
//        System.out.println("createUserId = " + createUserId);
        if (name==null||"".equals(name))
            return new Status("ERROR","设备名为空",null);
        if (createUserId == null)
            return new Status("ERROR","设备ID为空",null);

        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
//        System.out.println("deviceInfo = " + deviceInfo);
        if (deviceInfo==null)
            return new Status("ERROR","设备不存在",null);

        deviceService.deleteByNameAndId(name,createUserId);
        return new Status("OK","设备移除成功",deviceInfo);
    }

    @PostMapping("/updateDevice")
    public Status updateDevice(@RequestBody Device device){
        Integer id = device.getId();
        if (id == null)
            new Status("ERROR","参数id为空",null);
        String name = device.getName();
        if (name==null||"".equals(name))
            new Status("ERROR","参数name为空",null);
        Integer createUserId = device.getCreateUserId();
        if (createUserId == null)
            new Status("ERROR","参数createUserId为空",null);
        String scribe = device.getScribe();
        if (scribe==null||"".equals(scribe))
            new Status("ERROR","参数scribe为空",null);
        String mqttSub = device.getMqttSub();
        if (mqttSub==null||"".equals(mqttSub))
            new Status("ERROR","参数mqttSub为空",null);
        String mqttPub = device.getMqttPub();
        if (mqttPub==null||"".equals(mqttPub))
            new Status("ERROR","参数mqttPub为空",null);
        try {
            deviceService.updateDeviceById(id,name,createUserId,scribe,mqttSub,mqttPub);
            return new Status("OK","设备信息更新成功",device);
        }catch (Exception e){
            return new Status("ERROR","服务器错误",null);
        }
    }

    @GetMapping("/getAllDevice")
    public Status getAllDevice(@RequestParam(defaultValue = "0") Integer createUserId){
        if (createUserId==0)
            return new Status("ERROR","createUserId参数未传",null);
        User userInfo = userService.getById(createUserId);
        if (userInfo==null)
            return new Status("ERROR","用户不存在",null);
        List<Device> allDeviceInfo = deviceService.getAllByCreateUserId(createUserId);
        int count = allDeviceInfo.size();
//        allDeviceInfo.forEach(System.out::println);
        return new Status("OK",count+"个设备信息获取成功",allDeviceInfo);

    }
}
