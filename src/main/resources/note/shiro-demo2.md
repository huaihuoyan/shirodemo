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

   

2. 在resources文件夹中创建一个shiro-realm.ini 的配置文件

   ```ini
   
   # 自定义realm,指定自己的realm类
   myRealm=nu.shiro.realm.MyRealm
   # 指定securityManager的Realms实现
   securityManager.realms=$myRealm
   
   ```

   

3. 创建自己的Realm

   ```java
   package nu.shiro.realm;
   
   import org.apache.shiro.authc.AuthenticationException;
   import org.apache.shiro.authc.AuthenticationInfo;
   import org.apache.shiro.authc.AuthenticationToken;
   import org.apache.shiro.authc.SimpleAuthenticationInfo;
   import org.apache.shiro.authz.AuthorizationInfo;
   import org.apache.shiro.authz.SimpleAuthorizationInfo;
   import org.apache.shiro.realm.AuthorizingRealm;
   import org.apache.shiro.subject.PrincipalCollection;
   
   /**
    * @Program: shiro_demo
    * @Author: 努力就是魅力
    * @Since: 2019-02-17 15:44
    * Description:
    **/
   
   
   public class MyRealm  extends AuthorizingRealm {
   
       @Override
       public String getName(){
           return "MyRealm";
       }
   
       // 授权操作
       @Override
       protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
           return null;
       }
   
       // 认证操作
       @Override
       protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
   
           // 通过用户名到数据库中查用户信息，封装成一个AuthenticationInfo对象返回，方便认证器进行对比。
           // 获取token中的用户名
           String username = (String) authenticationToken.getPrincipal();
   
           // 通过用户名查询数据库，将该用户对应查询结果，账号与密码
           if(!"zhangsan".equals(username)){
               return null;
           }
   
           String password = "666";
   
           // info 对象表示realm登陆对比信息：
           // 参数1 : 用户信息（真实登陆中对应的user对象）
           // 参数2 ：密码，
           // 参数3 ： 当前realm的名字
           SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());
   
           return info;
       }
   }
   
   ```

   

4. 在test中创建一个测试类，进行安全验证的测试

   ```java
   package nu.wolfcode;
   
   import org.apache.shiro.SecurityUtils;
   import org.apache.shiro.authc.UsernamePasswordToken;
   import org.apache.shiro.config.IniSecurityManagerFactory;
   import org.apache.shiro.mgt.SecurityManager;
   import org.apache.shiro.subject.Subject;
   import org.apache.shiro.util.Factory;
   import org.junit.Test;
   
   /**
    * @Program: shiro_demo
    * @Author: 努力就是魅力
    * @Since: 2019-02-17 15:49
    * Description:
    **/
   
   
   public class ShiroMyRealmTest {
   
       @Test
       public void testLoginByRealm() throws Exception{
           // 1. 创建SecurityManager工厂对象,
           // 注意： import org.apache.shiro.mgt.SecurityManager; 导包
           Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
   
           // 2. 通过工厂对象，创建SecurityManager对象
           SecurityManager securityManager = factory.getInstance();
   
           // 3. 将securityManager绑定到当前运行环境中； 让系统随时随地都可以访问securityManager对象
           SecurityUtils.setSecurityManager(securityManager);
   
           // 4. 创建当前登录的主体，注意： 此时主体没有经过认证
           Subject subject = SecurityUtils.getSubject();
   
           // 5. 绑定主体登录登录的身份/认证，即账号密码。
           // param1 : account
           // param2 : password
           UsernamePasswordToken token = new UsernamePasswordToken("zhangsan","666");
   
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






观看到第五集完

[Shiro最新全套在线教程](https://www.bilibili.com/video/av39052886/?p=5)

