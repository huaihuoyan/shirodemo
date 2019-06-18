package com.huai.shiro.dao;


import com.huai.shiro.entity.User;

import java.util.List;

public interface IUserDAO {
    /**
     * 通过用户名查找用户对象
     * @param username
     * @return
     */
    User getUserByUsername(String username);

//    List<User> selectUser();
}
