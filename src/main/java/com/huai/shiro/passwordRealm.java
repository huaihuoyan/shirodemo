package com.huai.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class passwordRealm extends AuthorizingRealm {

    public String getName(){
        return "passwordRealm";
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println(authenticationToken);
        /*获取authenticationToken中的用户名*/
        String username = (String) authenticationToken.getPrincipal();
        if(!"zhangsan".equals(username)){
            return null;
        }
        /*模拟数据库中保存的加密的密文*/
        String password = "cd757bae8bd31da92c6b14c235668091";
        /*登录Realm对比信息*/
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password, ByteSource.Util.bytes("zhangsan"),
                 getName());
        return info;
    }
}
