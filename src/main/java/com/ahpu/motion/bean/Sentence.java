package com.ahpu.motion.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sentence {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String deviceId;
    private String sentence;
    private Date time;
}
