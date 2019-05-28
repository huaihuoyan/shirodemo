<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/05/28
  Time: 21:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>主页面</title>
    <link href="/static/style/main_css.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/static/js/jquery.js"></script>
    <script type="text/javascript" src="/static/js/main.js"></script>
</head>
<body>
<div class="top">
    <div class="logo">
        <h3>怀火焰科技有限公司</h3>
    </div>
    <div class="userinfo">
        当前登陆用户：<shiro:principal></shiro:principal>
        <a href="/loginOut">退出</a>
    </div>
</div>
<div class="center">
    <div class="center_left">
        <ul>
            <li>
                <a href="javascript:;" class="changePage" data-url="/employee">员工管理</a>
            </li>
            <li>
                <a href="javascript:;" class="changePage"  data-url="/department">部门管理</a>
            </li>
        </ul>
    </div>
    <div class="center_content">
        <iframe name="right" id="rightMain" src="/welcome.jsp" frameborder="no" scrolling="auto" width="100%" height="100%" allowtransparency="true">
        </iframe>
    </div>
</div>
</body>
</html>
