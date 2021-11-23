package com.detection.motion.mapper;

import com.detection.motion.bean.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    @Select("select * from device where name=#{userName}")
    Device getDeviceByName(String name);

    @Select("select * from device where name=#{name} and create_user_id=#{createUserId}")
    Device getDeviceByNameAndId(String name,Integer createUserId);

    @Delete("delete from device where create_user_id=#{createUserId} and name=#{name}")
    boolean deleteByNameAndId(String name,Integer createUserId);

    @Update("update device set name=#{name},create_user_id=#{createUserId},scribe=#{scribe},mqtt_sub=#{mqttSub},mqtt_pub=#{mqttPub} WHERE id=#{id}")
    boolean updateDeviceById(Integer id,String name,Integer createUserId,String scribe,String mqttSub,String mqttPub);

    @Select("select * from device where create_user_id=#{createUserId}")
    List<Device> getAllByCreateUserId(Integer createUserId);

    @Select("select * from device")
    List<Device> getAllDeviceInfo();
}
