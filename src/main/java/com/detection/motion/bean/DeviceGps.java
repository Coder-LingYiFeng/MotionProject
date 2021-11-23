package com.detection.motion.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//数据库设备GPS信息表实体类
public class DeviceGps {
    //自增id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //关联device.id
    private Integer deviceId;
    //经度信息
    private String longitude;
    //纬度信息
    private String latitude;
    //插入GPS信息数据的时间
    private Date dateTime;
}
