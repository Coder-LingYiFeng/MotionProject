package com.detection.motion.controller;

import com.detection.motion.service.JobService;
import com.alibaba.fastjson.JSONObject;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 定时任务的接口
 */
@RestController
@RequestMapping("/job")
public class JobController{

    @Autowired
    JobService jobService;
    @Autowired
    Scheduler scheduler;

    /**
     * 添加定时发送邮件的任务（监控时间范围内）
     * @param jsonObject 必要参数deviceId jobGroup cron startTime endTime toMail
     * @return 返回状态信息及开启的任务信息
     * @throws SchedulerException 任务调度Exception
     */
    @PostMapping("/addTimingWarningJob")
    public JSONObject addTimingWarningJob(@RequestBody JSONObject jsonObject) throws SchedulerException {
        //保存任务信息
        HashMap<String, Object> resMap = new HashMap<>();
        //获取参数及参数校验
        Integer deviceId = jsonObject.getInteger("deviceId");
        if (deviceId==null){
            resMap.put("status","ERROR");
            resMap.put("message","参数deviceId为空");
            return new JSONObject(resMap);
        }
        String jobGroup = jsonObject.getString("jobGroup");
        if (jobGroup==null||"".equals(jobGroup)){
            resMap.put("status","ERROR");
            resMap.put("message","参数name为空");
            return new JSONObject(resMap);
        }
        Object cron = jsonObject.get("cron");
        if (cron==null||"".equals(cron)){
            resMap.put("status","ERROR");
            resMap.put("message","参数cron为空");
            return new JSONObject(resMap);
        }
        Object startTime = jsonObject.get("startTime");
        if (startTime==null||"".equals(startTime)){
            resMap.put("status","ERROR");
            resMap.put("message","参数startTime为空");
            return new JSONObject(resMap);
        }
        Object endTime = jsonObject.get("endTime");
        if (endTime==null||"".equals(endTime)){
            resMap.put("status","ERROR");
            resMap.put("message","参数endTime为空");
            return new JSONObject(resMap);
        }

        String toMail = jsonObject.getString("toMail");
        if (toMail==null||"".equals(toMail)){
            resMap.put("status","ERROR");
            resMap.put("message","参数toMail为空");
            return new JSONObject(resMap);
        }


        String jobName=deviceId+":"+jobGroup;

        HashMap<String, Object> jobMap = jobService.jobExists(jobName);

        //判断任务是否开启
        if ((Boolean) jobMap.get("jobExists")){
            resMap.put("status","ERROR");
            resMap.put("message",jobName+":已开启任务，请先停止该任务后再启动！！！");
            resMap.put("jobData",jobMap.get("jobData"));
            return new JSONObject(resMap);
        }

        resMap.put("status","OK");
        resMap.put("message","任务开启成功！！！");
        if (!jobMap.isEmpty())
            resMap.put("jobData",jobMap.get("jobData"));
        resMap.put("jobData",jobService.timingWarning(jsonObject));

        return new JSONObject(resMap);
    }

    /**
     * 获取指定任务的任务信息
     * @param jsonObject 必要参数 deviceId jobGroup
     * @return 返回状态信息及任务信息
     * @throws SchedulerException 任务调度Exception
     */
    @PostMapping("/getJobInfo")
    public JSONObject getJObInfo(@RequestBody JSONObject jsonObject) throws SchedulerException {
        //保存任务信息
        HashMap<String, Object> resMap = new HashMap<>();
        //获取参数及参数校验
        Integer deviceId = jsonObject.getInteger("deviceId");
        if (deviceId==null){
            resMap.put("status","ERROR");
            resMap.put("message","参数deviceId为空");
            return new JSONObject(resMap);
        }
        String jobGroup = jsonObject.getString("jobGroup");
        if (jobGroup==null||"".equals(jobGroup)){
            resMap.put("status","ERROR");
            resMap.put("message","参数jobGroup为空");
            return new JSONObject(resMap);
        }
        String jobName=deviceId+":"+jobGroup;

        //获取设备信息
        HashMap<String, Object> jobInfo = jobService.getJobInfo(jobName);
        return new JSONObject(jobInfo);

    }

