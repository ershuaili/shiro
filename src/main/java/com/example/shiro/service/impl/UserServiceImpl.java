package com.example.shiro.service.impl;

import com.example.shiro.entity.User;
import com.example.shiro.mapper.UserMapper;
import com.example.shiro.service.UserService;
import com.example.shiro.utils.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author 李二帅
 * @since 2021-09-08 20:48:01
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userMapper.queryById(id);
    }

    /**
     * 通过用户名查询用户
     * @param name 用户名
     * @return 用户对象
     */
    @Override
    public User queryByName(String name) {
        return this.userMapper.queryByName(name);
    }

    /**
     * 用户注册
     *
     * @param user 用户实例
     */
    @Override
    public void insert(User user) {
        //处理业务调用mapper
        //1.生成随机盐
        String salt = SaltUtils.getSalt(15);
        //2.将随机盐保存到数据
        user.setSalt(salt);
        //3.明文密码进行md5 + salt + hash散列
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,1024);
        user.setPassword(md5Hash.toHex());
        userMapper.insert(user);
    }
}
