package com.ahpu.motion.mapper;

import com.ahpu.motion.bean.Sentence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface SentenceMapper extends BaseMapper<Sentence> {

    @Select("select * from sentence where device_id=#{deviceId} and date>=#{startTime} and date<=#{endTime}")
    ArrayList<Sentence> selectSentenceBytimeSection(Integer deviceId, String startTime, String endTime);

    @Insert("insert into sentence(device_id, sentence, sentiment, confidence, positive_prob, negative_prob) " +
            "values (#{deviceId},#{sentence},#{sentiment},#{confidence},#{positiveProb},#{negativeProb})")
    void insertSentence(Integer deviceId, String sentence, Integer sentiment, Double confidence, Double positiveProb, Double negativeProb);

    @Select("select * from sentence where device_id=#{deviceId}")
    ArrayList<Sentence> selectAllSentenceMessageByDeviceId(Integer deviceId);

    @Select("select sentiment from sentence where device_id=#{deviceId} and date>=#{startTime} and date<=#{endTime}")
    ArrayList<Integer> selectSentimentBytimeSection(Integer deviceId, String startTime, String endTime);

    @Select("select * from sentence where device_id=#{deviceId} and date>=#{yesterdayTime}")
    ArrayList<Sentence> getSentenceByDeviceIdOnOneday(Integer deviceId, String yesterdayTime);
}
