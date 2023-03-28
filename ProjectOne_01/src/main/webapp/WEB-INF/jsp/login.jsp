<%--
  Created by IntelliJ IDEA.
  User: yezi1
  Date: 2023/3/7
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>

<head>
    <base href="${ctx}/">
    <meta charset="utf-8">
    <title>登录</title>
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script>
        const ctx="${ctx}";
        //     获取上下文
        const error="${error}";
    </script>
    <script src="assets/login/js/login.js"></script>

    <!-- Meta-Tags -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <meta name="keywords" content="Prime login Form a Responsive Web Template, Bootstrap Web Templates, Flat Web Templates, Android Compatible Web Template, Smartphone Compatible Web Template, Free Webdesigns for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design">
    <script>
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    </script>
    <!-- //Meta-Tags -->
    <!-- Index-Page-CSS -->
    <link rel="stylesheet" href="assets/login/css/style.css" type="text/css" media="all">
    <!-- //Custom-Stylesheet-Links -->
    <!--fonts -->
    <link href="//fonts.googleapis.com/css?family=Mukta+Mahee:200,300,400,500,600,700,800" rel="stylesheet">
    <!-- //fonts -->
    <link rel="stylesheet" href="assets/login/css/font-awesome.css" type="text/css" media="all">
    <!-- //Font-Awesome-File-Links -->
<%--    <link rel="stylesheet" href="assets/login/css/login.css">--%>

</head>
<body>
<h1 class="title-agile text-center">PLEASE ENTER YOUR INFORMATION</h1>
<div class="content-w3ls">
    <div class="content-bottom">
        <form id="loginform" action="user/login" method="post">
            <div class="field-group">
                <div class="wthree-field">
                    <input name="username" id="username" type="text" value="${user.username}" placeholder="username" required>
                </div>
            </div>
            <div class="field-group">
                <div class="wthree-field">
                    <input name="password" id="password" type="password" value="${user.password}" placeholder="password">
                </div>
            </div>
            <div class="field-group">
                <input id="captchatext" type="text" name="captcha" placeholder="captcha">
                <img id="captcha" src="user/captcha">
                <%--                src发出请求生成动态图片--%>
            </div>
            <div class="field-group">
                <div class="check">
                    <label class="checkbox w3l">
                        <input type="checkbox" onclick="myFunction()">
                        <i> </i>show password</label>
                </div>
                <!-- script for show password -->
                <script>
                    function myFunction() {
                        var x = document.getElementById("password");
                        if (x.type === "password") {
                            x.type = "text";
                        } else {
                            x.type = "password";
                        }
                    }
                </script>
                <!-- //script for show password -->
            </div>
            <div class="wthree-field">
                <input id="saveForm" type="submit" value="sign in" />
<%--                <button id="saveForm">SIGN IN</button>--%>
<%--                435px 62px--%>
            </div>
            <div class="mb-3">
                <input id="remember-me" type="checkbox" class="form-check-input"><label class="ms-2" for="remember-me" style="user-select: none;color: white">记住我</label>
            </div>

        </form>
    </div>
</div>

</body>
</html>
