package com.detection.motion.mapper;

import com.detection.motion.bean.Sentence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

/**
 * 语句信息表的操作
 */
@Mapper
public interface SentenceMapper extends BaseMapper<Sentence> {

    /**
     * 查询时间区间内该设备的所有语句信息
     * @param deviceId 关联设备id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 语句列表
     */
    @Select("select * from sentence where device_id=#{deviceId} and date>=#{startTime} and date<=#{endTime}")
    ArrayList<Sentence> selectSentenceBytimeSection(Integer deviceId, String startTime, String endTime);

    /**
     * 插入语句信息
     * @param deviceId 关联的设备id
     * @param sentence 语句
     * @param sentiment 情感极性
     * @param confidence 置信度
     * @param positiveProb 积极概率
     * @param negativeProb 消极概率
     */
    @Insert("insert into sentence(device_id, sentence, sentiment, confidence, positive_prob, negative_prob) " +
            "values (#{deviceId},#{sentence},#{sentiment},#{confidence},#{positiveProb},#{negativeProb})")
    void insertSentence(Integer deviceId, String sentence, Integer sentiment, Double confidence, Double positiveProb, Double negativeProb);

    /**
     * 获取设备的所有语句信息
     * @param deviceId 关联的设备id
     * @return 所有的语句信息
     */
    @Select("select * from sentence where device_id=#{deviceId}")
    ArrayList<Sentence> selectAllSentenceMessageByDeviceId(Integer deviceId);

    /**
     * 查询时间区间内该设备所有的语句信息
     * @param deviceId 关联的设备id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 语句信息
     */
    @Select("select sentiment from sentence where device_id=#{deviceId} and date>=#{startTime} and date<=#{endTime}")
    ArrayList<Integer> selectSentimentBytimeSection(Integer deviceId, String startTime, String endTime);

    /**
     * 查询指定时间至今的该设备的语句信息
     * @param deviceId 关联设备id
     * @param yesterdayTime 指定时间
     * @return 语句信息列表
     */
    @Select("select * from sentence where device_id=#{deviceId} and date>=#{yesterdayTime}")
    ArrayList<Sentence> getSentenceByDeviceIdOnOneday(Integer deviceId, String yesterdayTime);
}
