package com.ahpu.motion.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String passWord;
//    @TableField(exist = false)
    private Date createDate;

    public User(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
}
