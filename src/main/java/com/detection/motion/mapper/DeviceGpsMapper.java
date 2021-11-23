package com.detection.motion.mapper;

import com.detection.motion.bean.DeviceGps;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * 设备GPS信息表操作
 */
@Mapper
public interface DeviceGpsMapper extends BaseMapper<DeviceGps> {

    /**
     * 插入信息
     * @param deviceId 关联设备id
     * @param longitude 经度
     * @param latitude 纬度
     */
    @Insert("insert into device_gps(device_id, longitude, latitude) values (#{deviceId},#{longitude},#{latitude})")
    void insertDeviceGps(Integer deviceId,String longitude,String latitude);

    /**
     * 获取时间区间内设备的GPS信息
     * @param deviceId 关联设备id
     * @param startTime 查询开始时间
     * @param endTime 查询结束时间
     * @return 时间区间内设备的GPS信息
     */
    @Select("select * from device_gps where device_id=#{deviceId} and date_time>=#{startTime} and date_time<=#{endTime}")
    ArrayList<DeviceGps> selectDeviceGpsBytimeSection(Integer deviceId, String startTime, String endTime);

    /**
     * 获取最后一次设备位置
     * @param deviceId 关联设备id
     * @return 返回最后一次位置信息
     */
    @Select("select * from device_gps WHERE device_id=#{deviceId} order by date_time desc limit 1")
    DeviceGps getLastInfo(Integer deviceId);

}
