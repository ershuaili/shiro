package com.example.shiro.config;

import com.example.shiro.entity.User;
import com.example.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * <p>描述: [自定义realm] </p>
 * <p>创建时间: 2021/09/06 下午 04:02 </p>
 *
 * @author 李二帅
 * @version v1.0
 */

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("++++++++++执行了授权方法++++++++++");
        return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("++++++++++执行了认证方法++++++++++");
        // 获取用户的登录token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 查询用户的权限
        User user = userService.queryByName(token.getUsername());
        // 如果查询不到用户
        if (user == null) {
            // 抛出异常 UnknownAccountException
            return null;
        }
        // 密码验证
        // 参数1:返回数据库中正确的用户名
        // 参数2:返回数据库中正确密码
        // 参数3:提供当前realm的名字 this.getName()
        return new SimpleAuthenticationInfo(user.getNickname(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
    }
}
