package com.ahpu.motion.controller;


import com.ahpu.motion.bean.Status;
import com.ahpu.motion.bean.User;
import com.ahpu.motion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRecord")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Status register(@RequestBody User user) {
        String userName = user.getUserName();
        String passWord = user.getPassWord();
        if ("".equals(userName) || "".equals(passWord) || userName == null || passWord == null)
            return new Status("ERROR", "用户名或密码为空", null);

        User userInfo = userService.getUserByUserName(userName);
//        System.out.println("userInfo = " + userInfo);
        if (userInfo == null) {
            User tempUser = new User(userName);
            userService.save(tempUser);
            return new Status("OK", "注册成功", tempUser);
        }
        return new Status("ERROR", "用户名已存在", null);
    }

    @PostMapping("/login")
    public Status login(@RequestBody User user) {
        String userName = user.getUserName();
        String passWord = user.getPassWord();
//        System.out.println("passWord = " + passWord);
        if ("".equals(userName) || "".equals(passWord) || userName == null || passWord == null)
            return new Status("ERROR", "用户名或密码为空", null);

        User userInfo = userService.getUserByUserName(userName);

        if (userInfo != null) {
            if (passWord.equals(userInfo.getPassWord()))
                return new Status("OK", "登陆成功", new User(userInfo.getId(),userInfo.getUserName()));
            return new Status("ERROR", "密码错误", null);
        } else {
            return new Status("ERROR", "用户名不存在", null);

        }

    }
}
