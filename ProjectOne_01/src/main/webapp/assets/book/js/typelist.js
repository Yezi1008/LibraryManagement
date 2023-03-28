$(function (){
    search(1,10);
    let pageSize=10;
    //修改
    $("#edit").click(function (){
        //可以尝试批量修改
        let $ch=$("#tbl>tbody>tr>td:first-child>:checked");
        if ($ch.length===0){
            layer.msg("请选中一种类型");
        }else if ($ch.length>1){
            layer.msg("一次只能选中一种类型！");
        }else {
            let type = $ch.closest("tr").children().eq(1).text();
            // location.href=ctx+"/type/edit?id="+id;
            editWindowHandler =  layer.open({
                //弹窗层类型：0（信息框，默认）1（页面层）2（iframe层，也就是解析content）3（加载层）4（tips层）
                type: 2,
                title: '修改类型',
                shadeClose: true,
                shade: false,//阴影
                area: ['600px', '500px'],//尺寸
                content: ctx + '/type/edit'//地址
            });
        }
    });

    //添加
    $("#add").click(function (){
        editWindowHandler =  layer.open({
            //弹窗层类型：0（信息框，默认）1（页面层）2（iframe层，也就是解析content）3（加载层）4（tips层）
            type: 2,
            title: '添加类型',
            shadeClose: true,
            shade: false,//阴影
            area: ['600px', '500px'],//尺寸
            content: ctx + '/type/add'//地址
        });
    });

    //删除
    $("#delete").click(function () {
        //已经选中的元素
        let $chs = $("#tbl>tbody>tr>td:first-child>:checked");
        if ($chs.length === 0) {
            layer.msg("请选中您要删除的类型");
        } else {
            let types = [];
            $chs.each(function (idx, item) {
                let id = $(this).closest("tr").children().eq(1).text();
                types.push(id);
            });

            if (types.length > 0) {
                layer.confirm("是否确认删除", function () {
                    deleteByIds();
                });
            }
            function deleteByIds() {
                let url = ctx + "/type/delete";
                $.ajax(url, {
                    method: "post",
                    data: {
                        types
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
    let data={
        //提交参数
        //可以用Request.GetParameter获取
        pageNo,
        pageSize
    }
    //请求地址
    let url=ctx+"/type/list";//上下文+...
    //发送一个请求
    $.post(url,data,function (resp){

        let rows=resp.data;
        let pi=resp.pi;
        //回调函数,当请求响应完成，调用
        //同时发生
        $("#tbl>tbody").empty();//放之前清空原数据
        for (let i=0;i<rows.length;i++){
            let type=rows[i];
            let $tr=$("<tr>");
            $tr.append($("<td><input type='checkbox'></td>"));
            $tr.append($("<td>"+type.typename+"</td>"));
            $tr.append($("<td>"+type.allBook+"</td>"));
            $tr.append($("<td>"+type.remaining+"</td>"));
            $tr.append($("<td>"+type.borrowed+"</td>"));
            //....
            $("#tbl>tbody").append($tr);
        }

    },"json");
}

var editWindowHandler;
function closeEditWindow() {
    layer.close(editWindowHandler);
    console.log(66666);
}