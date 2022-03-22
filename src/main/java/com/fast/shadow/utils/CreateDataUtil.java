package com.fast.shadow.utils;

import com.fast.shadow.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.utils
 * @Version:1.0
 **/


public class CreateDataUtil {


    //方便下面添加用戶測試，num是创造多少个user实体类对象
    public static List<User> getUsers(int num) {
        List<User> tUsers = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            byte a = 1;
            User user = new User();
            user.setAddTime(new Date());
            user.setIsdel(false);
            user.setMoney(new BigDecimal(12.00));
            user.setAddTime(new Date());
            user.setRemark(i+"");
            user.setState(a);
            user.setLeftMoney(1f);
            user.setUsername("测试" + UUID.randomUUID().toString().split("-")[1]);
            tUsers.add(user);
        }
        return tUsers;
    }

    public static User getUser() {
        byte a = 1;
        User user = new User();
        user.setAddTime(new Date());
        user.setIsdel(false);
        user.setMoney(new BigDecimal(14.00));
        user.setAddTime(new Date());
        user.setRemark("添加用户");
        user.setState(a);
        user.setLeftMoney(2f);
        user.setUsername("测试" + UUID.randomUUID().toString().split("-")[1]);
        return user;
    }
}
