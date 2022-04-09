package com.fast.shadow.controller;

import com.fast.shadow.model.User;
import com.fast.shadow.service.OperaUserService;
import com.fast.shadow.service.UserService;
import com.fast.shadow.utils.CreateDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.controller
 * @Version:1.0
 **/


@RestController
public class TestController {

    @Autowired
    UserService userService;

    @Autowired
    OperaUserService operaUserService;


    @GetMapping("/test")
    public String test() {
        return "Demo";
    }


    @GetMapping("/testConnection")
    public String testConnection() {
        for (int i = 0; i < 10; i++) {
            addTest();
        }
        return "成功";
    }

    @GetMapping("/testConnection2")
    public String testConnection2() {
        for (int i = 0; i < 10; i++) {
            operaUserService.waitBatchUser(CreateDataUtil.getUsers(10));
        }
        return "成功2";
    }

    @GetMapping("/testConnection3")
    public String testConnection3() {
        for (int i = 0; i < 10; i++) {
            operaUserService.waitBatchUser2(CreateDataUtil.getUsers(10));
        }
        return "成功2";
    }

    @GetMapping("/testConnection4")
    public String testConnection4() {
        for (int i = 0; i < 10; i++) {
            operaUserService.waitBatchUser4(CreateDataUtil.getUsers(10));
        }
        return "成功2";
    }


    @GetMapping("/testConnection5")
    public String testConnection5() {
        for (int i = 0; i < 10; i++) {
            operaUserService.test();
        }
        return "成功2";
    }

    @GetMapping("/testConnection6")
    public String testConnection6() {
        for (int i = 0; i < 10; i++) {
            operaUserService.test2();
        }
        return "成功6";
    }

    private void addTest() {
        try {
            User user = userService.getUser(1);
            Thread.sleep(10000l);
            userService.addUser(CreateDataUtil.getUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
