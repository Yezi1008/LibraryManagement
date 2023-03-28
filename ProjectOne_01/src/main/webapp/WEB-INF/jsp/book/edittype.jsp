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
    <title>修改类别信息</title>
    <link rel="stylesheet" href="assets/book/css/addtype.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script>
        const ctx="${ctx}";
        const error = "${error}";
        const type = "${book.type}";
    </script>
    <script src="assets/book/js/edittype.js"></script>
</head>
<body>
<h1>请输入信息</h1>
<form id="typeform" action="type/add" method="post">
    <div>
        <label for="" class="ab">类型</label>
        <input id="pg" type="text" name="typename" autocomplete="off" placeholder="类型(必填)" value="${type.typename}">
    </div>
    <div class="btn fl">
        <button id="submitbtn" type="button">SUBMIT</button>
    </div>

</form>


</body>
</html>
