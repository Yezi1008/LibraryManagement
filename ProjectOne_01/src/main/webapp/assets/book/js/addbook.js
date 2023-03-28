$(function () {
    if (erroradd) {
        layer.alert(erroradd);
    }
    //提示异常信息
    laydate.render({
        elem: "#pd"
    });

    fetchType();

    $("#submitbtn").click(function () {
        // console.log($(":input[name=book_name]").val());
        //检验
        let id = $(":input[name=id]").val();
        if (id.trim() === "") {
            layer.msg("ID不可为空");
            return false;
        }

        let bookname = $(":input[name=bookName]").val();
        if (bookname.trim() === "") {
            layer.msg("书名不可为空");
            return false;
        }

        let author = $(":input[name=author]").val();
        if (author.trim() === "") {
            layer.msg("作者不可为空");
            return false;
        }

        $("#bookform").submit();//刷新页面方式


    });

    let timer;//定时器
    //ID同步输入触发
    $(":input[name=id]").blur(function () {

    }).keyup(function () {
        let id = $(this).val();
        //节流
        if (timer) {
            clearTimeout(timer);//清理定时器
        }
        timer = setTimeout(function () {
            checkID(id);
        }, 500);
    });
});

    function checkID(id) {
        let url = ctx + "/book/checkID";
        //只能用ajex
        $.ajax(url, {
            method: "post",
            data: {
                id
            },
            dataType: "json",
            success: function (resp) {
                // callback(resp.exist);
                layer.msg("已存在相同ID");
            }
        });
    }

    function fetchType() {
        const url = ctx + "/type/list";
        $.post(url, {}, function (resp) {
            //typeservlet响应到这里
            if (resp.success) {
                let data = resp.data;
                // for (let i=0;i<data.length;i++){
                //     $("#typelist").append($("<option value='" + data.rows.item(i).name + "'>" + data.rows.item(i).name + "</option>"));
                // }
                $("#classId").empty();
                data.forEach(function (item) {
                    console.log(item.typename);
                    $("#typelist").append($("<option value='" + item.typename + "'>" + item.typename + "</option>"))
                });

            }
        }, "json");

}