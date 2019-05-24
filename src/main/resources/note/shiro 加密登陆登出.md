# shiro 加密登陆登出

### md5 Hash 加密算法介绍

1. md5使用的是对称加密算法
2. 通过一套算法将一个字符串以一定的规则打乱，对比的时候也同样将提供的字符串打乱对比，相同的算法当然得到相同的结果。

### 首先了解一下md5加密

```java
package nu.wolfcode;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * @Program: shiro_demo
 * @Author: 努力就是魅力
 * @Since: 2019-04-09 21:34
 * Description:
 *     对密码进行加密
 **/


public class MD5Test {

    @Test
    public void testMD5() throws Exception{
        String password = "666";

        // 加密 md5
        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println(md5Hash);
        // fae0b27c451c728867a567e8c1bb4e53

        // md5 加盐 加密
        md5Hash = new Md5Hash(password,"zhangsan");
        System.out.println(md5Hash);
        // 2f1f526e25fdefa341c7a302b47dd9df

        // md5 加盐 加密 + 加散列次数
        md5Hash = new Md5Hash(password,"zhangsan",3);
        System.out.println(md5Hash);
        // cd757bae8bd31da92c6b14c235668091

    }
}

```

### shiro 加密登陆登出了解一下

1. 使用上面第三个加密算法，生成加密字符串，然后自定义一个Realm。如果加盐了，需要在比对的时候设置加的盐是什么。

```java
package nu.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @Program: shiro_demo
 * @Author: 努力就是魅力
 * @Since: 2019-04-09 21:41
 * Description:  TODO
 **/


public class PasswordRealm extends AuthorizingRealm {

    @Override
    public String getName(){
        return "PasswordRealm";
    }

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 通过用户名到数据库中查用户信息，封装成一个AuthenticationInfo对象返回，方便认证器进行对比。
        // 获取token中的用户名
        String username = (String) authenticationToken.getPrincipal();

        // 通过用户名查询数据库，将该用户对应查询结果，账号与密码
        // 这里的用户名和密码是查库查出来
        if(!"zhangsan".equals(username)){
            return null;
        }

        // 假定我们从数据库查出来的密码是这个样子，
        // 加密方式： // md5 加盐 加密 + 加散列次数
        // 算法是 md5Hash = new Md5Hash(password,"zhangsan",3);
        String password = "cd757bae8bd31da92c6b14c235668091";

        // info 对象表示realm登陆对比信息：
        // 参数1 : 用户信息（真实登陆中对应的user对象）
        // 参数2 ：密码，
        // 参数3 ： 当加密后，第三个参数为加盐
        // 参数4 ： 当前realm的名字,
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password, ByteSource.Util.bytes("zhangsan"),getName());

        return info;
    }
}
```

2. 加密配置文件

```ini
# 定义加密的 配置
[main]
# 定义凭证匹配器
credentialsMatcher=org.apache.shiro.authc.credential.HashedCredentialsMatcher

# 散列算法
credentialsMatcher.hashAlgorithmName=md5

# 散列次数
credentialsMatcher.hashIterations=3

# 将凭证匹配器设置到realm
myRealm= nu.shiro.realm.PasswordRealm
myRealm.credentialsMatcher=$credentialsMatcher
securityManager.realms=$myRealm
```

3.  测试加密后登陆登出(指定自己加密的配置文件)

```java
package nu.wolfcode;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.Factory;
import org.junit.Test;


/**
 * @Program: shiro_demo
 * @Author: 努力就是魅力
 * @Since: 2019-02-17 14:38
 * Description:   这里的用户名和密码是指定在配置文件中的，硬编码方式
 *     测试 shiro 认证
 *
 *     账号不存在抛出的异常
 *     org.apache.shiro.authc.UnknownAccountException: Realm [org.apache.shiro.realm.text.IniRealm@1ff8b8f] was unable to find account data for the submitted AuthenticationToken [org.apache.shiro.authc.UsernamePasswordToken - zhangsan1, rememberMe=false].
 *
 *     账号正常，密码错误抛出的异常
 *     org.apache.shiro.authc.IncorrectCredentialsException: Submitted credentials for token [org.apache.shiro.authc.UsernamePasswordToken - zhangsan, rememberMe=false] did not match the expected credentials.
 **/


public class ShiroTest {

    /**
     * 普通的验证登录登出操作
     * @throws Exception
     */
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


    /**
     * 验证加密的登录登出操作
     * @throws Exception
     */
    @Test
    public void testLoginByPasswordRealm() throws Exception{
        // 1. 创建SecurityManager工厂对象,
        // 注意： import org.apache.shiro.mgt.SecurityManager; 导包
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-cryptography.ini");

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



观看到第6集完

[Shiro最新全套在线教程](https://www.bilibili.com/video/av39052886/?p=5)

