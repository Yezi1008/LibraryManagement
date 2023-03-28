package com.ProjectOne.servlet;

import com.ProjectOne.global.Global;
import com.ProjectOne.model.Book;
import com.ProjectOne.model.User;
import com.ProjectOne.service.UserService;
import com.ProjectOne.service.impl.UserServiceImpl;
import com.ProjectOne.util.BeanFactory;
import com.ProjectOne.util.Md5Utils;
import com.ProjectOne.util.Servlets;
import com.alibaba.fastjson2.JSON;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/user/*")
public class LoginServlet extends HttpServlet {
    //登录、注销操作
    private final UserService service = BeanFactory.getBean(UserServiceImpl.class);
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();//上下文路径
        String action = uri.replace(ctx, "");
        if (action.equals("/user/login")) {
            String error = (String) req.getSession().getAttribute("error");
            if (error != null) {
                req.setAttribute("error", error);
                req.getSession().removeAttribute("error");
            }
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
        }else if (action.equals("/user/logout")){
            //移除用户信息
            req.getSession().removeAttribute(Global.USER_LOGIN_KEY);
            resp.sendRedirect(req.getContextPath()+"/index");
            //会被过滤器拦截跳转到登录
            //不允许多次重定向
        }else if (action.equals("/user/captcha")){
            //验证码
            CaptchaUtil.out(100,60,4,req,resp);
        }else if (action.equals("/user/change")){
            //数据回显到输入框上
            HttpSession session=req.getSession();
            User user1=(User) session.getAttribute(Global.USER_LOGIN_KEY);
//            String username=req.getParameter("username");
            User user=service.findByUsername(user1.getUsername());
            req.getSession().setAttribute(Global.USER_LOGIN_KEY,user);
            if (user==null){
                req.setAttribute("error","信息不存在");
            }else {
                //查到数据,放入请求域
                req.setAttribute("user",user);
            }
            req.getRequestDispatcher("/WEB-INF/jsp/book/changeuser.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        String uri = req.getRequestURI();
        String ctx = req.getContextPath();//上下文路径
        String action = uri.replace(ctx, "");
        if (action.equals("/user/login")){
            login();
        }else if (action.equals("/user/change")) {
            updateuser();
        }

    }

     private void updateuser() throws IOException {
        String username=req.getParameter("username");
        String oldpassword=req.getParameter("oldpassword");
        String password=req.getParameter("password");
        String repassword=req.getParameter("repassword");
        String name=req.getParameter("name");
        String sex=req.getParameter("sex");
        String age=req.getParameter("age");
        System.out.println(age);
        User user=new User();//封装到一个对象里
        List<String> errors = new ArrayList<>();

        boolean a=passwordmatch(username,password);

         boolean passed = true;//是否通过校验
        HttpSession session = req.getSession();
        //后端验证
        if (!StringUtils.hasText(username)) {
            passed = false;
            errors.add("用户名不可为空");
        }
        user.setUsername(username);
         if (!StringUtils.hasText(oldpassword)) {
             passed = false;
             errors.add("原密码不可为空");
         }

         if (!password.equals(repassword)){
             passed = false;
             errors.add("两次密码不同");
         }

         User user1=(User) req.getSession().getAttribute(Global.USER_LOGIN_KEY);

         String encrypt= Md5Utils.encrypt(oldpassword+"{"+username+"}");
         String encrypt2= Md5Utils.encrypt(password+"{"+username+"}");
         if (encrypt.equals(user1.getPassword())){
             if (!StringUtils.hasText(password)) {
                 passed = false;
                 errors.add("新密码不可为空");
             }
             user.setPassword(encrypt2);
         }else {
             passed = false;
             errors.add("原密码不正确");
         }

         if (encrypt2.equals(encrypt)) {
             passed = false;
             errors.add("新旧密码不能相同");
         }

        if (!StringUtils.hasText(name)) {
            passed = false;
            errors.add("姓名不可为空");
        }
        user.setName(name);

        if (age!=null){
            user.setAge(Integer.valueOf(age));
        }
        if (sex!=null){
            user.setSex(sex);
        }
        //没成功
        if (!passed) {
            //session.setAttribute("user", user);
            Servlets.renderJson(Map.of("user",user),resp);
            String error = errors.stream().collect(Collectors.joining(","));
//            session.setAttribute("error", error);
            Servlets.renderJson(Map.of("error",error),resp);
            resp.sendRedirect(req.getContextPath() + "/user/change");
            return;
        }
        //成功
        boolean b = service.updateuse(user);
        System.out.println("updateuser");
         System.out.println("success");
        if (b) {
            Servlets.renderJson(Map.of("success",true),resp);
            //成功转入
            //重定向：当前页面处理完成，不再回来
            //存储转发：转发到其他页面进行处理
        } else {
//            session.setAttribute("success","啦啦啦啦啦啦啦");
            Servlets.renderJson(Map.of("user",user),resp);
            //失败error
            Servlets.renderJson(Map.of("error","保存信息异常"),resp);
            resp.sendRedirect(req.getContextPath() + "/book/list");

        }

    }


    private void login() throws IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        String captcha=req.getParameter("captcha");
        System.out.println(password);
        User user=service.findByUsername(username);

        //检验正确性
        boolean b=CaptchaUtil.ver(captcha,req);
        if (!b){
            req.getSession().setAttribute("error","验证码不正确");
            resp.sendRedirect(req.getContextPath()+"/user/login");
            return;
        }
        if (user==null){
            req.getSession().setAttribute("error","用户名不存在");
            resp.sendRedirect(req.getContextPath()+"/user/login");
            return;
        }
        boolean passed=service.checkLogin(user,password);
        if (passed){
            //成功登录
            req.getSession().setAttribute(Global.USER_LOGIN_KEY,user);
            //req.getSession().setAttribute("user",username);
            resp.sendRedirect(req.getContextPath()+"/index");
        }else {
            req.getSession().setAttribute("error","密码不正确");
            resp.sendRedirect(req.getContextPath()+"/user/login");
        }
    }


private boolean passwordmatch(String username,String password) throws IOException {
    String uri = req.getRequestURI();
    String ctx = req.getContextPath();//上下文路径
    String action = uri.replace(ctx, "");
    String regex1 = "^\\w{6,12}$";
    String regex2 = "\\w+[a-z]+\\w+";
    String regex3 = "\\w+[A-Z]+\\w+";
    String regex4 = "\\w+[0-9]+\\w+";

    if(!username.matches("^\\w+@\\w+\\.\\w+$")){
        req.getSession().setAttribute("error", "用户名必须为邮箱格式");
        return false;
    }else {
        return true;
    }
}


}
