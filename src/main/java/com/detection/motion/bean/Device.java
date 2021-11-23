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
public class Device {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer createUserId;
    private String scribe;
    private Date createDate;
    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;
    private String mqttSub;
    private String mqttPub;
}
