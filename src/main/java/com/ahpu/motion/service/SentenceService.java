package com.ahpu.motion.service;

import com.ahpu.motion.bean.Sentence;
import com.ahpu.motion.mapper.SentenceMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public interface SentenceService extends IService<Sentence> {

    ArrayList<Sentence> selectSentenceBytimeSection(Integer deviceId, String startTime, String endTime);

    void insertSentence(Integer deviceId,String sentence,Integer sentiment,Double confidence,Double positiveProb,Double negativeProb);

}
