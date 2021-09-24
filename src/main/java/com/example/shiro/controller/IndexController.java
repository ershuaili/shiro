package com.example.shiro.controller;

import com.example.shiro.entity.User;
import com.example.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>描述: [登录控制器] </p>
 * <p>创建时间: 2021/09/06 下午 12:20 </p>
 *
 * @author 李二帅
 * @version v1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private UserService userService;

    /**
     * 前端首页展示任务和任务详细信息列表
     *
     * @return index
     */
    @GetMapping("/")
    public String toIndex() {
        return "index";
    }

    @GetMapping("add")
    public String toAdd() {
        return "add";
    }

    @GetMapping("update")
    public String toUpdate() {
        return "update";
    }

    /**
     * 用户登录验证
     *
     * @param userName 用户名
     * @param password 密码
     * @return result
     */
    @RequestMapping("login")
    public String login(String userName, String password) {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        // 登录验证
        try {
            // 创建用户token    验证登录
            subject.login(new UsernamePasswordToken(userName, password));
            return "redirect:/";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误!");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "login";
    }


    /**
     * 用户退出
     * @return 视图
     */
    @RequestMapping("logout")
    public String logout(){
        // 获取用户主体对象
        Subject subject = SecurityUtils.getSubject();
        // 用户退出
        subject.logout();
        return "redirect:login";
    }

    /**
     * 用户注册
     *
     * @return 视图
     */
    @RequestMapping("register")
    public String register(User user) {
        try {
            userService.insert(user);
            return "redirect:login";
        } catch (Exception e) {
            e.printStackTrace();
            return "register";
        }
    }
}

