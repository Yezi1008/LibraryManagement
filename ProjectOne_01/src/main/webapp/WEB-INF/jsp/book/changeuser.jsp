<%--
  Created by IntelliJ IDEA.
  User: yezi1
  Date: 2023/3/5
  Time: 0:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <base href="${ctx}/">
    <title>修改用户信息</title>
    <link rel="stylesheet" href="assets/book/css/changeuser.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script>
        const ctx="${ctx}";
        const error = "${error}";
        const success="${success}"
    </script>
    <script src="assets/book/js/changeuser.js"></script>
</head>
<body>
<h2>请输入信息</h2>
<form id="userform" action="user/change" method="post">

    <div>
        <label for="" class="ab">用户名</label>
        <input id="bn" type="text" name="username" autocomplete="off" placeholder="用户名（必填）" value="${user.username}" readonly="true">
    </div>
    <div>
        <label for="" class="ab">原密码</label>
        <input id="au" type="password" name="oldpassword" autocomplete="off" placeholder="密码（必填）" value="">
<%--        <input type="checkbox" onclick="myFunction2()">--%>
        <script>
            function myFunction2() {
                var x = document.getElementById("oldpassword");
                if (x.type === "password") {
                    x.type = "text";
                } else {
                    x.type = "password";
                }
            }
        </script>
    </div>
    <div>
        <label for="" class="ab">新密码</label>
        <input id="ph" type="password" name="password" autocomplete="off" placeholder="密码（必填）" value="">
    </div>
    <div>
        <label for="" class="ab">重复密码</label>
        <input id="pre" type="password" name="repassword" autocomplete="off" placeholder="重复密码（必填）" value="">
    </div>
    <div>
        <label for="" class="ab">姓名</label>
        <input id="pg" type="text" name="name" autocomplete="off" placeholder="姓名（必填）" value="${user.name}">
    </div>
    <div>
        <label for="" class="ab">性别</label>
        <input id="pd" type="text" name="sex" placeholder="性别" value="${user.sex}">
    </div>
    <div>
        <label for="" class="ab">年龄</label>
        <input id="pr" type="text" name="age" autocomplete="off" placeholder="年龄" value="${user.age}">
    </div>

    <div class="btn fl">
        <button id="submitbtn" type="button">SUBMIT</button>
    </div>
    <input type="hidden" value="${user.username}">
    <input type="hidden" value="${user.password}">
    <input type="hidden" value="${user.name}">
    <input type="hidden" value="${user.sex}">
    <input type="hidden" value="${user.age}">
</form>


</body>
</html>
