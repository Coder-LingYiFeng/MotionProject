package com.detection.motion.service;

import com.detection.motion.bean.Sentence;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

public interface SentenceService extends IService<Sentence> {

    ArrayList<Sentence> selectSentenceBytimeSection(Integer deviceId, String startTime, String endTime);

    void insertSentence(Integer deviceId,String sentence,Integer sentiment,Double confidence,Double positiveProb,Double negativeProb);

    ArrayList<Sentence> selectAllSentenceMessageByDeviceId(Integer deviceId);

    ArrayList<Integer> selectSentimentBytimeSection(Integer deviceId, String startTime, String endTime);

    ArrayList<Sentence> getSentenceByDeviceIdOnOneday(Integer deviceId, String yesterdayTime);


}
