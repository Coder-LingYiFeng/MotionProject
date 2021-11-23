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
 * 定时发送邮件任务的实现
 */
@Component
public class TimingWarningJob extends QuartzJobBean {

    @Autowired
    MailSendUtil mailSendUtil;

    @Autowired
    MailTempUtil mailTempUtil;

    @Autowired
    SentenceService sentenceService;
    @Autowired
    DeviceService deviceService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        //参数由上下文获取
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
//        String s = jobDataMap.get("deviceId") + "--" + jobDataMap.get("startTime") + "--" + jobDataMap.get("endTime");
//        System.out.println(jobDataMap.get("jobName") + "--" + jobDataMap.get("jobGroup") + "--" + jobDataMap.get("triggrtName"));
//        System.out.println(s);
        int deviceId = jobDataMap.getInt("deviceId");
        String startTime = jobDataMap.getString("startTime");
        String endTime = jobDataMap.getString("endTime");
        //获取设备信息
        Device deviceInfo = deviceService.getById(deviceId);
        String deviceName = deviceInfo.getName();
        //获取时间区间内的语句信息
        ArrayList<Sentence> sentencesInfo = sentenceService.selectSentenceBytimeSection(deviceId,startTime,endTime);
        if (sentencesInfo.size()==0){
            mailSendUtil.sendMail(jobDataMap.getString("toMail"), mailTempUtil.getTimingWaringTemp(String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(0),String.valueOf(0),deviceName,startTime,endTime));
        }
        //数据统计
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
        //发送邮件
        mailSendUtil.sendMail(jobDataMap.getString("toMail"), mailTempUtil.getTimingWaringTemp(String.valueOf(df.format(negativePro)),String.valueOf(df.format(neutralPro)),String.valueOf(df.format(positivePro)) ,String.valueOf(negativeNum),String.valueOf(neutralNum),String.valueOf(positiveNum),String.valueOf(sentenceNum),deviceName,startTime,endTime));
    }
}