    /**
     * 删除任务
     * @param jsonObject 必要参数 deviceId jobGroup
     * @return 返回删除的任务信息及状态信息
     * @throws SchedulerException 任务调度Exception
     */
    @PostMapping("/deleteJob")
    public JSONObject deleteJob(@RequestBody JSONObject jsonObject) throws SchedulerException {
        //保存任务信息
        HashMap<String, Object> resMap = new HashMap<>();
        //获取参数及参数校验
        Integer deviceId = jsonObject.getInteger("deviceId");
        if (deviceId==null){
            resMap.put("status","ERROR");
            resMap.put("message","参数deviceId为空");
            return new JSONObject(resMap);
        }
        String jobGroup = jsonObject.getString("jobGroup");
        if (jobGroup==null||"".equals(jobGroup)){
            resMap.put("status","ERROR");
            resMap.put("message","参数jobGroup为空");
            return new JSONObject(resMap);
        }
        String jobName=deviceId+":"+jobGroup;
        //判断job是否存在，并返回相关信息
        HashMap<String, Object> jobMap = jobService.jobExists(jobName);

        //关闭成功返回的信息
        if ((Boolean) jobMap.get("jobExists")){
            jobService.deleteJob(jobName,jobGroup);
            System.out.println(jobName+":任务关闭成功！");
            resMap.put("status","OK");
            resMap.put("message",jobName+":任务关闭成功！");
            //jobMap非空说明job存在，则将job信息添加进去
            if (!jobMap.isEmpty())
                resMap.put("Data",jobMap);
            //否则直接返回
            return new JSONObject(resMap);
        }
        //关闭失败返回的信息
        resMap.put("status","ERROR");
        resMap.put("message",jobName+":任务不存在！！！");
        if (!jobMap.isEmpty())
            resMap.put("Data",jobMap);
        return new JSONObject(resMap);
    }

    /**
     * 获取所有的任务信息
     * @return 返回所有job信息
     * @throws SchedulerException 任务调度Exception
     */
    @GetMapping("/getJobs")
    public JSONObject getJobs() throws SchedulerException {
        return new JSONObject(jobService.getJobs());
    }

    /**
     * 添加消极条数超过阈值时发送邮件的任务（监控时间范围内）
     * @param jsonObject 必要参数deviceId cron jobGroup negativeMaxNum startTime endTime toMail
     * @return 返回状态信息及开启的任务信息
     * @throws SchedulerException 任务调度Exception
     */
    @PostMapping("/addNegativeNumJob")
    public JSONObject addNegativeNumJob(@RequestBody JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
        //参数获取及校验
        Integer deviceId = jsonObject.getInteger("deviceId");
        if (deviceId==null){
            resMap.put("status","ERROR");
            resMap.put("message","参数deviceId为空");
            return new JSONObject(resMap);
        }
        Object cron = jsonObject.get("cron");
        if (cron==null||"".equals(cron)){
            resMap.put("status","ERROR");
            resMap.put("message","参数cron为空");
            return new JSONObject(resMap);
        }
        String jobGroup = jsonObject.getString("jobGroup");
        if (jobGroup==null||"".equals(jobGroup)){
            resMap.put("status","ERROR");
            resMap.put("message","参数name为空");
            return new JSONObject(resMap);
        }
        Object negativeMaxNum = jsonObject.get("negativeMaxNum");
        if (negativeMaxNum==null||"".equals(negativeMaxNum)){
            resMap.put("status","ERROR");
            resMap.put("message","参数negativeMaxNum为空");
            return new JSONObject(resMap);
        }
        Object startTime = jsonObject.get("startTime");
        if (startTime==null||"".equals(startTime)){
            resMap.put("status","ERROR");
            resMap.put("message","参数startTime为空");
            return new JSONObject(resMap);
        }
        Object endTime = jsonObject.get("endTime");
        if (endTime==null||"".equals(endTime)){
            resMap.put("status","ERROR");
            resMap.put("message","参数endTime为空");
            return new JSONObject(resMap);
        }

        String toMail = jsonObject.getString("toMail");
        if (toMail==null||"".equals(toMail)){
            resMap.put("status","ERROR");
            resMap.put("message","参数toMail为空");
            return new JSONObject(resMap);
        }


        String jobName=deviceId+":"+jobGroup;

        //判断job是否存在，存在会多返回一个job信息
        HashMap<String, Object> jobMap = jobService.jobExists(jobName);

        //判断job是否存在
        if ((Boolean) jobMap.get("jobExists")){
            resMap.put("status","ERROR");
            resMap.put("message",jobName+":已开启任务，请先停止该任务后再启动！！！");
            //将获取的job信息添加到结果
            resMap.put("jobData",jobMap.get("jobData"));
            return new JSONObject(resMap);
        }

        resMap.put("status","OK");
        resMap.put("message","任务开启成功！！！");
        //将获取的job信息添加到结果
        if (!jobMap.isEmpty())
            resMap.put("jobData",jobMap.get("jobData"));

        resMap.put("jobData",jobService.negativeNum(jsonObject));

        return new JSONObject(resMap);
    }

