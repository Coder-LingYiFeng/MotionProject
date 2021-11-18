package com.ahpu.motion.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceGps {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer deviceId;
    private Float longitude;
    private Float latitude;
    @TableField(exist = false)
    private Date dateTime;
}
