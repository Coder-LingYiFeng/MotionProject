package com.ahpu.motion.bean;

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
public class Sentence {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer deviceId;
    private String sentence;
    private Date time;
    private Integer sentiment;
    private Double confidence;
    private Double positiveProb;
    private Double negativeProb;
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
