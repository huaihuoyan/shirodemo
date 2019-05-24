# shiro RBAC:基于角色的权限管理

### summary

1. RBAC简单理解为：谁扮演什么角色，被允许做什么操作。
   - 用户对象：user：当前操作用户
   - 角色对象：role： 表示权限操作许可权的集合
   - 权限对象：permission:资源操作许可权

2. 例子：
   - 张三（user）下载（permission）一个高清无码的资源，需要VIP权限（role），
   - 张三——》普通用户——》VIP用户——》下载资源

### 三种权限管理实现的方式

1. 编程方式，使用if-else代码块完成
2. 注解方式：使用@RequiresRoles("admin")，需要有admin权限才可以执行
3. jsp标签方式：

```jsp
<shiro:hasRole name="admin">
	<!--执行满足权限的操作-->
</shiro:hasRole>
```

###### 使用编程方式实现权限验证

1. 首先，编写相应的配置文件

```ini
[users]
# 用户张三的密码是123，具有role1和role2两个权限
zhangsan=666,role1,role2
lisi=888,role2

[roles]
# 角色role1对资源user拥有create，update权限
role1=user:create,user:update
# 角色role2对资源user拥有create，delete权限
role2=user:create,user:delete
# 角色role3对资源user拥有create权限
role3=user:create

# ini权限配置规则：【用户=密码，角色1，角色n。】 【角色=权限1，权限2。】 首先根据用户名找角色，在根据角色找权限，角色是权限集合。

# 权限字符串的规则是； 【资源标识符：操作：资源实例标识符】

# 一般的，我们只需要关心 【资源：操作】
# *：* 表示所有资源的所有操作。admin权限
```

2. 角色验证测试代码

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

import java.util.Arrays;


/**
 * @Program: shiro_demo
 * @Author: 努力就是魅力
 * @Since: 2019-02-17 14:38
 * Description:   这里的用户名和密码是指定在配置文件中的，硬编码方式
 * 测试 shiro 认证
 * <p>
 * 账号不存在抛出的异常
 * org.apache.shiro.authc.UnknownAccountException: Realm [org.apache.shiro.realm.text.IniRealm@1ff8b8f] was unable to find account data for the submitted AuthenticationToken [org.apache.shiro.authc.UsernamePasswordToken - zhangsan1, rememberMe=false].
 * <p>
 * 账号正常，密码错误抛出的异常
 * org.apache.shiro.authc.IncorrectCredentialsException: Submitted credentials for token [org.apache.shiro.authc.UsernamePasswordToken - zhangsan, rememberMe=false] did not match the expected credentials.
 **/


public class ShiroTest {

    /**
     * 角色验证，判断用户是否拥有某个或某些角色
     * @throws Exception
     */
    @Test
    public void testHasRole() throws Exception {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "666");
        subject.login(token);
        // 进行授权操作时的前提，用户必须通过认证
        // 判断当前用户是否拥有某个角色，返回true表示拥有，false表示没有
        System.out.println(subject.hasRole("role1"));
        // 判断当前用户是否拥有一些角色，返回的Boolean类型的数组，顺序对应是否拥有权限
        System.out.println(Arrays.toString(subject.hasRoles(Arrays.asList("role1", "role2"))));
        // 判断用户是否拥有一些权限，返回一个Boolean值，
        System.out.println(subject.hasAllRoles(Arrays.asList("role1","role3")));

        // 如果有权限不做任何操作，没有权限则抛出下面的异常
        // org.apache.shiro.authz.UnauthorizedException: Subject does not have role [role3]
        subject.checkRole("role3");
        
