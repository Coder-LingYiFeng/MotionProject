package com.ahpu.motion.mapper;

import com.ahpu.motion.bean.Sentence;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface SentenceMapper extends BaseMapper<Sentence> {

    @Select("select * from sentence where device_id=#{deviceId} and time>=#{startTime} and time<=#{endTime}")
    ArrayList<Sentence> selectSentenceBytimeSection(Integer deviceId , String startTime, String endTime);
}
