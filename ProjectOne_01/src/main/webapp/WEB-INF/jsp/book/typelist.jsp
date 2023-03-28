<%--
  Created by IntelliJ IDEA.
  User: yezi1
  Date: 2023/3/2
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <base href="${ctx}/">
    <meta charset="utf-8">
    <title>图书列表</title>
    <link rel="stylesheet" href="assets/book/css/typelist.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script>
        const ctx="${ctx}";
    //     获取上下文
    </script>
    <script src="assets/book/js/typelist.js"></script>
</head>
<body>
<div class="action clear">
    <div><button id="delete">删除</button></div>
    <div><button id="add">添加</button></div>
<%--    <div><button id="edit">修改</button></div>--%>
</div>
<div class="display">
    <table id="tbl">
        <thead>
        <tr>
            <th><input type="checkbox" id="check-all"></th>
<%--            格前选项--%>
            <td>类别名</td>
            <td>总数量</td>
            <td>剩余数量</td>
            <td>借阅数量</td>

        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>


</body>



</html>
