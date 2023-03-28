<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <base href="${ctx}/">
    <meta charset="utf-8">
    <title>借阅列表</title>
    <link rel="stylesheet" href="assets/book/css/borrowlist.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>

    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script>
        const ctx="${ctx}";
    //     获取上下文
    </script>
    <script src="assets/book/js/borrowlist.js"></script>
</head>
<body>
<div class="search clear">
    <form action="" method="post">
<%--        <div>--%>
<%--            <label for="">ID</label>--%>
<%--            <input type="text" id="id" name="id" autocomplete="off" placeholder="请输入ID">--%>
<%--        </div>--%>

        <div>
            <label for="">书名</label>
            <input type="text" id="bookname" name="bookname" autocomplete="off" placeholder="请输入书名">
        </div>
        <div>
            <label for="">姓名</label>
            <input type="text" id="name" name="name" autocomplete="off" placeholder="请输入姓名">
        </div>

    </form>
</div>
<div class="action clear">
<%--    <div><button id="update">修改</button></div>--%>
    <div><button id="search">查询</button></div>
    <div><button id="delete">删除</button></div>
    <div><button id="reset">重置</button></div>
</div>
<div class="display">
    <table id="tbl">
        <thead>
        <tr>
            <th><input type="checkbox" id="check-all"></th>
<%--            格前选项--%>
            <td>ID</td>
            <td>姓名</td>
            <td>性别</td>
            <td>年龄</td>
            <td>书名</td>
            <td>借阅时间</td>
            <td>联系方式</td>
        </tr>
        </thead>
        <tbody>

        </tbody>
    </table>
</div>
<div class="paginate clear">
    <ul>
        <li class="first"><a href="#">首页</a></li>
        <li class="prev"><a href="#">上一页</a></li>

        <li class="next"><a href="#">下一页</a></li>
        <li class="last"><a href="#">尾页</a></li>
    </ul>
<%--    <div class="page">--%>
<%--        <span>总页数：</span>--%>
<%--        <span id="tpage"></span>--%>
<%--        <span>总数：</span>--%>
<%--        <span id="tnum"></span>--%>
<%--        <span id="pagenow" style="display: none"></span>--%>
<%--    </div>--%>
</div>
<div></div>
<div></div>
<div></div>
</body>



</html>
