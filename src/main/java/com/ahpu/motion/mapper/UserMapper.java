package com.ahpu.motion.mapper;

import com.ahpu.motion.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where user_name=#{userName}")
    User getUserByUserName(String userName);

}
