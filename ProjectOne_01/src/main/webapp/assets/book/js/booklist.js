$(function (){
    //search(1,10);
    let pageSize=10;
    //在未对页数进行操作时，显示第一页的数据
    //触发事件的选择器
    $(".paginate>ul").on("click","li",function (e){
        e.preventDefault();//取消默认行为
        let val=$(this).text().trim();
        let pageNo=parseInt($("#pagenow").text());
        let pages = parseInt($("#tpage").text());
        //text取内容，trim去除字符串空格
        if (val==="首页"){
            search(1,10);
            console.log(pageNo);
        }else if (val==="上一页"){
            pageNo--;
            if (pageNo<1){
                pageNo=1;
            }
            search(pageNo,10);
            console.log(pageNo);
        }else if (val==="下一页"){
            pageNo++;
            if (pageNo>pages){
                pageNo=pages;
            }
            search(pageNo,10);
            console.log(pageNo);
        }else if (val==="尾页"){
            // let ps=parseInt($("#tnum").text())%10;
            search(pages,10);
            console.log(pageNo);
        } else{//数字页码
            search(parseInt(val),10);
        }
    });
    //修改
    $("#update").click(function (){
        //可以尝试批量修改
        let $ch=$("#tbl>tbody>tr>td:first-child>:checked");
        if ($ch.length===0){
            layer.msg("请选中一本书");
        }else if ($ch.length>1){
            layer.msg("一次只能选中一本书！");
        }else {
            let id = $ch.closest("tr").children().eq(1).text();
            location.href=ctx+"/book/edit?id="+id;
        }
    });
    //查询
    $("#search").click(function (){
        search(1, pageSize);
    });
    $("#search").trigger("click");
    //重置
    $("#reset").click(function () {
        $(".search>form")[0].reset();
        search(1,10);
    });
    //删除
    $("#delete").click(function () {
        //已经选中的元素
        let $chs = $("#tbl>tbody>tr>td:first-child>:checked");
        if ($chs.length === 0) {
            layer.msg("请选中您要删除的记录");
        } else {
            let ids = [];
            $chs.each(function (idx, item) {
                let id = $(this).closest("tr").children().eq(1).text();
                ids.push(id);
            });

            if (ids.length > 0) {
                layer.confirm("是否确认删除", function () {
                    deleteByIds();
                });
            }
            function deleteByIds() {
                let url = ctx + "/book/delete";
                $.ajax(url, {
                    method: "post",
                    data: {
                        ids
                    },
                    dataType: "json",
                    traditional: true,//可以向后台提交数组参数
                    success: function (resp) {//成功之后的回调函数
                        if (resp.success) {
                            layer.msg(resp.msg || "删除成功");
                            location.reload();
                        } else {
                            layer.alert(resp.error || "删除失败，请稍候再试");
                        }
                    },
                    error: function (resp) {//失败之后的回调函数
                        layer.alert(resp.error || "删除失败，请稍候再试");
                    }
                });
            }

        }
    });
    //导出功能
    $("#export").click(function () {
        let ids = [];
        let $checked = $("#tbl>tbody>tr>td:first-child>:checked");

        if ($checked.length === 0) {
            layer.msg("请选择您要导出的图书");
        } else {
            let url = ctx + "/book/export?";
            $checked.each(function (idx, item) {
                let id = $(this).closest("tr").children().eq(1).text();
                ids.push(id);
                //在集合中添加元素
            });
            if (ids.length>0){
                layer.confirm("是否确认导出",function (){
                    for (let i = 0; i < ids.length; i++) {
                        url += "&ids=" + ids[i];
                    }
                    location.href = url;
                    //用于页面跳转
                })
            }

        }
    });

    //日期选择表
    laydate.render({
        elem: "#publishDataFrom"
    });

    laydate.render({
        elem: "#publishDataTo"
    });

    //全选与取消全选
    $("#tbl #check-all").click(function () {
        let checked = $(this).prop("checked");
        $("#tbl tbody>tr>td:first-child>:checkbox").prop("checked", checked);
    });

    //选中行
    $("#tbl>tbody").on("click", "tr>td:not(:first-child)", function () {
        let $tr = $(this).parent();
        let $ch = $tr.children().eq(0).children();
        $ch.prop("checked", !$ch.prop("checked"));
    });
});

function search(pageNo=1,pageSize=10){
    let id = $(".search #id").val();
    let type = $(".search #type").val();
    let bookname = $(".search #bookname").val();
    let ano = $(".search #ano").val();
    let publishDataFrom = $(".search #publishDataFrom").val();
    let publishDataTo = $(".search #publishDataTo").val();

    let data={
        //提交参数
        //可以用Request.GetParameter获取
        pageNo,
        pageSize
    }
    if (id.trim()!==""){
        data.id=id;
        // console.log("11111");
    }
    if (type.trim()!==""){
        data.type=type;
    }
    if (bookname.trim()!==""){
        data.bookname=bookname;
    }
    if (ano.trim()!==""){
        data.ano=ano;
    }
    if (publishDataFrom.trim()!==""){
        data.publishDataFrom=publishDataFrom;
    }
    if (publishDataTo.trim()!==""){
        data.publishDataTo=publishDataTo;
    }
    //请求地址
    let url=ctx+"/book/list";//上下文+...
    //发送一个请求
    $.post(url,data,function (resp){
        if (resp.error) {
            layer.alert(resp.error);
            return false;
        }//如果有异常直接返回
        let rows=resp.books;
        let pi=resp.pi;
        $("#tpage").text(pi.pages);
        $("#tnum").text(pi.count);
        $("#pagenow").text(pi.pageNo);
        //回调函数,当请求响应完成，调用
        //同时发生
        $("#tbl>tbody").empty();//放之前清空原数据
        for (let i=0;i<rows.length;i++){
            let book=rows[i];
            let $tr=$("<tr>");
            $tr.append($("<td><input type='checkbox'></td>"));
            $tr.append($("<td>"+book.id+"</td>"));
            $tr.append($("<td>"+book.bookName+"</td>"));
            $tr.append($("<td>"+book.author+"</td>"));
            $tr.append($("<td>"+(book.type || "")+"</td>"));
            $tr.append($("<td>"+(book.briefIntroduction || "")+"</td>"));
            $tr.append($("<td>"+book.pages+"</td>"));
            $tr.append($("<td>"+(book.publishingHouse || "")+"</td>"));
            $tr.append($("<td>"+(book.publishDate || "")+"</td>"));
            $tr.append($("<td>"+book.price+"</td>"));
            $tr.append($("<td>"+(book.borrowing || "")+"</td>"));
            //....
            $("#tbl>tbody").append($tr);
        }
        let navFirst=pi.navFirst;
        let navLast=pi.navLast;
        $(".paginate>ul>li:not(.first):not(.last):not(.prev):not(.next)").remove();
        for (let i=navFirst;i<=navLast;i++){
            let $li = $("<li><a href='#'>" + i + "</a></li>");

            //添加页表
            $(".paginate li.next").before($li);
        }
    },"json");
}
