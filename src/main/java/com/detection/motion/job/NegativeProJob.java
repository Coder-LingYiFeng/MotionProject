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

@Component
public class NegativeProJob extends QuartzJobBean {

    @Autowired
    DeviceService deviceService;
    @Autowired
    SentenceService sentenceService;
    @Autowired
    MailSendUtil mailSendUtil;
    @Autowired
    MailTempUtil mailTempUtil;

    @Override
    protected void executeInternal(JobExecutionContext context){
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        int deviceId = jobDataMap.getInt("deviceId");
        String startTime = jobDataMap.getString("startTime");
        String endTime = jobDataMap.getString("endTime");
        Double negativeMaxPro = jobDataMap.getDouble("negativeMaxPro");
        Device deviceInfo = deviceService.getById(deviceId);
        String deviceName = deviceInfo.getName();
        ArrayList<Sentence> sentencesInfo = sentenceService.selectSentenceBytimeSection(deviceId, startTime, endTime);
        int negativeNum=0;
        int positiveNum=0;
        int neutralNum=0;
        int sentenceNum = sentencesInfo.size();
        for (Sentence sentence : sentencesInfo) {
            if (sentence.getSentiment()==0)
                negativeNum++;
            if (sentence.getSentiment()==2)
                positiveNum++;
            if (sentence.getSentiment()==1)
                negativeNum++;
        }
        float negativePro = (float) negativeNum / (float) sentenceNum;
        float positivePro = (float) positiveNum / (float) sentenceNum;
        float neutralPro=(float) neutralNum/(float) sentenceNum;
        DecimalFormat df = new DecimalFormat("#0.00%");
        System.out.println("negativePro = " + negativePro);
        System.out.println("negativeMaxPro = " + negativeMaxPro);
        if (negativePro>negativeMaxPro)
            mailSendUtil.sendMail(jobDataMap.getString("toMail"), mailTempUtil.getNegativeProTemp(String.valueOf(df.format(negativePro)),String.valueOf(df.format(neutralPro)),String.valueOf(df.format(positivePro)) ,String.valueOf(negativeNum),String.valueOf(neutralNum),String.valueOf(positiveNum),String.valueOf(sentenceNum), df.format(negativeMaxPro),deviceName,startTime,endTime));
    }

}