$(function (){
    if (error){
        layer.alert(error);
    }
    $(".nav").on("click","li>a",function (e){
        e.preventDefault();
        // 阻止默认行为
        let val=$(this).attr("href");
        $(".main>iframe").attr("src",val);
    });

    $("#logout").click(function (e){
        e.preventDefault();
        //发出请求
        location.href=ctx+"/user/logout";

    });

    $("#edit").click(function (){
        layer.open({
            type: 2,
            title: '修改个人信息',
            shadeClose: true,
            shade: false,//阴影
            area: ['700px', '650px'],//尺寸
            content: ctx + '/user/change'//地址
        });
    });

});
