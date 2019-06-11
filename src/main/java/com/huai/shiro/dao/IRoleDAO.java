package com.huai.shiro.dao;



import java.util.List;

public interface IRoleDAO {
    /**
     * 查找所有的角色
     * @return
     */
    List<String> getAllRoleSn();

    /**
     * 通过用户id获取角色表达式
     * @param userId
     * @return
     */
    List<String> getRoleSnByUserId(Long userId);
}
