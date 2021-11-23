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
//数据库中用户表实体类
public class User {
    //自增id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //用户名
    private String userName;
    //密码
    private String passWord;
    //创建时间
    private Date createDate;

    public User(String userName) {
        this.userName = userName;
    }
    public User(Integer id,String userName){
        this.userName=userName;
        this.id=id;
    }
}
