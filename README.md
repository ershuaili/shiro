# shiro三大对象
## Subject  用户
## SecurityManage   管理所有用户
## Realm    连接数据

    /*
        anon:无需认证就可以访问
        authc:必须认证了才能访问
        user:必须拥有记住我功能才能访问
        perms:拥有对某个资源的权限才能访问
        role:拥有某个角色权限才能访问
     */

实现过程    
配置ShiroConfig
1.创建ShiroFilter
负责拦截请求
2.创建安全管理器

3.创建自定义Realm
