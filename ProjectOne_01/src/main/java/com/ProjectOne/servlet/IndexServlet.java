package com.ProjectOne.servlet;

import com.ProjectOne.global.Global;
import com.ProjectOne.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/index")
//在WEB-INF中不能直接访问index
public class IndexServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user=(User)req.getSession().getAttribute(Global.USER_LOGIN_KEY);
        if (user!=null){
            req.setAttribute("user",user);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req,resp);
        //作用:转发到首页
    }
}
