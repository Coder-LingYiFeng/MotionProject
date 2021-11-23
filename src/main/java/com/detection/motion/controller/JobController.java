package com.detection.motion.controller;

import com.detection.motion.service.JobService;
import com.alibaba.fastjson.JSONObject;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/job")
public class JobController{

    @Autowired
    JobService jobService;
    @Autowired
    Scheduler scheduler;

    @PostMapping("/addTimingWarningJob")
    public JSONObject addTimingWarningJob(@RequestBody JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
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

        if ((Boolean) jobMap.get("jobExists")){
            resMap.put("status","ERROR");
            resMap.put("message",jobName+":已开启任务，请先停止该任务后再启动！！！");
            if (!jobMap.isEmpty())
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

    @PostMapping("/getJobInfo")
    public JSONObject getJObInfo(@RequestBody JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
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

        HashMap<String, Object> jobInfo = jobService.getJobInfo(jobName);
        return new JSONObject(jobInfo);

    }

    @PostMapping("/deleteJob")
    public JSONObject deleteJob(@RequestBody JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
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
        HashMap<String, Object> jobMap = jobService.jobExists(jobName);

        if (jobService.deleteJob(jobName,jobGroup)){
            System.out.println(jobName+":任务关闭成功！");
            resMap.put("status","OK");
            resMap.put("message",jobName+":任务关闭成功！");
            if (!jobMap.isEmpty())
                resMap.put("Data",jobMap);
            return new JSONObject(resMap);
        }
        resMap.put("status","ERROR");
        resMap.put("message",jobName+":任务关闭出错！！！");
        if (!jobMap.isEmpty())
            resMap.put("Data",jobMap);
        return new JSONObject(resMap);
    }

    @GetMapping("/getJobs")
    public JSONObject getJobs() throws SchedulerException {
        return new JSONObject(jobService.getJobs());
    }

    @PostMapping("/addNegativeNumJob")
    public JSONObject addNegativeNumJob(@RequestBody JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
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

        HashMap<String, Object> jobMap = jobService.jobExists(jobName);

        if ((Boolean) jobMap.get("jobExists")){
            resMap.put("status","ERROR");
            resMap.put("message",jobName+":已开启任务，请先停止该任务后再启动！！！");
            if (!jobMap.isEmpty())
                resMap.put("jobData",jobMap.get("jobData"));
            return new JSONObject(resMap);
        }

        resMap.put("status","OK");
        resMap.put("message","任务开启成功！！！");
        if (!jobMap.isEmpty())
            resMap.put("jobData",jobMap.get("jobData"));

        resMap.put("jobData",jobService.negativeNum(jsonObject));

        return new JSONObject(resMap);
    }

    @PostMapping("/addNegativeProJob")
    public JSONObject addNegativeProJob(@RequestBody JSONObject jsonObject) throws SchedulerException {
        HashMap<String, Object> resMap = new HashMap<>();
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

        HashMap<String, Object> jobMap = jobService.jobExists(jobName);

        if ((Boolean) jobMap.get("jobExists")){
            resMap.put("status","ERROR");
            resMap.put("message",jobName+":已开启任务，请先停止该任务后再启动！！！");
            if (!jobMap.isEmpty())
                resMap.put("jobData",jobMap.get("jobData"));
            return new JSONObject(resMap);
        }

        resMap.put("status","OK");
        resMap.put("message","任务开启成功！！！");
        if (!jobMap.isEmpty())
            resMap.put("jobData",jobMap.get("jobData"));

        resMap.put("jobData",jobService.negativePro(jsonObject));

        return new JSONObject(resMap);
    }

}
