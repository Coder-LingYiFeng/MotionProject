package com.detection.motion.mapper;

import com.detection.motion.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户表操作
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    @Select("select * from user where user_name=#{userName}")
    User getUserByUserName(String userName);

}
