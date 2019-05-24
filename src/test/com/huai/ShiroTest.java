package com.huai;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

public class ShiroTest {
    @Test
    public void login() throws Exception{
        /*创建SecurityManager工厂对象，加载配置文件，创建工厂对象*/
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.imi");
        /*通过工厂创建securityManager对象*/
        SecurityManager securityManager = factory.getInstance();
        /*将securityManager绑定到当前的环境中，让系统都可以访问securityManager对象*/
        SecurityUtils.setSecurityManager(securityManager);
        /*创建当前登录的主体*/
        Subject subject = SecurityUtils.getSubject();
        /*绑定主体登录的身份凭证*/
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","666");
        try {
            /*登录*/
            subject.login(token);
        }catch (Exception e){

        }
        System.out.println("登录是否成功"+subject.isAuthenticated());
        subject.logout();
        System.out.println("登录是否成功"+subject.isAuthenticated());
    }

    @Test
    public void testloginByRealms() throws Exception{
        /*创建SecurityManager工厂对象，加载配置文件，创建工厂对象*/
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.imi");
        /*通过工厂创建securityManager对象*/
        SecurityManager securityManager = factory.getInstance();
        /*将securityManager绑定到当前的环境中，让系统都可以访问securityManager对象*/
        SecurityUtils.setSecurityManager(securityManager);
        /*创建当前登录的主体*/
        Subject subject = SecurityUtils.getSubject();
        /*绑定主体登录的身份凭证*/
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","666");
        try {
            /*登录*/
            subject.login(token);
        }catch (Exception e){

        }

        System.out.println("登录是否成功"+subject.isAuthenticated());
        subject.logout();
        System.out.println("登录是否成功"+subject.isAuthenticated());
    }

    @Test
    public void testloginBymd5Realms() throws Exception{
        /*创建SecurityManager工厂对象，加载配置文件，创建工厂对象*/
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-md5.imi");
        /*通过工厂创建securityManager对象*/
        SecurityManager securityManager = factory.getInstance();
        /*将securityManager绑定到当前的环境中，让系统都可以访问securityManager对象*/
        SecurityUtils.setSecurityManager(securityManager);
        /*创建当前登录的主体*/
        Subject subject = SecurityUtils.getSubject();
        /*绑定主体登录的身份凭证*/
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","666");
        try {
            /*登录*/
            subject.login(token);
        }catch (Exception e){

        }

        System.out.println("登录是否成功"+subject.isAuthenticated());
        subject.logout();
        System.out.println("登录是否成功"+subject.isAuthenticated());
    }
    @Test
    public void loginrole() throws Exception{
        /*创建SecurityManager工厂对象，加载配置文件，创建工厂对象*/
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-role.imi");
        /*通过工厂创建securityManager对象*/
        SecurityManager securityManager = factory.getInstance();
        /*将securityManager绑定到当前的环境中，让系统都可以访问securityManager对象*/
        SecurityUtils.setSecurityManager(securityManager);
        /*创建当前登录的主体*/
        Subject subject = SecurityUtils.getSubject();
        /*绑定主体登录的身份凭证*/
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","666");

            /*登录*/
        subject.login(token);
        /*有就true，没有就false*/
        System.out.println(subject.hasRole("role1"));
        System.out.println(subject.hasAllRoles(Arrays.asList("role1","role2")));
        System.out.println(subject.hasRoles(Arrays.asList("role1","role2")));
        System.out.println(Arrays.toString(subject.hasRoles(Arrays.asList("role1","role2"))));
        /*如果有角色不做任何操作，没有就报异常*/
        subject.checkRole("role1");

        System.out.println(subject.isPermitted("user:delete"));
        System.out.println(subject.isPermitted("user:delete","user:create"));
        System.out.println(subject.isPermittedAll("user:delete","user:create"));
        System.out.println(Arrays.toString(subject.isPermitted("user:delete","user:create")));
        subject.checkPermission("user:delete");




    }

    @Test
    public void permRealm() throws Exception{
        /*创建SecurityManager工厂对象，加载配置文件，创建工厂对象*/
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permRealm.imi");
        /*通过工厂创建securityManager对象*/
        SecurityManager securityManager = factory.getInstance();
        /*将securityManager绑定到当前的环境中，让系统都可以访问securityManager对象*/
        SecurityUtils.setSecurityManager(securityManager);
        /*创建当前登录的主体*/
        Subject subject = SecurityUtils.getSubject();
        /*绑定主体登录的身份凭证*/
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","666");

        /*登录*/

        try {
            /*登录*/
            subject.login(token);
        }catch (Exception e){

        }

        /*有就true，没有就false*/
        System.out.println(subject.hasRole("role1"));



        System.out.println(subject.isPermitted("user:delete"));





    }
}
