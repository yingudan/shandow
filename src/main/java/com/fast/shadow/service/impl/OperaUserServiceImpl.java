package com.fast.shadow.service.impl;

import com.fast.shadow.model.User;
import com.fast.shadow.service.OperaUserService;
import com.fast.shadow.service.UserService;
import com.fast.shadow.utils.BatchListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
}
