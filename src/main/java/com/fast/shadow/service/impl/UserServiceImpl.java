package com.fast.shadow.service.impl;

import com.fast.shadow.dao.UserMapper;
import com.fast.shadow.model.User;
import com.fast.shadow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.service.impl
 * @Version:1.0
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int addUser(User user) {
        userMapper.save(user);
        return 0;
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.update(user);
    }


    @Override
    public User getUser(int id) {
        return userMapper.getById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int addUsers(List<User> users) {
        if (users == null || users.size() < 1) {
            return -1;
        }
        return userMapper.saveBatch(users);
    }


}
