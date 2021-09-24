package com.example.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>描述: [自定义shiro] </p>
 * <p>创建时间: 2021/09/06 下午 02:55 </p>
 *
 * @author 李二帅
 * @version v1.0
 */
@Configuration
public class ShiroConfig {
    /**
     * ShiroFilterFactoryBean
     * @param defaultWebSecurityManager defaultWebSecurityManager
     * @return bean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        // 添加内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/login","anon");
        filterMap.put("/register","anon");
        filterMap.put("/*","authc");

        // 设置登录界面
        bean.setLoginUrl("/login");
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }

    /**
     * DefaultWebSecurityManager
     * @param realm userRealm
     * @return securityManager
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 关联Realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    /**
     * 创建Realm对象,  需要自定义
     * @return UserRealm    自定义realm
     */
    @Bean
    public Realm getRealm(){
        UserRealm userRealm = new UserRealm();
        // 修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 设置散列次数
        credentialsMatcher.setHashIterations(1024);
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

}
