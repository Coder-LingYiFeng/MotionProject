package com.detection.motion.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//返回的结果类
public class Status {
    //状态信息
    private String status;
    //状态描述
    private String message;
    //相关数据
    private Object data;
}
