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
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//数据库设备表实体类
public class Device {
    //自增id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //设备名
    private String name;
    //关联user.id
    private Integer createUserId;
    //设备描述信息
    private String scribe;
    //创建设备的时间
    private Date createDate;
    //非数据库字段，用于controller接收参数
    @TableField(exist = false)
    private String startTime;
    //非数据库字段，用于controller接收参数
    @TableField(exist = false)
    private String endTime;
    //mqttSub配置
    private String mqttSub;
    //mqttPub配置
    private String mqttPub;
}
