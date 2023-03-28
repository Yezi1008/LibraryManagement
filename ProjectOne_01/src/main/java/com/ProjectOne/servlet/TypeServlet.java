package com.ProjectOne.servlet;

import com.ProjectOne.model.Book;
import com.ProjectOne.model.TypeModel;
import com.ProjectOne.service.TypeService;
import com.ProjectOne.service.impl.TypeServiceImpl;
import com.ProjectOne.util.BeanFactory;
import com.ProjectOne.util.Servlets;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/type/*")
public class TypeServlet extends HttpServlet {

    private final TypeService service= BeanFactory.getBean(TypeServiceImpl.class);

    private HttpServletRequest req;
    private HttpServletResponse resp;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri=req.getRequestURI();
        String ctx=req.getContextPath();
        String action=uri.replace(ctx,"");
        if (action.equals("/type/list")){
            req.getRequestDispatcher("/WEB-INF/jsp/book/typelist.jsp").forward(req,resp);
        }else if (action.equals("/type/add")){
            req.getRequestDispatcher("/WEB-INF/jsp/book/addtype.jsp").forward(req,resp);
        }else if (action.equals("/type/edit")) {
            req.getRequestDispatcher("/WEB-INF/jsp/book/edittype.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req = req;
        this.resp = resp;
        String uri=req.getRequestURI();
        String ctx=req.getContextPath();
        String action=uri.replace(ctx,"");
        if (action.equals("/type/list")){
            combolist();
        }else if (action.equals("/type/delete")){
            delete();
        }else if (action.equals("/type/add")){
            save();
        }else if (action.equals("/type/edit")){
            edit();
        }
    }

    private void combolist(){
        //获取
        List<TypeModel> typeModels=service.findAll();
        System.out.println(typeModels.get(0).getTypename());
        //响应给前端
        Servlets.renderJson(Map.of("success",true,"data",typeModels),resp);

    }
    private void delete(){
        String[] types = req.getParameterValues("types");
        //流里面每一个数据进行映射，调用valueof转为Integer，然后保存到数组中
        //Integer[] ids = Stream.of(booksid).map(Integer::valueOf).toArray(Integer[]::new);
        //调用业务方法完成删除操作
        try {
            int rows = service.deleteBytypes(types);
            Servlets.renderJson(Map.of("success", true, "msg", "删除成功", "rows", rows), resp);
        } catch (Exception e) {
            e.printStackTrace();
            Servlets.renderJson(Map.of("success", false, "error", "删除失败"), resp);
        }
    }

    private void save() throws IOException {
       String typename=req.getParameter("typename");
        TypeModel typeModel=new TypeModel();//封装到一个对象里
        boolean passed = true;//是否通过校验
        List<String> errors = new ArrayList<>();
        //后端验证
        if (!StringUtils.hasText(typename)) {
            passed = false;
            errors.add("类型不可为空");
        }
        typeModel.setTypename(typename);
        typeModel.setBorrowed(0);
        typeModel.setRemaining(0);
        typeModel.setAllBook(0);
        boolean b=service.save(typeModel);
        HttpSession session = req.getSession();

        if (!passed) {
            session.setAttribute("typeModel", typeModel);
            String error = errors.stream().collect(Collectors.joining(","));
            session.setAttribute("error", error);
            resp.sendRedirect(req.getContextPath() + "/type/add");
            return;
        }

        if (b) {
            Servlets.renderJson(Map.of("addsuccess",true),resp);
            //成功转入
            //重定向：当前页面处理完成，不再回来
            //存储转发：转发到其他页面进行处理
        } else {
            session.setAttribute("typeModel", typeModel);
            //失败error
            session.setAttribute("error", "保存类型异常");
            resp.sendRedirect(req.getContextPath() + "/type/add");
        }

    }

    private void edit() throws IOException {
       // String[] typenames=req.getParameterValues("typename");
        String typename=req.getParameter("typename");

        TypeModel typeModel=new TypeModel();//封装到一个对象里
        boolean passed = true;//是否通过校验
        List<String> errors = new ArrayList<>();
        //后端验证
        if (!StringUtils.hasText(typename)) {
            passed = false;
            errors.add("类型不可为空");
        }
        boolean b=service.change(typename);
        HttpSession session=req.getSession();
        if (!passed) {
           // session.setAttribute("typeModel", typeModel);
            Servlets.renderJson(Map.of("typeModel",typeModel),resp);
            String error = errors.stream().collect(Collectors.joining(","));
            session.setAttribute("error", error);
            resp.sendRedirect(req.getContextPath() + "/type/change");
            return;
        }

        if (b) {
            Servlets.renderJson(Map.of("success",true),resp);

            // resp.sendRedirect(req.getContextPath() + "/type/list");
            //成功转入
            //重定向：当前页面处理完成，不再回来
            //存储转发：转发到其他页面进行处理
        } else {
            session.setAttribute("typeModel", typeModel);
            //失败error
            session.setAttribute("error", "保存类型异常");
            resp.sendRedirect(req.getContextPath() + "/type/add");
        }


    }
}