        // org.apache.shiro.authz.UnauthorizedException: Subject does not have role [role3]
        subject.checkRoles("role1","role2","role3");
    }
}
```

3. 权限验证测试代码

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

import java.util.Arrays;


/**
 * @Program: shiro_demo
 * @Author: 努力就是魅力
 * @Since: 2019-02-17 14:38
 * Description:   这里的用户名和密码是指定在配置文件中的，硬编码方式
 * 测试 shiro 认证
 * <p>
 * 账号不存在抛出的异常
 * org.apache.shiro.authc.UnknownAccountException: Realm [org.apache.shiro.realm.text.IniRealm@1ff8b8f] was unable to find account data for the submitted AuthenticationToken [org.apache.shiro.authc.UsernamePasswordToken - zhangsan1, rememberMe=false].
 * <p>
 * 账号正常，密码错误抛出的异常
 * org.apache.shiro.authc.IncorrectCredentialsException: Submitted credentials for token [org.apache.shiro.authc.UsernamePasswordToken - zhangsan, rememberMe=false] did not match the expected credentials.
 **/


public class ShiroTest {

    /**
     * 判断用户是否拥有某个或某些权限
     * @throws Exception
     */
    @Test
    public void testHasPermission() throws Exception {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "666");
        subject.login(token);

        // 进行授权操作的前提，用户必须通过认证
        // 判断当前用户是否拥有某个权限，返回true表示拥有指定权限，false表示没有某个权限
        System.out.println(subject.isPermitted("user:delete"));  // true

        // 判断用户是否拥有一些权限, 返回true表示拥有这些权限，反之没有
        System.out.println(subject.isPermittedAll("user:list","user:create"));  // false

        // 判断用户是否拥有一些权限, 返回true表示拥有这些权限，反之没有
        System.out.println(Arrays.toString(subject.isPermitted("user:create","user:update")));  // [true, true]

        // 判断当前用户是否拥有某个权限，没有返回值，如果没有指定的权限将会抛出异常
        // org.apache.shiro.authz.UnauthorizedException: Subject does not have permission [user:list]
        subject.checkPermission("user:list");

    }
}
```

### 自定义realm进行权限验证(解决权限验证硬编码)

1. 配置自定义realm的配置文件

```ini
[main]
# 自定义realm,指定自己的realm类
myRealm=nu.shiro.realm.PermissionRealm
# 指定securityManager的Realms实现
securityManager.realms=$myRealm
```

2. 编写自定义的PermissionRealm类

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

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: shiro_demo
 * @Author: 努力就是魅力
 * @Since: 2019-02-17 15:44
 * Description:
 **/
public class PermissionRealm extends AuthorizingRealm {

    @Override
    public String getName(){
        return "PermissionRealm";
    }

    /**
     * 授权操作
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 传入参数： principalCollection : 用户认证凭证信息
        // SimpleAuthorizationInfo: 认证方法返回封装认证信息中的第一个参数，用户信息

        // 当前登录的用户信息，用户认证
        String username = (String)principalCollection.getPrimaryPrincipal();

        // 模拟查询数据库，查询用户实现指定的角色，以及用户权限
        // 角色集合
        List<String> roles = new ArrayList<String>();
        // 权限集合
        List<String> permission = new ArrayList<String>();
        roles.add("role1");

        // 假设用户在数据库中拥有user:delete权限
        permission.add("user:delete");

        // 返回用户在数据库中的权限和角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRoles(roles);
        info.addStringPermissions(permission);

        return info;
    }

    /**
     * 认证操作
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
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

3. 测试自定义的权限realm类

```java
package nu.wolfcode;

import nu.shiro.realm.PermissionRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;


/**
 * @Program: shiro_demo
 * @Author: 努力就是魅力
 * @Since: 2019-02-17 14:38
 * Description:   这里的用户名和密码是指定在配置文件中的，硬编码方式
 * 测试 shiro 认证
 * <p>
 * 账号不存在抛出的异常
 * org.apache.shiro.authc.UnknownAccountException: Realm [org.apache.shiro.realm.text.IniRealm@1ff8b8f] was unable to find account data for the submitted AuthenticationToken [org.apache.shiro.authc.UsernamePasswordToken - zhangsan1, rememberMe=false].
 * <p>
 * 账号正常，密码错误抛出的异常
 * org.apache.shiro.authc.IncorrectCredentialsException: Submitted credentials for token [org.apache.shiro.authc.UsernamePasswordToken - zhangsan, rememberMe=false] did not match the expected credentials.
 **/


public class ShiroTest {

    /**
     * 通过自定义realm验证用户是否拥有权限
     * 查看自定义的realm： {@link PermissionRealm}
     * @throws Exception
     */
    @Test
    public void testHasRoleByRealm() throws Exception {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "666");
        subject.login(token);

        // 进行授权操作的前提，用户必须通过认证
        // 判断当前用户是否拥有某个权限，返回true表示拥有指定权限，false表示没有某个权限
        System.out.println(subject.isPermitted("user:delete"));  // true

        // 判断当前用户是否拥有某个角色
        System.out.println(subject.hasRole("role1"));  // true

    }

}
```



观看到第10集完

[Shiro最新全套在线教程](https://www.bilibili.com/video/av39052886/?p=5)

