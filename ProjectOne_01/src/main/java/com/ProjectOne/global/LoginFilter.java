package com.ProjectOne.global;

import com.ProjectOne.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
//"/"过滤一切包括静态资源文件
//“/*”过滤一切请求不包括静态资源文件
public class LoginFilter implements Filter {
    private static final List<String> whiteList = List.of("/user/login", "/user/logout","/user/captcha");
    //登录和注销和验证码
    //白名单过滤掉，放行,防止被拦截
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        HttpServletResponse resp=(HttpServletResponse)servletResponse;
        HttpSession session =req.getSession();
        //和网站建立链接则一种共用一个链接
        User user=(User) session.getAttribute(Global.USER_LOGIN_KEY);
        //登录成功在session里放user
        //如果session里有user直接放行
        String uri=req.getRequestURI();
        String ctx=req.getContextPath();//上下文
        String action=uri.replace(ctx,"");
        boolean match = false;
        if (uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".jpg")) {
            match = true;
        } else {
            for (String url : whiteList) {
                if (action.startsWith(url)) {
                    match = true;
                    break;
                }
            }
        }
        if (match) {
            filterChain.doFilter(req, resp);
        } else {
            if (user==null){
                //没有登陆过或者会话失效
                //重定向
                resp.sendRedirect(req.getContextPath()+"/user/login");

            }else {
                filterChain.doFilter(req,resp);
                //放行
            }
        }


    }
}
