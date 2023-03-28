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
    <title>添加图书信息</title>
    <link rel="stylesheet" href="assets/book/css/addbook.css">
    <script src="assets/lib/jquery/jquery-3.6.3.min.js"></script>
    <script src="assets/lib/layer/layer.js"></script>
    <script src="assets/lib/laydate/laydate.js"></script>
    <script>
        const ctx="${ctx}";
        const erroradd = "${erroradd}";
        const type = "${book.type}";
    </script>
    <script src="assets/book/js/addbook.js"></script>
</head>
<body>
<h1>请输入信息</h1>
<form id="bookform" action="book/add" method="post">

    <div>
        <label for="typelist" class="type1">类型</label>
        <select id="typelist" name="typelist">
<%--            <option value="小说">小说</option>--%>
<%--            <option value="话剧">话剧</option>--%>
<%--            <option value="历史">历史</option>--%>
<%--            <option value="爱情">爱情</option>--%>
<%--            <option value="励志">励志</option>--%>
<%--            <option value="金融">金融</option>--%>
<%--            <option value="儿童">儿童</option>--%>
<%--            <option value="科幻">科幻</option>--%>
        </select>
        <label for="" class="ab">ID</label>
        <input type="text" name="id" autocomplete="off" placeholder="ID（必填）" value="${book.id}">
    </div>
    <div>
        <label for="" class="ab">书名</label>
        <input id="bn" type="text" name="bookName" autocomplete="off" placeholder="书名（必填）" value="${book.bookName}">
        <label for="" class="ab">作者</label>
        <input id="au" type="text" name="author" autocomplete="off" placeholder="作者（必填）" value="${book.author}">
    </div>
    <div>
        <label for="" class="ab">页数</label>
        <input id="pg" type="text" name="pages" autocomplete="off" placeholder="页数（选填）" value="${book.pages}">
        <label for="" class="ab">出版社</label>
        <input id="ph" type="text" name="publishingHouse" autocomplete="off" placeholder="出版社（选填）" value="${book.publishingHouse}">
    </div>
    <div>
        <label for="" class="ab">出版日期</label>
        <input id="pd" type="text" name="publishDate" placeholder="出版日期(选填)" value="${book.publishDate}">
        <label for="" class="ab">价格</label>
        <input id="pr" type="text" name="price" autocomplete="off" placeholder="价格（选填）" value="${book.price}">
    </div>
    <div>
        <label for="" class="borrow">借阅</label>
        <select name="borrowing">
            <option value="是">是</option>
            <option value="否">否</option>
        </select>
    </div>
    <div class="briefIntroduction">
        <label for="" class="ab">内容简介</label>
    </div>
    <div class="dis">
        <textarea type="text" name="briefIntroduction" autocomplete="off" placeholder="内容简介 一百字以内（选填）">${book.briefIntroduction}</textarea>
    </div>
    <div class="btn fl">
        <button id="reset" type="button">RESET</button>
        <button id="submitbtn" type="button">SUBMIT</button>
    </div>

</form>


</body>
</html>
