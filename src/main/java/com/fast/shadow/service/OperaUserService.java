package com.fast.shadow.service;

import com.fast.shadow.model.User;

import java.util.List;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-27
 * @Description:com.fast.shadow.service
 * @Version:1.0
 **/


public interface OperaUserService {


    void addBatchUser(List<User> addList);

    void waitBatchUser(List<User> addList);

    void waitBatchUser2(List<User> addList);

    void waitBatchUser4(List<User> addList);


    void test();

    void test2();
}
