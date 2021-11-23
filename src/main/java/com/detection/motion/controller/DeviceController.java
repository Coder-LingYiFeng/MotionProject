package com.detection.motion.controller;

import com.detection.motion.bean.Device;
import com.detection.motion.bean.Status;
import com.detection.motion.bean.User;
import com.detection.motion.service.DeviceService;
import com.detection.motion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

/**
 * 设备表相关操作的接口
 */
@RestController
@RequestMapping("/deviceRecord")
public class DeviceController {

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    /**
     * 添加设备
     * @param device 必要参数name createUserId mqttPub mqttSub
     * @return 返回状态信息及添加的设备信息
     */
    @PostMapping("/addDevice")
    public Status addDevice(@RequestBody Device device){
        //获取前端传来的参数
        String name = device.getName();
        Integer createUserId = device.getCreateUserId();
        String mqttPub = device.getMqttPub();
        String mqttSub = device.getMqttSub();
        //以上参数为必须参数，判断参数合法性
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

        //查询该用户下是否存在该设备
        Device deviceInfo = deviceService.getDeviceByNameAndId(name,createUserId);
        //存在
        if (deviceInfo!=null)
            return new Status("ERROR","设备名已存在",null);
        //map保存mqttSub及mqttPub信息
        HashSet<String> mqttPubSet=new HashSet<>();
        HashSet<String> mqttSubSet=new HashSet<>();

        //得到设备表所有的信息
        List<Device> allDeviceInfo = deviceService.getAllDeviceInfo();
        //将mqttPub和mqttSub所有信息分别放到两个map
        allDeviceInfo.forEach(info->{
            mqttPubSet.add(info.getMqttPub());
            mqttSubSet.add(info.getMqttSub());
        });
        //判断传入的mqttSub和mqttPub是否在数据库存在（mqttSub和mqttPub全表唯一）
        if (!mqttPubSet.add(mqttPub))
            return new Status("ERROR","mqttPub已存在",null);
        if (!mqttSubSet.add(mqttSub))
            return new Status("ERROR","mqttSub已存在",null);
        //保存设备信息到数据库
        deviceService.save(device);
        return new Status("OK","设备添加成功",device);
    }


    /**
     * 删除设备信息
     * @param device 必要参数name createUserId
     * @return 返回状态信息及删除设备的信息
     */
    @PostMapping("/deleteDevice")
    public Status deleteDevice(@RequestBody Device device){
        //获取前端传来的参数
        String name = device.getName();
        Integer createUserId = device.getCreateUserId();
        //判断参数合法性
        if (name==null||"".equals(name))
            return new Status("ERROR","设备名为空",null);
        if (createUserId == null)
            return new Status("ERROR","设备ID为空",null);

        //查找设备
        Device deviceInfo = deviceService.getDeviceByNameAndId(name, createUserId);
//        System.out.println("deviceInfo = " + deviceInfo);
        if (deviceInfo==null)
            return new Status("ERROR","设备不存在",null);

        //从数据库删除设备
        deviceService.deleteByNameAndId(name,createUserId);
        return new Status("OK","设备移除成功",deviceInfo);
    }

    /**
     * 更新设备信息
     * @param device 必要参数id name createUserId scribe mqttSub mqttPub（无需更新的设备信息传入原来的数据）
     * @return 返回状态信息及更新的设备信息
     */
    @PostMapping("/updateDevice")
    public Status updateDevice(@RequestBody Device device){
        //从前端获取参数及判断合法性
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
        //更新设备信息
        try {
            deviceService.updateDeviceById(id,name,createUserId,scribe,mqttSub,mqttPub);
            return new Status("OK","设备信息更新成功",device);
        }catch (Exception e){
            return new Status("ERROR","服务器错误",null);
        }
    }

    /**
     * 获取该用户所有的设备信息
     * @param createUserId 必要参数 createUserId
     * @return 返回设备列表
     */
    @GetMapping("/getAllDevice")
    public Status getAllDevice(@RequestParam(defaultValue = "0") Integer createUserId){
        //判断参数合法性
        if (createUserId==0)
            return new Status("ERROR","createUserId参数未传",null);
        //判断用户是否存在
        User userInfo = userService.getById(createUserId);
        if (userInfo==null)
            return new Status("ERROR","用户不存在",null);
        //获取该用户下所有的设备信息
        List<Device> allDeviceInfo = deviceService.getAllByCreateUserId(createUserId);
        int count = allDeviceInfo.size();
//        allDeviceInfo.forEach(System.out::println);
        return new Status("OK",count+"个设备信息获取成功",allDeviceInfo);

    }
}
