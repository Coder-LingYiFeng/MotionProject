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
//数据库语句信息表实体类
public class Sentence {
    //自增id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //关联device.id
    private Integer deviceId;
    //语句信息
    private String sentence;
    //插入的时间
    private Date date;
    //情感分析极性
    private Integer sentiment;
    //情感分析置信度
    private Double confidence;
    //情感分析积极概率
    private Double positiveProb;
    //情感分析消极概率
    private Double negativeProb;
    //非表字段，用于controller层接收参数
    @TableField(exist = false)
    private String mqttPub;


    public Sentence(Integer deviceId, String sentence, Integer sentiment, Double confidence, Double positiveProb, Double negativeProb) {
        this.deviceId = deviceId;
        this.sentence = sentence;
        this.sentiment = sentiment;
        this.confidence = confidence;
        this.positiveProb = positiveProb;
        this.negativeProb = negativeProb;
    }



}
