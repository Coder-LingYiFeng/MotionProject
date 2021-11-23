package com.detection.motion.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String passWord;
//    @TableField(exist = false)
    private Date createDate;

    public User(String userName) {
        this.userName = userName;
    }
    public User(Integer id,String userName){
        this.userName=userName;
        this.id=id;
    }
}