    /**
     * 添加消极占比超过阈值时发送邮件的任务（监控时间范围内）
     * @param jsonObject deviceId cron jobGroup negativeMaxPro startTime endTime toMail
     * @return 返回状态信息及开启的任务信息
     * @throws SchedulerException 任务调度Exception
     */
    @PostMapping("/addNegativeProJob")
    public JSONObject addNegativeProJob(@RequestBody JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
        //获取参数及参数校验
        Integer deviceId = jsonObject.getInteger("deviceId");
        if (deviceId==null){
            resMap.put("status","ERROR");
            resMap.put("message","参数deviceId为空");
            return new JSONObject(resMap);
        }
        Object cron = jsonObject.get("cron");
        if (cron==null||"".equals(cron)){
            resMap.put("status","ERROR");
            resMap.put("message","参数cron为空");
            return new JSONObject(resMap);
        }
        String jobGroup = jsonObject.getString("jobGroup");
        if (jobGroup==null||"".equals(jobGroup)){
            resMap.put("status","ERROR");
            resMap.put("message","参数name为空");
            return new JSONObject(resMap);
        }
        Object negativeMaxPro = jsonObject.get("negativeMaxPro");
        if (negativeMaxPro==null||"".equals(negativeMaxPro)){
            resMap.put("status","ERROR");
            resMap.put("message","参数negativeMaxPro为空");
            return new JSONObject(resMap);
        }
        Object startTime = jsonObject.get("startTime");
        if (startTime==null||"".equals(startTime)){
            resMap.put("status","ERROR");
            resMap.put("message","参数startTime为空");
            return new JSONObject(resMap);
        }
        Object endTime = jsonObject.get("endTime");
        if (endTime==null||"".equals(endTime)){
            resMap.put("status","ERROR");
            resMap.put("message","参数endTime为空");
            return new JSONObject(resMap);
        }

        String toMail = jsonObject.getString("toMail");
        if (toMail==null||"".equals(toMail)){
            resMap.put("status","ERROR");
            resMap.put("message","参数toMail为空");
            return new JSONObject(resMap);
        }


        String jobName=deviceId+":"+jobGroup;

        //判断job是否存在，存在或多返回job信息
        HashMap<String, Object> jobMap = jobService.jobExists(jobName);

        //job已存在
        if ((Boolean) jobMap.get("jobExists")){
            resMap.put("status","ERROR");
            resMap.put("message",jobName+":已开启任务，请先停止该任务后再启动！！！");
            resMap.put("jobData",jobMap.get("jobData"));
            return new JSONObject(resMap);
        }

        //job不存在
        resMap.put("status","OK");
        resMap.put("message","任务开启成功！！！");
        if (!jobMap.isEmpty())
            resMap.put("jobData",jobMap.get("jobData"));

        resMap.put("jobData",jobService.negativePro(jsonObject));

        return new JSONObject(resMap);
    }

}
