$(function (){
if (error){
    layer.alert(error);
}
    $("#submitbtn").click(function () {
        //关闭页面自动刷新
        //todo
        //$("#username").focus();
        //检验
        let username = $(":input[name=username]").val();
        let result2=validateUsername(username);
        if (result2!==true){
            layer.msg(result2);
            return false;
        }

        let oldpassword = $(":input[name=oldpassword]").val();
        let result=validatePassword(oldpassword);
        if (result!==true){
            layer.msg(result);
            return false;
        }
        let password = $(":input[name=password]").val();
        let result1=validatePassword(password);
        if (result1!==true){
            layer.msg(result1);
            return false;
        }

        let repassword = $(":input[name=repassword]").val();
        if (password!==repassword){
            layer.msg("两次密码不同，请重新输入");
            return false;
        }
        let name = $(":input[name=name]").val();
        if (name.trim() === "") {
            layer.msg("姓名不可为空");
            return false;
        }
        let sex = $(":input[name=sex]").val();
        let age = $(":input[name=age]").val();

        const url = ctx + "/user/change";
        $.post(url, {
            username,
            oldpassword,
            password,
            repassword,
            name,
            sex,
            age
        }, function (resp) {
            let error=this.error;

            if (error) {
                layer.msg(error);
            } else {
                //更新成功直接关闭弹窗
                layer.msg("修改成功");
                // sleep(5000);
                setTimeout(function (){
                    var index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                },2000);

            }
        }, "json");
    });


});
function validateUsername(username){
    let regex=/^\w+@\w+\.\w+$/;
    //"\w"是至少一个字符
    //邮件格式
    if (!username.match(regex)){
        return "用户名需符合邮箱格式";
    }else {
        return true;
    }
}

function validatePassword(password) {

    let regex=/^\w{6,12}$/;
    //6-12位
    if (!password.match(regex)){
        return "密码必须介于6~12位之间";
    }

    regex = /[a-z]+/;
    if (!password.match(regex)) {
        return "密码必须包含至少一个小写字母";
    }

    regex = /[A-Z]+/;
    if (!password.match(regex)) {
        return "密码必须包含至少一个大写字母";
    }

    regex = /[0-9]+/;
    if (!password.match(regex)) {
        return "密码必须包含至少一个数字";
    }

    return true;
}
function sleep(milliSeconds) {
    var startTime = new Date().getTime();
    while (new Date().getTime() < startTime + milliSeconds) {
        console.log(new Date().getTime());
    }//暂停一段时间 10000=1S。
}