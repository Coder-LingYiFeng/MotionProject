package com.detection.motion.job;

import com.detection.motion.bean.Device;
import com.detection.motion.bean.Sentence;
import com.detection.motion.service.DeviceService;
import com.detection.motion.service.SentenceService;
import com.detection.motion.utils.MailSendUtil;
import com.detection.motion.utils.MailTempUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 消极条数超过阈值发送邮件的任务实现
 */
@Component
public class NegativeNumJob extends QuartzJobBean {

    @Autowired
    DeviceService deviceService;
    @Autowired
    SentenceService sentenceService;
    @Autowired
    MailSendUtil mailSendUtil;
    @Autowired
    MailTempUtil mailTempUtil;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        //参数由上下文传输
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        int deviceId = jobDataMap.getInt("deviceId");
        String startTime = jobDataMap.getString("startTime");
        String endTime = jobDataMap.getString("endTime");
        int negativeMaxNum = jobDataMap.getInt("negativeMaxNum");
        //获取设备信息
        Device deviceInfo = deviceService.getById(deviceId);
        //获取设备名
        String deviceName = deviceInfo.getName();
        //获取该时间区间内的语句信息
        ArrayList<Sentence> sentencesInfo = sentenceService.selectSentenceBytimeSection(deviceId, startTime, endTime);
        int negativeNum=0;
        int positiveNum=0;
        int neutralNum=0;
        int sentenceNum = sentencesInfo.size();
        //获取各个情感信息的语句条数
        for (Sentence sentence : sentencesInfo) {
            if (sentence.getSentiment()==0)
                negativeNum++;
            if (sentence.getSentiment()==2)
                positiveNum++;
            if (sentence.getSentiment()==1)
                negativeNum++;
        }
        //获取占比等信息
        float negativePro = (float) negativeNum / (float) sentenceNum;
        float positivePro = (float) positiveNum / (float) sentenceNum;
        float neutralPro=(float) neutralNum/(float) sentenceNum;
        //格式化占比信息为百分比
        DecimalFormat df = new DecimalFormat("#0.00%");
        //超过阈值则发邮件
        if (negativeNum>=negativeMaxNum)
            //发送邮件
            mailSendUtil.sendMail(jobDataMap.getString("toMail"), mailTempUtil.getNegativeNumTemp(String.valueOf(df.format(negativePro)),String.valueOf(df.format(neutralPro)),String.valueOf(df.format(positivePro)) ,String.valueOf(negativeNum),String.valueOf(neutralNum),String.valueOf(positiveNum),String.valueOf(sentenceNum),String.valueOf(negativeMaxNum),deviceName,startTime,endTime));
    }

}
