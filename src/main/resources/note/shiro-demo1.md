# shiro demo

1. 创建一个maven项目并引入一下依赖

   ```xml
   <dependency>
       <groupId>junit</groupId>
       <artifactId>junit</artifactId>
       <version>4.12</version>
   </dependency>
   
   <dependency>
       <groupId>commons-logging</groupId>
       <artifactId>commons-logging</artifactId>
       <version>1.1.3</version>
   </dependency>
   
   <dependency>
       <groupId>org.apache.shiro</groupId>
       <artifactId>shiro-core</artifactId>
       <version>1.2.2</version>
   </dependency>
   ```

   

2.  在resources文件夹中创建一个shiro.ini 的配置文件

   ```ini
   [users]
   # 模拟数据库用户列表：账号=密码
   zhangsan=666
   lisi=888
   
   ```

   

3. 在test中创建一个测试类，进行安全验证的测试

   ```java
   package nu.wolfcode;
   
   import org.apache.shiro.SecurityUtils;
   import org.apache.shiro.authc.UnknownAccountException;
   import org.apache.shiro.authc.UsernamePasswordToken;
   import org.apache.shiro.config.IniSecurityManagerFactory;
   import org.apache.shiro.mgt.SecurityManager;
   import org.apache.shiro.subject.Subject;
   import org.apache.shiro.util.Factory;
   import org.junit.Test;
   
   /**
    * @Program: shiro_demo
    * @Author: 努力就是魅力
    * @Since: 2019-02-17 14:38
    * Description:
    *     测试 shiro 认证
    *
    *     账号不存在抛出的异常
    *     org.apache.shiro.authc.UnknownAccountException: Realm [org.apache.shiro.realm.text.IniRealm@1ff8b8f] was unable to find account data for the submitted AuthenticationToken [org.apache.shiro.authc.UsernamePasswordToken - zhangsan1, rememberMe=false].
    *
    *     账号正常，密码错误抛出的异常
    *     org.apache.shiro.authc.IncorrectCredentialsException: Submitted credentials for token [org.apache.shiro.authc.UsernamePasswordToken - zhangsan, rememberMe=false] did not match the expected credentials.
    **/
   
   public class shiroTest {
   
       @Test
       public void testLogin() throws Exception{
           // 1. 创建SecurityManager工厂对象,
           // 注意： import org.apache.shiro.mgt.SecurityManager; 导包
           Factory<SecurityManager> factory = new IniSecurityManagerFactory(shiro.ini_bak);
   
           // 2. 通过工厂对象，创建SecurityManager对象
           SecurityManager securityManager = factory.getInstance();
   
           // 3. 将securityManager绑定到当前运行环境中； 让系统随时随地都可以访问securityManager对象
           SecurityUtils.setSecurityManager(securityManager);
   
           // 4. 创建当前登录的主体，注意： 此时主体没有经过认证
           Subject subject = SecurityUtils.getSubject();
   
           // 5. 绑定主体登录登录的身份/认证，即账号密码。
           // param1 : account
           // param2 : password
           UsernamePasswordToken token = new UsernamePasswordToken("zhangsan1","666");
   
           // 6. 主体登录
           try{
               subject.login(token);
   
           }catch (Exception e){
               // 登录失败
               e.printStackTrace();
           }
   
           // 7. 判断是否登录成功
           System.out.println("验证登录是否成功： " + subject.isAuthenticated());
   
           // 8. 登出（注销）
           subject.logout();
           System.out.println("验证登录是否成功 ： " + subject.isAuthenticated());
   
       }
   }
   
   ```

   