package com.ahpu.motion.service;

import com.ahpu.motion.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User getUserByUserName(String userName);
}
