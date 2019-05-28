<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/05/28
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>部门列表</title>
</head>
<body>
<center>
    <h1>部门列表</h1>
    <shiro:hasPermission name="department:add">
        <a href="/department?cmd=input">新增</a>
    </shiro:hasPermission>
    <br>
    <table border="1">
        <tbody>
        <tr>
            <th>编号</th>
            <th>部门名称</th>
            <th></th>
        </tr>
        <tr>
            <td>1</td>
            <td>总经办</td>
            <td>
                <shiro:hasPermission name="department:edit">
                    <a href="/department?cmd=input&id=1">编辑</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="department:delete">
                    <a href="/department?cmd=delete">删除</a>
                </shiro:hasPermission>
            </td>
        </tr>
        </tbody>
    </table>
</center>
</body>
</html>