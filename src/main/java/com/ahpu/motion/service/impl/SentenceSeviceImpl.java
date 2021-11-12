package com.ahpu.motion.service.impl;

import com.ahpu.motion.bean.Sentence;
import com.ahpu.motion.mapper.SentenceMapper;
import com.ahpu.motion.service.SentenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class SentenceSeviceImpl extends ServiceImpl<SentenceMapper, Sentence> implements SentenceService {

    @Autowired
    SentenceMapper sentenceMapper;

    @Override
    public ArrayList<Sentence> selectSentenceBytimeSection(Integer deviceId, String startTime, String endTime) {
        return sentenceMapper.selectSentenceBytimeSection(deviceId, startTime, endTime);
    }

    @Override
    public void insertSentence(Integer deviceId, String sentence, Integer sentiment, Double confidence, Double positiveProb, Double negativeProb) {
        sentenceMapper.insertSentence(deviceId, sentence, sentiment, confidence, positiveProb, negativeProb);
    }

    @Override
    public ArrayList<Sentence> selectAllSentenceMessageByDeviceId(Integer deviceId) {
        return sentenceMapper.selectAllSentenceMessageByDeviceId(deviceId);
    }

    @Override
    public ArrayList<Integer> selectSentimentBytimeSection(Integer deviceId, String startTime, String endTime) {
        return sentenceMapper.selectSentimentBytimeSection(deviceId, startTime, endTime);
    }

    @Override
    public ArrayList<Sentence> getSentenceByDeviceIdOnOneday(Integer deviceId, String yesterdayTime) {
        return sentenceMapper.getSentenceByDeviceIdOnOneday(deviceId,yesterdayTime);
    }


}
