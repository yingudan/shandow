package com.fast.shadow.service;

import com.fast.shadow.model.User;
import com.gitee.fastmybatis.core.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.service
 * @Version:1.0
 **/
public interface UserService {

    int addUser(User user);

    int deleteUser(int id);

    int updateUser(User user);

    User getUser(int id);

    int addUsers(List<User> users);


}
