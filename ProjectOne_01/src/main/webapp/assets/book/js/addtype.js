$(function () {
    if (error) {
        layer.alert(error);
    }
    $("#submitbtn").click(function () {
        //检验
        let typename = $(":input[name=typename]").val();
        if (typename.trim() === "") {
            layer.msg("类型不可为空");
            return false;
        }
       // $("#typeform").submit();//刷新页面方式
        //关闭页面自动刷新
        //todo
        const url = ctx + "/type/add";
        $.post(url, {
            typename
        }, function (resp) {
            if (resp.error) {
                layer.alert(resp.error);
            } else {
                //更新成功直接关闭弹窗
                parent.closeEditWindow();
            }
        }, "json");
    });

});

