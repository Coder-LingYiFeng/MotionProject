package com.detection.motion.service.impl;

import com.detection.motion.job.NegativeNumJob;
import com.detection.motion.job.NegativeProJob;
import com.detection.motion.job.TimingWarningJob;
import com.detection.motion.service.JobService;
import com.alibaba.fastjson.JSONObject;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 定时任务实现类
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    Scheduler scheduler;

    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;


    /**
     * 定时发送邮件任务实现
     * @param jsonObject 必要参数 deviceId cron  startTime endTime jobGroup toMail
     * @return 返回状态信息及任务信息
     */
    @Override
    public JSONObject timingWarning(JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
        // 每3s执行一次
        //        0/3 * * * * ?
        //参数获取，校验在controller做了
        Integer deviceId = jsonObject.getInteger("deviceId");
        Object cron = jsonObject.get("cron");
        Object startTime = jsonObject.get("startTime");
        Object endTime = jsonObject.get("endTime");
        String jobGroup = jsonObject.getString("jobGroup");


        String toMail = jsonObject.getString("toMail");

        String jobName = deviceId + ":" + jobGroup;
        String triggrtName = "Triggrt:" + deviceId + ":" + jobGroup;
        //新建job任务
        JobDetail jobDetail = JobBuilder.newJob(TimingWarningJob.class).withIdentity(jobName, jobGroup).build();
        //向上下文添加数据，具体任务需要从此获取参数
        jobDetail.getJobDataMap().put("deviceId", deviceId);
        jobDetail.getJobDataMap().put("jobName", jobName);
        jobDetail.getJobDataMap().put("triggrtName", triggrtName);
        jobDetail.getJobDataMap().put("jobGroup", jobGroup);
        jobDetail.getJobDataMap().put("startTime", startTime);
        jobDetail.getJobDataMap().put("endTime", endTime);
        jobDetail.getJobDataMap().put("toMail", toMail);
        //设置任务间隔
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron.toString());
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggrtName, jobGroup).withSchedule(scheduleBuilder).build();
        //开启任务
        scheduler.scheduleJob(jobDetail, cronTrigger);
        //返回任务数据
        resMap.put("deviceId", deviceId);
        resMap.put("jobName", jobName);
        resMap.put("triggrtName", triggrtName);
        resMap.put("jobGroup", jobGroup);
        resMap.put("startTime", startTime);
        resMap.put("endTime", endTime);
        resMap.put("cron", cron);

        resMap.put("toMail", toMail);
        return new JSONObject(resMap);
    }


    /**
     * 判断任务是否开启
     * @param jobName 任务名
     * @return 是否开启的状态信息，开启了的就多返回一个任务信息
     */
    @Override
    public HashMap<String, Object> jobExists(String jobName) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
        Map<String, Object> jobMap = new HashMap<>();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        for (String groupName : triggerGroupNames) {
            //组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
            GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
            //获取所有的triggerKey
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
            for (TriggerKey triggerKey : triggerKeySet) {
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                //获取trigger拥有的Job
                JobKey jobKey = trigger.getJobKey();
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                //分组名称
                jobMap.put("jobGoup", groupName);
                //定时任务名称
                jobMap.put("jobName", jobDetail.getName());
                //cron表达式
                String cronExpression = trigger.getCronExpression();
                jobMap.put("corn", cronExpression);

                jobMap.put("deviceId", jobDetail.getJobDataMap().get("deviceId"));
                jobMap.put("triggrtName", jobDetail.getJobDataMap().get("triggrtName"));
                jobMap.put("startTime", jobDetail.getJobDataMap().get("startTime"));
                jobMap.put("endTime", jobDetail.getJobDataMap().get("endTime"));

                jobMap.put("toMail", jobDetail.getJobDataMap().get("toMail"));

                Object negativeMaxNum = jobDetail.getJobDataMap().get("negativeMaxNum");
                Object negativeMaxPro = jobDetail.getJobDataMap().get("negativeMaxPro");
                //存在即添加不存在就不添加，因为不同任务可能没有该参数
                if (negativeMaxNum!=null&&jobName.contains("Num"))
                    jobMap.put("negativeMaxNum",negativeMaxNum);
                if (negativeMaxPro!=null&&jobName.contains("Pro"))
                    jobMap.put("negativeMaxPro",negativeMaxPro);

                //判断是否是要找的job，是则返回，否则继续查找
                if (jobName.equals(jobDetail.getName())) {
                    resMap.put("jobExists", true);
                    resMap.put("jobData", jobMap);
                    return resMap;
                }
            }
        }
        resMap.put("jobExists", false);
        //说明没有找到任务信息，返回null
        resMap.put("jobData", null);
        return resMap;
    }

    /**
     * 获取所有任务信息
     * @return 返回所有任务信息
     */
    @Override
    public HashMap<String, Object> getJobs() throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        for (String groupName : triggerGroupNames) {
            //组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
            GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
            //获取所有的triggerKey
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
            for (TriggerKey triggerKey : triggerKeySet) {
                Map<String, Object> jobMap = new HashMap<>();
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                //获取trigger拥有的Job
                JobKey jobKey = trigger.getJobKey();
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                //分组名称
                jobMap.put("jobGoup", groupName);
                //定时任务名称
                jobMap.put("jobName", jobDetail.getName());
                //cron表达式
                String cronExpression = trigger.getCronExpression();
                //添加到返回的map
                jobMap.put("corn", cronExpression);
                jobMap.put("deviceId", jobDetail.getJobDataMap().get("deviceId"));
                jobMap.put("triggrtName", jobDetail.getJobDataMap().get("triggrtName"));
                jobMap.put("startTime", jobDetail.getJobDataMap().get("startTime"));
                jobMap.put("endTime", jobDetail.getJobDataMap().get("endTime"));

                jobMap.put("toMail", jobDetail.getJobDataMap().get("toMail"));

                Object negativeMaxNum = jobDetail.getJobDataMap().get("negativeMaxNum");
                Object negativeMaxPro = jobDetail.getJobDataMap().get("negativeMaxPro");
                //存在即添加不存在就不添加，因为不同任务可能没有该参数
                if (negativeMaxNum!=null&&jobDetail.getName().contains("Num"))
                    jobMap.put("negativeMaxNum",negativeMaxNum);
                if (negativeMaxPro!=null&&jobDetail.getName().contains("Pro"))
                    jobMap.put("negativeMaxPro",negativeMaxPro);

                resMap.put(jobDetail.getName(), jobMap);
            }
        }
        return resMap;
    }

    /**
     * 获取指定job的信息
     * @param jobName job名
     * @return job信息
     */
    @Override
    public HashMap<String, Object> getJobInfo(String jobName) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
        Map<String, Object> jobMap = new HashMap<>();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        //遍历所有group
        for (String groupName : triggerGroupNames) {
            //组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
            GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
            //获取所有的triggerKey
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
            //遍历所有triggerKey
            for (TriggerKey triggerKey : triggerKeySet) {
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                //获取trigger拥有的Job
                JobKey jobKey = trigger.getJobKey();
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                //分组名称
                jobMap.put("jobGoup", groupName);
                //定时任务名称
                jobMap.put("jobName", jobDetail.getName());
                //cron表达式
                String cronExpression = trigger.getCronExpression();
                //添加到要返回的map
                jobMap.put("corn", cronExpression);
                jobMap.put("deviceId", jobDetail.getJobDataMap().get("deviceId"));
                jobMap.put("triggrtName", jobDetail.getJobDataMap().get("triggrtName"));
                jobMap.put("startTime", jobDetail.getJobDataMap().get("startTime"));
                jobMap.put("endTime", jobDetail.getJobDataMap().get("endTime"));

                jobMap.put("toMail", jobDetail.getJobDataMap().get("toMail"));

                Object negativeMaxNum = jobDetail.getJobDataMap().get("negativeMaxNum");
                Object negativeMaxPro = jobDetail.getJobDataMap().get("negativeMaxPro");
                //存在即添加不存在就不添加，因为不同任务可能没有该参数
                if (negativeMaxNum!=null&&jobName.contains("Num"))
                    jobMap.put("negativeMaxNum",negativeMaxNum);
                if (negativeMaxPro!=null&&jobName.contains("Pro"))
                    jobMap.put("negativeMaxPro",negativeMaxPro);

                //判断是否是要找的job，是则返回，否则继续查找
                if (jobName.equals(jobDetail.getName())) {
                    resMap.put("jobExists", true);
                    resMap.put("jobData", jobMap);
                    return resMap;
                }
            }
        }
        resMap.put("jobExists", false);
        //说明没有找到任务信息，返回null
        resMap.put("jobData", null);
        return resMap;
    }


    /**
     * 删除任务
     * @param jobName 任务名
     * @param jobGroup 任务组
     * @return 返回是否成功
     */
    @Override
    public boolean deleteJob(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        return scheduler.deleteJob(jobKey);
    }

    /**
     * 同第一个任务，参数不同而已
     */
    @Override
    public JSONObject negativeNum(JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
        // 每5s执行一次
        //        0/5 * * * * ?
        String cron = jsonObject.getString("cron");
        Integer deviceId = jsonObject.getInteger("deviceId");
        Object startTime = jsonObject.get("startTime");
        Object endTime = jsonObject.get("endTime");
        String jobGroup = jsonObject.getString("jobGroup");
        Integer negativeMaxNum = jsonObject.getInteger("negativeMaxNum");


        String toMail = jsonObject.getString("toMail");

        String jobName = deviceId + ":" + jobGroup;
        String triggrtName = "Triggrt:" + deviceId + ":" + jobGroup;
        JobDetail jobDetail = JobBuilder.newJob(NegativeNumJob.class).withIdentity(jobName, jobGroup).build();
        jobDetail.getJobDataMap().put("deviceId", deviceId);
        jobDetail.getJobDataMap().put("jobName", jobName);
        jobDetail.getJobDataMap().put("negativeMaxNum",negativeMaxNum);
        jobDetail.getJobDataMap().put("triggrtName", triggrtName);
        jobDetail.getJobDataMap().put("jobGroup", jobGroup);
        jobDetail.getJobDataMap().put("startTime", startTime);
        jobDetail.getJobDataMap().put("endTime", endTime);
        jobDetail.getJobDataMap().put("toMail", toMail);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggrtName, jobGroup).withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        resMap.put("deviceId", deviceId);
        resMap.put("jobName", jobName);
        resMap.put("triggrtName", triggrtName);
        resMap.put("jobGroup", jobGroup);
        resMap.put("startTime", startTime);
        resMap.put("endTime", endTime);
        resMap.put("cron", cron);
        resMap.put("negativeMaxNum",negativeMaxNum);

        resMap.put("toMail", toMail);
        return new JSONObject(resMap);
    }

    /**
     * 同第一个任务，参数不同而已
     */
    @Override
    public JSONObject negativePro(JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
        // 每5s执行一次
        //        0/5 * * * * ?
        String cron = jsonObject.getString("cron");
        Integer deviceId = jsonObject.getInteger("deviceId");
        Object startTime = jsonObject.get("startTime");
        Object endTime = jsonObject.get("endTime");
        String jobGroup = jsonObject.getString("jobGroup");
        Double negativeMaxPro = jsonObject.getDouble("negativeMaxPro");
//        System.out.println("negativeMaxPro = " + negativeMaxPro);


        String toMail = jsonObject.getString("toMail");

        String jobName = deviceId + ":" + jobGroup;
        String triggrtName = "Triggrt:" + deviceId + ":" + jobGroup;
        JobDetail jobDetail = JobBuilder.newJob(NegativeProJob.class).withIdentity(jobName, jobGroup).build();
        jobDetail.getJobDataMap().put("deviceId", deviceId);
        jobDetail.getJobDataMap().put("jobName", jobName);
        jobDetail.getJobDataMap().put("negativeMaxPro",negativeMaxPro);
        jobDetail.getJobDataMap().put("triggrtName", triggrtName);
        jobDetail.getJobDataMap().put("jobGroup", jobGroup);
        jobDetail.getJobDataMap().put("startTime", startTime);
        jobDetail.getJobDataMap().put("endTime", endTime);
        jobDetail.getJobDataMap().put("toMail", toMail);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggrtName, jobGroup).withSchedule(scheduleBuilder).build();
//        System.out.println("cronTrigger = " + cronTrigger);
        scheduler.scheduleJob(jobDetail, cronTrigger);
        resMap.put("deviceId", deviceId);
        resMap.put("jobName", jobName);
        resMap.put("triggrtName", triggrtName);
        resMap.put("jobGroup", jobGroup);
        resMap.put("startTime", startTime);
        resMap.put("endTime", endTime);
        resMap.put("cron", cron);
        resMap.put("negativeMaxPro",negativeMaxPro);

        resMap.put("toMail", toMail);
        return new JSONObject(resMap);
    }
}


