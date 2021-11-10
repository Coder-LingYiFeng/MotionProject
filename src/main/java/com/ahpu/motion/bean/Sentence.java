package com.ahpu.motion.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Sentence {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer deviceId;
    private String sentence;
    private Date time;
    private Integer sentiment;
    private Double confidence;
    private Double positiveProb;

    public Sentence(Integer deviceId, String sentence, Integer sentiment, Double confidence, Double positiveProb, Double negativeProb) {
        this.deviceId = deviceId;
        this.sentence = sentence;
        this.sentiment = sentiment;
        this.confidence = confidence;
        this.positiveProb = positiveProb;
        this.negativeProb = negativeProb;
    }

    private Double negativeProb;


}
