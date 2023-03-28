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
    <link rel="stylesheet" href="assets/book/css/booklist.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>

    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script>
        const ctx="${ctx}";
    //     获取上下文
    </script>
    <script src="assets/book/js/booklist.js"></script>
</head>
<body>
<div class="search clear">
    <form action="" method="post">
        <div>
            <label for="">ID</label>
            <input type="text" id="id" autocomplete="off" placeholder="请输入ID">
        </div>
        <div>
            <label for="">类型</label>
            <select id="type">
                <option value="">不限</option>
                <option value="小说">小说</option>
                <option value="话剧">话剧</option>
                <option value="历史">历史</option>
                <option value="爱情">爱情</option>
                <option value="励志">励志</option>
                <option value="金融">金融</option>
                <option value="儿童">儿童</option>
                <option value="科幻">科幻</option>
            </select>
        </div>

        <div>
            <label for="">书名</label>
            <input type="text" id="bookname" autocomplete="off" placeholder="请输入书名">
        </div>
        <div>
            <label for="">作者</label>
            <input type="text" id="ano" autocomplete="off" placeholder="请输入作者">
        </div>
        <div>
            <label for="">出版日期</label>
            <input type="text" id="publishDataFrom" placeholder="示例：2000-01-01" >
            <input type="text" id="publishDataTo">
        </div>

    </form>
</div>
<div class="action clear">
    <div><button id="update">修改</button></div>
    <div><button id="search">查询</button></div>
    <div><button id="delete">删除</button></div>
    <div><button id="reset">重置</button></div>
    <div><button id="export">导出</button></div>
</div>
<div class="display">
    <table id="tbl">
        <thead>
        <tr>
            <th><input type="checkbox" id="check-all"></th>
<%--            格前选项--%>
            <td>ID</td>
            <td>书名</td>
            <td>作者</td>
            <td>类型</td>
            <td>内容简介</td>
            <td>页数</td>
            <td>出版社</td>
            <td>出版时间</td>
            <td>价格</td>
            <td>借阅</td>
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
    <div class="page">
        <span>总页数：</span>
        <span id="tpage"></span>
        <span>总数：</span>
        <span id="tnum"></span>
        <span id="pagenow" style="display: none"></span>
    </div>
</div>
<div></div>
<div></div>
<div></div>
</body>



</html>
