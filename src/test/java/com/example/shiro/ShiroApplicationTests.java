package com.example.shiro;

import com.example.shiro.config.UserRealm;
import com.example.shiro.service.UserService;
import com.example.shiro.utils.SaltUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ShiroApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void test(){
        // System.out.println(SaltUtils.getSalt(10));

        System.out.println(userService.queryByName("123"));
    }

    @Test
    void contextLoads() {
        //1.创建安全管理器对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.给安全管理器设置realm
        securityManager.setRealm(new UserRealm());
        //3.SecurityUtils给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //4.关键对象subject主体
        Subject subject = SecurityUtils.getSubject();
        //5.创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("1", "123");
        try {
            System.out.println("认证状态" + subject.isAuthenticated());//false
            //用户认证
            subject.login(token);
            System.out.println("认证状态" + subject.isAuthenticated());
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("认证失败，用户名不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("认证失败，密码错误");
        }
    }

    @Test
    void testRealm() {
        //1.创建安全管理对象 securityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //2.给安全管理器设置realm（设置为自定义realm获取认证数据）
        defaultSecurityManager.setRealm(new UserRealm());
        //IniRealm realm = new IniRealm("classpath:shiro.ini");

        //3.给安装工具类中设置默认安全管理器
        SecurityUtils.setSecurityManager(defaultSecurityManager);

        //4.获取主体对象subject
        Subject subject = SecurityUtils.getSubject();

        //5.创建token令牌
        UsernamePasswordToken token = new UsernamePasswordToken("1", "1");
        try {
            subject.login(token);//用户登录
            System.out.println("登录成功~~");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误!!");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误!!!");
        }catch (LockedAccountException e){
            e.printStackTrace();
            System.out.println("账户锁定");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("身份验证错误");
        }
    }

}
