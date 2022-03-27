package com.fast.shadow.controller;

import com.fast.shadow.model.User;
import com.fast.shadow.service.OperaUserService;
import com.fast.shadow.service.UserService;
import com.fast.shadow.utils.BatchListUtil;
import com.fast.shadow.utils.CreateDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.controller
 * @Version:1.0
 **/
@RestController
@RequestMapping(value = "/user/fastmybatis/")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    OperaUserService operaUserService;

    @PostMapping("addBatchUser")
    public String addBatchUser() {
        for (int i = 0; i < 10; i++) {
            List<User> users = CreateDataUtil.getUsers(100000);
            List<List<User>> lists = BatchListUtil.groupList(users, 10000);
            for (List<User> userList : lists) {
                operaUserService.addBatchUser(userList);
            }
        }
        return "addBatchUser" + "成功";
    }

    @PostMapping("addUser")
    public String addUser() {
        try {
            userService.addUser(CreateDataUtil.getUser());
            Thread.sleep(4000l);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "以fastmybatis方式" + "添加一个对象数据成功";
    }

    /**
     * @description:添加num个用户
     * @author:MuJiuTian
     * @createDate: 2019/6/8 14:24
     */
    @PostMapping("/addUsers")
    public String addUsers(int num) {
        userService.addUsers(CreateDataUtil.getUsers(num));
        return "以fastmybatis方式" + "批量添加数据成功";
    }


    /**
     * @description:逻辑意义上删除用户,如果你想把数据库中的数据真正删除，那么修改实体类中，把@LogicDelete这个注解去掉
     * @author:MuJiuTian
     * @createDate: 2019/6/8 14:24
     */
    @DeleteMapping(value = "/deleteUser")
    public String deleteUser(int id) {
        userService.deleteUser(id);
        return "以fastmybatis方式" + "删除成功";
    }

    /**
     * @description:查找某个用户
     * @author:MuJiuTian
     * @createDate: 2019/6/8 14:25
     */
    @GetMapping(value = "/getUser")
    public String getUser(int id) {
        User tUser = userService.getUser(id);
        return "以fastmybatis方式获取对象数据：" + tUser.toString();
    }


}
