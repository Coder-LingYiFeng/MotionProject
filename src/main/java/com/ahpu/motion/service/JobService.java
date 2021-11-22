package com.ahpu.motion.service;

import com.alibaba.fastjson.JSONObject;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;

public interface JobService {
    JSONObject timingWarning(@RequestBody JSONObject jsonObject) throws SchedulerException;

    HashMap<String,Object> jobExists(String jobName) throws SchedulerException;

    HashMap<String,Object> getJobs() throws SchedulerException;

    HashMap<String,Object> getJobInfo(String jobName) throws SchedulerException;

    boolean deleteJob(String jobName,String jobGroup) throws SchedulerException;

    JSONObject negativeNum(@RequestBody JSONObject jsonObject) throws SchedulerException;

    JSONObject negativePro(@RequestBody JSONObject jsonObject) throws SchedulerException;
}
