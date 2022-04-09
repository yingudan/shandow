package com.fast.shadow.service.impl;

import com.alibaba.fastjson.JSON;
import com.fast.shadow.dao.UserMapper;
import com.fast.shadow.model.User;
import com.fast.shadow.service.OperaUserService;
import com.fast.shadow.service.UserService;
import com.fast.shadow.utils.BatchListUtil;
import com.fast.shadow.utils.CreateDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-27
 * @Description:com.fast.shadow.service.impl
 * @Version:1.0
 **/

@Service
@Slf4j
public class OperaUserServiceImpl implements OperaUserService {

    @Autowired
    UserService userService;

    @Resource
    UserMapper userMapper;


    @Override
    @Async("threadPoolTaskExecutor")
    public void addBatchUser(List<User> addList) {
        if (CollectionUtils.isEmpty(addList)) {
            return;
        }
        long beginTime = System.currentTimeMillis();//记录任务开始时间
        List<List<User>> lists = BatchListUtil.groupList(addList, 500);

        try {
            TimeUnit.MILLISECONDS.sleep(2000);//模拟任务执行
            for (List<User> userList : lists) {
                userService.addUsers(userList);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();//记录任务结束时间
        log.info("耗时={}", endTime - beginTime);

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void waitBatchUser(List<User> addList) {
        try {
            //模拟等待
            User user = userMapper.getById(8);
            log.info("查询user:" + JSON.toJSONString(user));
            Thread.sleep(10000l);
            userMapper.saveBatch(addList);
        } catch (Exception e) {

        }
    }

    @Override
    public void waitBatchUser2(List<User> addList) {
        try {
            //模拟等待
            User user = userMapper.getById(8);
            log.info("查询user:" + JSON.toJSONString(user));
            Thread.sleep(20000l);
            userMapper.saveBatch(addList);
        } catch (Exception e) {

        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void waitBatchUser4(List<User> addList) {
        try {
            //模拟等待
            User user = userMapper.getById(8);
            log.info("查询user:" + JSON.toJSONString(user));
            Thread.sleep(20000l);
            userMapper.saveBatch(addList);
        } catch (Exception e) {

        }
    }

    @Override
    @Async("threadPoolTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void test() {
        try {
            User user = userMapper.getById(8);
            log.info("查询user:" + JSON.toJSONString(user));
            //模拟等待
            Thread.sleep(20000l);
            userMapper.saveBatch(CreateDataUtil.getUsers(10));
        } catch (Exception e) {

        }

    }

    @Override
    @Async("threadPoolTaskExecutor")
    @Transactional(rollbackFor = {Exception.class})
    public void test2() {
        try {
            User user = userMapper.getById(8);
            log.info("查询user:" + JSON.toJSONString(user));
            //模拟等待
            Thread.sleep(20000l);
            userMapper.saveBatch(CreateDataUtil.getUsers(10));
        } catch (Exception e) {

        }
    }


}
