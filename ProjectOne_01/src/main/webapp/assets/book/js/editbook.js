$(function (){
    if (error) {
        layer.alert(error);
    }
    // 提示异常信息
    laydate.render({
        elem: "#pd"
    });

    $("#editbtn").click(function (){

        //检验
        let id = $(":input[name=id]").val();
        if (id.trim() === "") {
            layer.msg("ID不可为空且不可更改");
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

        console.log("get"+id);
         $("#bookform").submit();//刷新页面方式
        //console.log(ctx);
        //const  url= ctx + "/book/edit";
        //ajex
        // $.post(url,{
        //     // 绿色是已经获取的值
        //     //需要再前面获取各属性的值
        //     id,
        //     bookname,
        //     author,
        //     type,
        //     pages,
        //     publishingHouse,
        //     publishDate,
        //     price,
        //     //briefIntroduction
        // },function (resp){
        //     //成功之后的回调函数
        //     if (resp.error){
        //         //edit函数结束跳转这里
        //         //有异常则输出error
        //         layer.alert(error);
        //     }else {
        //         location.href=ctx+"book/list";
        //     }
        // },"json");
        //希望的返回类型为json

    });
});