package com.ahpu.motion.mapper;

import com.ahpu.motion.bean.DeviceGps;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface DeviceGpsMapper extends BaseMapper<DeviceGps> {

    @Insert("insert into device_gps(device_id, longitude, latitude) values (#{deviceId},#{longitude},#{latitude})")
    void insertDeviceGps(Integer deviceId,String longitude,String latitude);

    @Select("select * from device_gps where device_id=#{deviceId} and date_time>=#{startTime} and date_time<=#{endTime}")
    ArrayList<DeviceGps> selectDeviceGpsBytimeSection(Integer deviceId, String startTime, String endTime);

}
