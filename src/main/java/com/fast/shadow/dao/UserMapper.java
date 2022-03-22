package com.fast.shadow.dao;

import com.fast.shadow.model.User;
import com.gitee.fastmybatis.core.mapper.CrudMapper;
import com.gitee.fastmybatis.core.query.Query;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @Auther: yingd [RipperF@hotmail.com]
 * @Date:2022-03-22
 * @Description:com.fast.shadow.dao
 * @Version:1.0
 **/


public interface UserMapper extends CrudMapper<User, Integer> {


    // 自定义sql
    @Update("update t_user set username = #{username} where id = #{id}")
    int updateById(@Param("id") int id, @Param("username") String username);

    User selectByName(@Param("username") String username);

    List<User> findByMap(@Param("map") Map<String, Object> map);

    User getByMap(@Param("map") Map<String, Object> map);

    List<User> findJoinPage(Query query);
}
