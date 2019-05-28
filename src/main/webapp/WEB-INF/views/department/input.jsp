<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/05/28
  Time: 21:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>部门列表</title>
</head>
<body>
<h1>部门${cmdType}页面</h1>
<table border="1">
    <tbody>
    <tr>
        <td>部门名称</td>
        <td><input type="text" value="${name}"/></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="保存">
            <input type="reset" value="重置">
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
