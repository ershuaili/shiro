package com.example.shiro.mapper;

import com.example.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author 李二帅
 * @date 2021-09-08 20:48:01
 */
@Mapper
public interface UserMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Integer id);

    /**
     * 通过用户名查询用户
     * @param name 用户名
     * @return 用户对象
     */
    User queryByName(String name);

    /**
     * 用户注册
     * @param user 用户实例
     */
    void insert(User user);
}

