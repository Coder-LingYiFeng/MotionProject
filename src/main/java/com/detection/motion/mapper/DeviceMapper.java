package com.detection.motion.mapper;

import com.detection.motion.bean.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 设备表操作
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    /**
     * 通过设备名查询设备信息
     * @param name 设备名
     * @return 设备信息
     */
    @Select("select * from device where name=#{userName}")
    Device getDeviceByName(String name);

    /**
     * 通过设备名和创建设备的id查询设备信息
     * @param name 设备名
     * @param createUserId 关联用户的id
     * @return 设备信息
     */
    @Select("select * from device where name=#{name} and create_user_id=#{createUserId}")
    Device getDeviceByNameAndId(String name,Integer createUserId);

    /**
     * 通过设备名和创建设备的id删除设备信息
     * @param name 设备名
     * @param createUserId 关联用户的id
     * @return 设备信息
     */
    @Delete("delete from device where create_user_id=#{createUserId} and name=#{name}")
    boolean deleteByNameAndId(String name,Integer createUserId);

    /**
     * 更新设备信息
     * @param id 设备自增id
     * @param name 要更新的设备名
     * @param createUserId 要更新的设备的用户id
     * @param scribe 要更新的设备描述
     * @param mqttSub mqttsub参数
     * @param mqttPub mqttPub参数
     * @return 更新的设备信息
     */
    @Update("update device set name=#{name},create_user_id=#{createUserId},scribe=#{scribe},mqtt_sub=#{mqttSub},mqtt_pub=#{mqttPub} WHERE id=#{id}")
    boolean updateDeviceById(Integer id,String name,Integer createUserId,String scribe,String mqttSub,String mqttPub);

    /**
     * 查询用户下所有的设备信息
     * @param createUserId 设备关联的用户id
     * @return 所有设备信息
     */
    @Select("select * from device where create_user_id=#{createUserId}")
    List<Device> getAllByCreateUserId(Integer createUserId);

    /**
     * 获取所有的设备信息
     * @return 所有的设备信息
     */
    @Select("select * from device")
    List<Device> getAllDeviceInfo();
}
