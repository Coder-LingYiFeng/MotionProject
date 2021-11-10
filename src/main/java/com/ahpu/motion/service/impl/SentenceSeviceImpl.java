package com.ahpu.motion.service.impl;

import com.ahpu.motion.bean.Sentence;
import com.ahpu.motion.mapper.SentenceMapper;
import com.ahpu.motion.service.SentenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}
