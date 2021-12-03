package com.detection.motion.controller;


import com.detection.motion.bean.Status;
import com.detection.motion.bean.User;
import com.detection.motion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆注册接口
 */
@RestController
@RequestMapping("/userRecord")
public class LoginController {

    @Autowired
    UserService userService;

    /**
     * 注册接口
     * @param user 必要参数 userName passWord
     * @return 返回注册的用户名
     */
    @PostMapping("/register")
    public Status register(@RequestBody User user) {
        //获取参数及参数校验
        String userName = user.getUserName();
        String passWord = user.getPassWord();
        if ("".equals(userName) || "".equals(passWord) || userName == null || passWord == null)
            return new Status("ERROR", "用户名或密码为空", null);

        //查询用户名是否存在
        User userInfo = userService.getUserByUserName(userName);
//        System.out.println("userInfo = " + userInfo);
        //不存在则添加用户
        if (userInfo == null) {
            User tempUser = new User(userName,passWord);
            userService.save(tempUser);
            return new Status("OK", "注册成功", new User(userName));
        }
        return new Status("ERROR", "用户名已存在", null);
    }

    /**
     * 登陆接口
     * @param user 必要参数 userName passWord
     * @return 返回用户在user表中的id（后续需要使用到）及用户名
     */
    @PostMapping("/login")
    public Status login(@RequestBody User user) {
        //参数获取及校验
        String userName = user.getUserName();
        String passWord = user.getPassWord();
//        System.out.println("passWord = " + passWord);
        if ("".equals(userName) || "".equals(passWord) || userName == null || passWord == null)
            return new Status("ERROR", "用户名或密码为空", null);

        //获取用户信息
        User userInfo = userService.getUserByUserName(userName);

        //查询到用户信息
        if (userInfo != null) {
            if (passWord.equals(userInfo.getPassWord()))
                return new Status("OK", "登陆成功", new User(userInfo.getId(),userInfo.getUserName()));
            return new Status("ERROR", "密码错误", null);
        } else {
            return new Status("ERROR", "用户名不存在", null);

        }

    }
}
