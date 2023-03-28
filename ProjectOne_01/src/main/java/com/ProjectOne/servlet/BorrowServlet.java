package com.ProjectOne.servlet;

import com.ProjectOne.model.Book;
import com.ProjectOne.model.Borrowed;
import com.ProjectOne.service.BookService;
import com.ProjectOne.service.BorrowService;
import com.ProjectOne.service.impl.BookServiceImpl;
import com.ProjectOne.service.impl.BorrowServiceImpl;
import com.ProjectOne.util.BeanFactory;
import com.ProjectOne.util.PaginateInfo;
import com.ProjectOne.util.Servlets;
import com.alibaba.fastjson2.JSON;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/borrow/*")
public class BorrowServlet extends HttpServlet {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private final BorrowService service= BeanFactory.getBean(BorrowServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri=req.getRequestURI();
        String ctx=req.getContextPath();
        String action=uri.replace(ctx,"");

        if (action.equals("/borrow/list")){
            req.getRequestDispatcher("/WEB-INF/jsp/book/borrowlist.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req=req;
        this.resp=resp;
        String uri=req.getRequestURI();
        String ctx=req.getContextPath();
        String action=uri.replace(ctx,"");

        if (action.equals("/borrow/list")){
            searchborrow();
        }else if (action.equals("/borrow/delete")){
            delete();
        }
    }

    private void searchborrow(){
        String pageNo=req.getParameter("pageNo");
        String pageSize=req.getParameter("pageSize");

        String name=req.getParameter("name");
        String bookname=req.getParameter("bookname");

        Borrowed borrowed =new Borrowed();//条件

        if (StringUtils.hasText(name)){
            borrowed.setName(name);
        }
        if (StringUtils.hasText(bookname)){
            borrowed.setBookname(bookname);
        }


        int iPageNo=1;
        if (StringUtils.hasText(pageNo)) {//判断是否空
            System.out.println(pageNo);
            iPageNo = Integer.parseInt(pageNo);//String转int
        }
        int iPageSize=10;
        if (StringUtils.hasText(pageSize)) {//判断是否空
            System.out.println(pageSize);
            iPageSize = Integer.parseInt(pageSize);//String转int
        }

        PaginateInfo pi=new PaginateInfo(iPageNo,iPageSize);
        List<Borrowed> borrowlist=service.findAll(borrowed,pi);
        //ajex响应类型不是text.html,1.设置响应application/json
        //2.json字符串,用双引号
        //2.1 导入依赖后更简便的方法，将数组转换为字符串
        String json= JSON.toJSONString(Map.of("borrowlist",borrowlist,"pi",pi));

        //3.输出
        Servlets.renderJson(json,resp);
    }

    private void delete(){
        String[] borrowsid = req.getParameterValues("ids");
        //流里面每一个数据进行映射，调用valueof转为Integer，然后保存到数组中
        //Integer[] ids = Stream.of(booksid).map(Integer::valueOf).toArray(Integer[]::new);
        //调用业务方法完成删除操作
        try {
            int rows = service.deleteByIds(borrowsid);
            Servlets.renderJson(Map.of("success", true, "msg", "删除成功", "rows", rows), resp);
        } catch (Exception e) {
            e.printStackTrace();
            Servlets.renderJson(Map.of("success", false, "error", "删除失败"), resp);
        }
    }


}
