package com.huai.shiro.dao;



import com.huai.shiro.entity.Permission;

import java.util.List;

public interface IPermissionDAO {

    /**
     * 保存权限对象
     * @param permission
     */
    void save(Permission permission);

    /**
     * 获取员工的权限表达式
     * @param userId
     * @return
     */
    List<String> getPermissionResourceByUserId(Long userId);


    List<String> getAllResources();
}
