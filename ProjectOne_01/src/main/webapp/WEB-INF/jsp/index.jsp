<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <base href="${ctx}/">
    <meta charset="utf-8" />
    <title>图书管理系统</title>
    <link rel="stylesheet" href="assets/index/css/index.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script src="assets/index/js/index.js"></script>

    <script>
        const ctx="${ctx}";
        const error="${error}"
        //const usernamemsg="Global.USER_LOGIN_KEY.username";
        //     获取上下文
    </script>
</head>
<body>
<header>
    <img src="assets/index/css/book-stack.png">
    <img id="title" src="assets/index/css/title.png">
    <a class="fr" id="logout" href="javascript:void(0)">退出</a>
    <h5 class="fr" style="color: white;margin-top: 40px; margin-right: 20px">尊敬的用户${user.username}你好，欢迎使用本系统</h5>
</header>

<div class="ctr clear">
    <div class="fl lft">
        <ul class="nav">
            <li><a href="book/list">图书管理</a></li>
            <li><a href="book/add">添加图书</a></li>
            <li><a href="type/list">图书模块管理</a></li>
            <li><a href="borrow/list">借阅列表</a></li>

            <li><a id="edit" href="javascript:void(0)">修改个人信息</a></li>
        </ul>
        <input type="hidden" id="username" style="color: transparent" value="${user.username}">
<%--        <input type="hidden" id="password" value="${user.password}">--%>
    </div>

    <div class="fr main">
        <iframe src=""></iframe>
    </div>

</div>

</body>
</html>