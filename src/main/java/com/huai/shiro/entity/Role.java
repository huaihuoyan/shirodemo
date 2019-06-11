package com.huai.shiro.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Role {
    private Long id;
    private String name;  //角色名称
    private String sn;  //角色表达式： empMgr
}
