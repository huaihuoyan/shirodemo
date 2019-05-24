# Apache Shiro

### Summary

![认证授权流程图](img/认证授权流程图.png)

### shiro 组件及介绍

1. **Authentication**： 身份认证/登陆， 验证用户是否具有相应的身份；
2. **Authorization**：授权，即权限认证，验证某个已经认证的用户是否具有某个权限。判断用户能做哪些事情，常见的如：验证用户是否拥有某个角色，或者细粒度的验证某个用户对某个资源是否具有某个具有某个权限。

3. Session Manager: 会话管理，即用户登录就是一次回话，在没有退出之前，它的所有信息在会话中。会话可以是普通的JavaSE程序也可以是Web程序。
4. **Cryptography** ：加密，保护数据的安全性，如加密保存到数据库，而不是明文存储。
5. Web Support : Web支持，可以非常容易的集成到web环境。
6. Caching：缓存，比如用户登录后，其用户信息、拥有角色权限不必每次去查，这样可以提高效率。
7. Concurrency: shiro 支持多线程应用的并发验证。
8. Testing ： 提供测试支持。
9. Run As ： 允许一个用户假装另一个用户（如果是被允许的）的身份进行访问。
10. Remember Me : 记住我，这是一个常见的功能，即一次登录后，下次再来的话就不用登录的。

### shiro架构的三个主要概念

1. **Subject** : 访问系统的用户，主体是用户，程序等，进行认证的都是主体。
   - Subject 是一个安全术语，其基本意思是“当前的操作用户”。它是一个抽象概念，可以是人，也可以是第三方进程或其他类似的事物，如爬虫，robot等。
   - 在程序的任意位置： ```Subject currentUser = SecurityUtils.getSubject();```获取shiro
   - 一旦获得subject,就可以立即获取shiro的权限控制，如登陆，登出，访问会话，执行授权检查等。
2. **SecurityManager** :  安全管理器，它是shiro功能实现的核心，负责与后边介绍的其他组件（认证器，授权器，缓存控制器）进行交互，实现subject委托的各种功能。有点类似于springMVC中的DispatcherServlet。
3. Realms：Reams充当了shiro与应用安全数据间的“桥梁”或“连接器”。可以把Reams看成DataSource，即安全数据源。执行认证（登陆）和授权（访问控制）时，shiro会从应用配置的Realm中查找相关的比对数据。以确认用户是否合法，操作是否合理。

### 从架构的角度来看shiro

1. **subject**： 主体，可以看到主体可以是任何能够与应用交互的“用户”。
2. **SecurityManager**: 相当于SpringMVC中的DispatcherServlet,是shiro的心脏。所有具体的交互都是通过SecurityManager来进行控制的。它管理所有的subject、且负责进行认证授权、及会话、缓存的管理。
3. **Authenticator**: 认证器，负责主体认证。这是一个扩展点，如果用户觉得shiro默认的不好，可以自定义实现。其需要指定认证策略（Authentication Strategy），也就是说在什么情况下才算是用户认证通过了。
4. **Authorizer**: 授权器，或者访问控制器。用来决定主体是否有权进行响应的操作，控制着用户具体能访问到哪些功能。
5. Realm：可以有一个或多个Realm，可以认为是安全实体的数据源，用于获取安全实体。可以是jdbc实现也可以是LDAP实现，或者内存实现等等，由用户提供。注意：shiro不知道你的用户/权限存储在哪及以何种方式存储，所以我们一般在应用中都需要实现自己的Realm。
6. SessionManager： 如果写过servlet都知道session的概念，session需要有人去管理它的生命周期，这个组件就是Sessionmanager，而shiro并不仅仅可以用在web环境下，也可以用在普通的javaSE环境和EJB环境下；因此，shiro抽象了一个自己的session来管理主体和应用之间数据的交互。可以实现分布式会话管理。
7. SessionDAO： DAO就是数据访问对象，用于会话的CURD,比如想把session保存到数据库，那么就需要实现自己的SessionDAO，通过jdbc写到数据库。也可以存储到redis中。另外sessionDAO可以使用cache进行缓存，以提高性能。
8. CacheManager ： 缓存控制器，用来管理用户、角色、权限等对象的缓存。这些数据基本上很少会被改变，放到缓存中可以提高访问性能。
9. Cryptography: 密码模块，shiro提供了一些常见的加密/解密组件。

### shiro认证过程

![shiro认证过程](img/shiro认证过程.png)

1. 调用subject.login方法进行登录，器会自动委托给SecurityManager.login方法进行登录。
2. SecurityManager通过Authenticator（认证器）进行认证。
3. Authenticator 的实现ModularRealmAuthenticator调用realm从ini配置文件取真实账户和密码，这里使用的是IniRealm（shiro自带，相当于数据源）。
4. IniRealm先根据token中的账号去ini中找该账号，如果找不到则给ModularRealmAuthenticator返回null，如果找到匹配密码，匹配密码成功则认证通过。
5. 最后调用调用subject.logout进行注销操作。


