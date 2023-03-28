$(function (){
    $("#username").focus();
    //自动赋值用户名和密码，实现自动登录
    let rememberState = localStorage.getItem("rememberMe");
    if (rememberState === "true") {
        let username = localStorage.getItem("username");
        if (username) {
            $("#username").val(username);
        }
        let password = localStorage.getItem("password");
        if (password) {
            $("#password").val(password);
        }
        $("#remember-me").prop("checked", true);
    }

    $("#username").keydown(function (e) {
        if (e.keyCode === 13) {
            $("#password").focus();
        }
    }).blur(function (){
        //blur失去焦点
        let username=$("#username").val();
        let result=validateUsername(username);
        if (result!==true){
            layer.msg(result);
        }
    });
    $("#password").keydown(function (e) {
        if (e.keyCode === 13) {
            $("#captcha").focus();
        }
    }).blur(function (){
        //焦点
        let password=$("#password").val();
        let result=validatePassword(password);
        if (result!==true){
            layer.msg(result);
        }
    });

    $("#saveForm").click(function (){
        //校验
        let username = $("#username").val();
        let password = $("#password").val();
        console.log(username);
        console.log(password);
        let result=validateUsername(username);
        if (result!==true){
            layer.msg(result);
            return false;
        }
        result=validatePassword(password);
        if (result!==true){
            layer.msg(result);
            return false;
        }

        rememberAndLogin();

        // $("#loginform").submit();
        //登录一定要刷新

        });
    $("#captcha").click(function () {
        $(this).attr("src", ctx + "/user/captcha?k=" + new Date().getTime());
        //浏览器对相同的请求缓存,加上参数避免缓存

        //回车提交
        $("#captcha").keydown(function (e) {
            if (e.keyCode === 13) {
                rememberAndLogin();
            }
        });
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

function rememberAndLogin() {
    let state = $("#remember-me").prop("checked");
    if (state) {
        let username = $("#username").val();
        let password = $("#password").val();
        // 只读的localStorage 属性允许你访问一个Document 源（origin）的对象 Storage；存储的数据将保存在浏览器会话中。
        // 存储在 localStorage 的数据可以长期保留
        //将用户名和密码保存到本地缓存
        localStorage.setItem("rememberMe", "true");//第2个参数是字符串类型
        localStorage.setItem("username", username);
        localStorage.setItem("password", password);
    } else {
        localStorage.removeItem("rememberMe");
        localStorage.removeItem("username");
        localStorage.removeItem("password");
    }

    $("#loginform").submit();
}