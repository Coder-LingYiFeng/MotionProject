package com.detection.motion.service;

import com.detection.motion.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    User getUserByUserName(String userName);
}
