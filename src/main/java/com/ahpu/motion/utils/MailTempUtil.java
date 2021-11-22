package com.ahpu.motion.utils;

import org.springframework.stereotype.Component;

@Component
public class MailTempUtil {
    public String getTimingWaringTemp(String negativePro,String neutralPro,String positivePro,String negativeNum,String neutralNum,String positiveNum,String sentenceNum,String deviceName,String startTime,String endTime){
        return "<html lang=\"en\">\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "\t\t<title>情绪检测系统报警邮件</title>\n" +
                "\t\t<style type=\"text/css\">\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<div style=\"text-align: center;\">\n" +
                "\t\t\t<h1 style=\"color: red;\">情绪检测系统报警邮件</h1>\n" +
                "\t\t\t<h3>设备名："+deviceName+"</h3>\n" +
                "\t\t\t<h3>语音总条数："+sentenceNum+"</h3>\n" +
                "\t\t\t<h3>积极语音条数："+positiveNum+"</h3>\n" +
                "\t\t\t<h3>积极语音占比："+positivePro+"</h3>\n" +
                "\t\t\t<h3>中性语音条数："+neutralNum+"</h3>\n" +
                "\t\t\t<h3>中性语音占比："+neutralPro+"</h3>\n" +
                "\t\t\t<h3>消极语音条数："+negativeNum+"</h3>\n" +
                "\t\t\t<h3>消极语音占比："+negativePro+"</h3>\n" +
                "\t\t\t<h6>监控时间区间："+startTime+" &nbsp;-&nbsp; "+endTime+"</h6>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>";
    }

    public String getNegativeNumTemp(String negativePro,String neutralPro,String positivePro,String negativeNum,String neutralNum,String positiveNum,String sentenceNum,String negativeMaxNum,String deviceName,String startTime,String endTime){
        return "<html lang=\"en\">\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "\t\t<title>情绪检测系统报警邮件</title>\n" +
                "\t\t<style type=\"text/css\">\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<div style=\"text-align: center;\">\n" +
                "\t\t\t<h1 style=\"color: red;\">情绪检测系统报警邮件-消极语音条数超过阈值("+negativeMaxNum+")</h1>\n" +
                "\t\t\t<h3>设备名："+deviceName+"</h3>\n" +
                "\t\t\t<h3>语音总条数："+sentenceNum+"</h3>\n" +
                "\t\t\t<h3>积极语音条数："+positiveNum+"</h3>\n" +
                "\t\t\t<h3>积极语音占比："+positivePro+"</h3>\n" +
                "\t\t\t<h3>中性语音条数："+neutralNum+"</h3>\n" +
                "\t\t\t<h3>中性语音占比："+neutralPro+"</h3>\n" +
                "\t\t\t<h3 style=\"color: red;\">消极语音条数："+negativeNum+"</h3>\n" +
                "\t\t\t<h3>消极语音占比："+negativePro+"</h3>\n" +
                "\t\t\t<h6>监控时间区间："+startTime+" &nbsp;-&nbsp; "+endTime+"</h6>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>";
    }

    public String getNegativeProTemp(String negativePro,String neutralPro,String positivePro,String negativeNum,String neutralNum,String positiveNum,String sentenceNum,String negativeMaxPro,String deviceName,String startTime,String endTime){
        return "<html lang=\"en\">\n" +
                "\t<head>\n" +
                "\t\t<meta charset=\"UTF-8\">\n" +
                "\t\t<title>情绪检测系统报警邮件</title>\n" +
                "\t\t<style type=\"text/css\">\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<div style=\"text-align: center;\">\n" +
                "\t\t\t<h1 style=\"color: red;\">情绪检测系统报警邮件-消极语音占比超过阈值("+negativeMaxPro+")</h1>\n" +
                "\t\t\t<h3>设备名："+deviceName+"</h3>\n" +
                "\t\t\t<h3>语音总条数："+sentenceNum+"</h3>\n" +
                "\t\t\t<h3>积极语音条数："+positiveNum+"</h3>\n" +
                "\t\t\t<h3>积极语音占比："+positivePro+"</h3>\n" +
                "\t\t\t<h3>中性语音条数："+neutralNum+"</h3>\n" +
                "\t\t\t<h3>中性语音占比："+neutralPro+"</h3>\n" +
                "\t\t\t<h3>消极语音条数："+negativeNum+"</h3>\n" +
                "\t\t\t<h3 style=\"color: red;\">消极语音占比："+negativePro+"</h3>\n" +
                "\t\t\t<h6>监控时间区间："+startTime+" &nbsp;-&nbsp; "+endTime+"</h6>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>";
    }
}
