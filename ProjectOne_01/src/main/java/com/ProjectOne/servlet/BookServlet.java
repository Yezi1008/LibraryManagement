package com.ProjectOne.servlet;

import com.ProjectOne.model.Book;
import com.ProjectOne.model.BookSeachBean;
import com.ProjectOne.model.Borrowed;
import com.ProjectOne.model.User;
import com.ProjectOne.service.BookService;
import com.ProjectOne.service.impl.BookServiceImpl;
import com.ProjectOne.util.BeanFactory;
import com.ProjectOne.util.PaginateInfo;
import com.ProjectOne.util.Servlets;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import org.springframework.util.StringUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@WebServlet("/book/*")
public class BookServlet extends HttpServlet {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private final BookService service= BeanFactory.getBean(BookServiceImpl.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri=req.getRequestURI();
        String ctx=req.getContextPath();
        String action=uri.replace(ctx,"");
        if (action.equals("/book/list")){
            req.getRequestDispatcher("/WEB-INF/jsp/book/booklist.jsp").forward(req,resp);
        }else if(action.equals("/book/add")){
            HttpSession session = req.getSession();
            Book book = (Book) session.getAttribute("book");
            //获取提交的数据
            String error = (String) session.getAttribute("error");

            if (book != null) {
                //不为空判断
                req.setAttribute("book", book);
                session.removeAttribute("book");
                req.setAttribute("error", error);
                session.removeAttribute("error");
            }

            req.getRequestDispatcher("/WEB-INF/jsp/book/addbook.jsp").forward(req,resp);
        }else if (action.equals("/book/edit")){
            String id=req.getParameter("id");
            Book book=service.findById(id);
            if (book==null){
                req.setAttribute("error","信息不存在");
            }else {
                //查到数据,放入请求域
                req.setAttribute("book",book);
            }
            //System.out.println("no1");
            req.getRequestDispatcher("/WEB-INF/jsp/book/editbook.jsp").forward(req,resp);
        }else if (action.equals("/book/export")){
            doPost(req,resp);
        }

    }
    //post接受的ajex的请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.req=req;
        this.resp=resp;
        String uri=req.getRequestURI();
        String ctx=req.getContextPath();
        String action=uri.replace(ctx,"");

        if (action.equals("/book/list")){
            search();
        }else if (action.equals("/book/delete")){
            delete();
        }else if (action.equals("/book/add")){
            //post类型
            save();
        }else if (action.equals("/book/edit")){
            edit();
        }else if (action.equals("/book/checkID")){
            //检查重复ID
            String ID=req.getParameter("id");
            //和ajex上传数据相同
            if (StringUtils.hasText(ID)){
                boolean b=service.hasId(ID);
                Servlets.renderJson(Map.of("exist",b),resp);
            }else {
                Servlets.renderJson(Map.of("exist","ID不能为空"),resp);
            }

        }else if (action.equals("/book/export")){
            export();
        }

    }



    private void search() throws IOException {
        String pageNo=req.getParameter("pageNo");
        String pageSize=req.getParameter("pageSize");
        String id=req.getParameter("id");
        String type=req.getParameter("type");
        String bookname=req.getParameter("bookname");
        String ano=req.getParameter("ano");
        String publishDataFrom=req.getParameter("publishDataFrom");
        String publishDataTo=req.getParameter("publishDataTo");
        String borrowing=req.getParameter("borrowing");
        BookSeachBean bsb =new BookSeachBean();//条件
        if (StringUtils.hasText(id)){
            bsb.setId(id);
        }
        if (StringUtils.hasText(type)){
            bsb.setType(type);
        }
        if (StringUtils.hasText(bookname)){
            bsb.setBookName(bookname);
        }
        if (StringUtils.hasText(ano)){
            bsb.setAuthor(ano);
        }
        System.out.println(borrowing);
        if (StringUtils.hasText(borrowing)){
            bsb.setBorrowing(borrowing);
        }
        if (StringUtils.hasText(publishDataFrom)){
            //解析为时间格式
//            LocalDate ld=LocalDate.parse(publish_data_from, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            bsb.setPublish_data_from(ld);
            LocalDate ld = null;
            try {
                ld = LocalDate.parse(publishDataFrom, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                bsb.setPublishDataFrom(ld);
            } catch (Exception e) {
                e.printStackTrace();
                Servlets.renderJson(Map.of("error", "日期格式不正确"), resp);
                return;
            }
        }
        if (StringUtils.hasText(publishDataTo)){
            LocalDate ld = null;
            try {
                ld = LocalDate.parse(publishDataTo, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                bsb.setPublishDataTo(ld);
            } catch (Exception e) {
                e.printStackTrace();
                Servlets.renderJson(Map.of("error", "日期格式不正确"), resp);
                return;
            }
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

//            BookSeachBean bsb=new BookSeachBean();
        PaginateInfo pi=new PaginateInfo(iPageNo,iPageSize);
        List<Book> books=service.findAll(bsb,pi);
        System.out.println(books.size());
        if(books.size()==0){
            Servlets.renderJson(Map.of("error", "未查询到相关信息，请稍后再试"), resp);
        }
        //ajex响应类型不是text.html,1.设置响应application/json
        //2.json字符串,用双引号
        //2.1 导入依赖后更简便的方法，将数组转换为字符串
        String json= JSON.toJSONString(Map.of("books",books,"pi",pi));

        //3.输出
        Servlets.renderJson(json,resp);
    }

    private void delete(){
        String[] booksid = req.getParameterValues("ids");
        //流里面每一个数据进行映射，调用valueof转为Integer，然后保存到数组中
        //Integer[] ids = Stream.of(booksid).map(Integer::valueOf).toArray(Integer[]::new);
        //调用业务方法完成删除操作
        try {
            int rows = service.deleteByIds(booksid);
            Servlets.renderJson(Map.of("success", true, "msg", "删除成功", "rows", rows), resp);
        } catch (Exception e) {
            e.printStackTrace();
            Servlets.renderJson(Map.of("success", false, "error", "删除失败"), resp);
        }

    }

    private void save() throws IOException {
        String id=req.getParameter("id");
        String type=req.getParameter("typelist");
        String bookname=req.getParameter("bookName");
        String ano=req.getParameter("author");
        String bi=req.getParameter("briefIntroduction");
        String pages=req.getParameter("pages");
        String ph=req.getParameter("publishingHouse");
        String pd=req.getParameter("publishDate");
        String price=req.getParameter("price");
        String borrowing=req.getParameter("borrowing");
        System.out.println("后端的"+borrowing);
        //校验
        Book book=new Book();//封装到一个对象里

        HttpSession session=req.getSession();

        boolean passed = true;//是否通过校验
        List<String> errors = new ArrayList<>();


        //后端验证
        if (!StringUtils.hasText(id)) {
            passed = false;
            errors.add("ID不可为空");
        }
        book.setId(id);

        if (!StringUtils.hasText(bookname)) {
            passed = false;
            errors.add("书名不可为空");
        }
        //System.out.println(bookname);
        book.setBookName(bookname);

        if (!StringUtils.hasText(ano)) {
            passed = false;
            errors.add("作者不可为空");
        }
        book.setAuthor(ano);

        if (!StringUtils.hasText(type)) {
            passed = false;
            errors.add("类别不可为空");
        }
        book.setType(type);

        if (!StringUtils.hasText(borrowing)) {
            passed = false;
            errors.add("借阅不可为空");
        }
        book.setBorrowing(borrowing);

        if (!passed) {
            session.setAttribute("book", book);
            String error = errors.stream().collect(Collectors.joining(","));
            session.setAttribute("error", error);
            resp.sendRedirect(req.getContextPath() + "/book/add");
            return;
        }
        if (pd!=""){
            try {
                book.setPublishDate(LocalDate.parse(pd));
            } catch (Exception e) {
                errors.add("出版日期不正确");
            }
        }

        //为空则不输入，选填
        if (bi!=""){
            book.setBriefIntroduction(bi);
        }
        if (pages!=""){
            try {
                book.setPages(Integer.parseInt(pages));
            } catch (NumberFormatException e) {
                errors.add("页数不正确");
            }
        }
        if (ph!=""){
            book.setPublishingHouse(ph);
        }
        if (price!=""){
            try {
                book.setPrice(Float.parseFloat(price));
            } catch (NumberFormatException e) {
                errors.add("价格不正确");
            }
        }

        boolean b = service.save(book);

        if (b) {
            resp.sendRedirect(req.getContextPath() + "/book/list");
            //成功转入
            //重定向：当前页面处理完成，不再回来
            //存储转发：转发到其他页面进行处理
        } else {
            session.setAttribute("book", book);
            //失败error
            session.setAttribute("erroradd", "保存信息异常");
            resp.sendRedirect(req.getContextPath() + "/book/add");
        }


    }

    private void edit() throws IOException {
        String id=req.getParameter("id");
        String[] ids=req.getParameterValues("id");
        service.deleteByIds(ids);
        String type=req.getParameter("type");
        String bookname=req.getParameter("bookName");
        String ano=req.getParameter("author");
        String bi=req.getParameter("briefIntroduction");
        String pages=req.getParameter("pages");
        String ph=req.getParameter("publishingHouse");
        String pd=req.getParameter("publishDate");
        String price=req.getParameter("price");
        String borrowing=req.getParameter("borrowing");

        System.out.println(borrowing);
        //校验
        Book book=new Book();//封装到一个对象里
        //Book book1=service.findById(id);

        List<String> errors = new ArrayList<>();

        boolean passed = true;//是否通过校验
        HttpSession session = req.getSession();

        //session.setAttribute("bookn", book1);
        //后端验证
        if (!StringUtils.hasText(id)) {
            passed = false;
            errors.add("ID不可为空且不能更改");
        }
        book.setId(id);

        if (!StringUtils.hasText(bookname)) {
            passed = false;
            errors.add("书名不可为空");
        }
        //System.out.println(bookname);
        book.setBookName(bookname);

        if (!StringUtils.hasText(ano)) {
            passed = false;
            errors.add("作者不可为空");
        }
        book.setAuthor(ano);
        if (!StringUtils.hasText(borrowing)) {
            passed = false;
            errors.add("借阅不可为空");
        }
        book.setBorrowing(borrowing);

        try {
            LocalDate ld = LocalDate.parse(pd, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            book.setPublishDate(ld);
        } catch (Exception e) {
            passed = false;
            errors.add("出版日期不正确");
        }

        if (!StringUtils.hasText(type)) {
            passed = false;
            errors.add("类别不可为空");
        }
        book.setType(type);
        System.out.println(passed);
        if (!passed) {
            session.setAttribute("book", book);
            String error = errors.stream().collect(Collectors.joining(","));
            session.setAttribute("error", error);
            resp.sendRedirect(req.getContextPath() + "/book/add");
            return;
        }
        //为空则不输入，选填
        if (bi!=null){
            book.setBriefIntroduction(bi);
        }
        if (pages!=null){
            try {
                book.setPages(Integer.parseInt(pages));
                //
            } catch (NumberFormatException e) {
                errors.add("页数不正确");
            }
        }
        if (ph!=null){
            book.setPublishingHouse(ph);
        }
        if (price!=null){
            try {
                book.setPrice(Float.parseFloat(price));
            } catch (NumberFormatException e) {
                errors.add("价格不正确");
            }
        }

        boolean b = service.save(book);
        System.out.println("update");
//        if (b) {
//            resp.sendRedirect(req.getContextPath() + "/book/list");
//            //成功转入
//            //重定向：当前页面处理完成，不再回来
//            //存储转发：转发到其他页面进行处理
//        } else {
//            session.setAttribute("book", book);
//            //失败error
//            session.setAttribute("error", "保存信息异常");
//            resp.sendRedirect(req.getContextPath() + "/book/add");
//        }
//
//        if (b) {
//            Servlets.renderJson(Map.of("success",true),resp);
//        } else {
//            Servlets.renderJson(Map.of("success",false,"error","保存信息失败"),resp);
//        }
        if (b) {
            resp.sendRedirect(req.getContextPath() + "/book/list");
            //成功转入
            //重定向：当前页面处理完成，不再回来
            //存储转发：转发到其他页面进行处理
        } else {
            session.setAttribute("book", book);
            //失败error
            session.setAttribute("error", "保存信息异常");
            resp.sendRedirect(req.getContextPath() + "/book/add");
        }
    }

    private void export() {
        String[] ids = req.getParameterValues("ids");

        List<Book> books = new ArrayList<>();//要导出的数据
        for (int i=0;i<ids.length;i++){
            books.add(service.findById(ids[i]));
        }
            //导出
            //区分不同种类的数据，并根据不同的MIME调用浏览器内不同的程序嵌入模块来处理相应的数据。
            //让浏览器知道要保存为什么文件,真正的文件还是在流里面的数据，你设定一个下载类型并不会去改变流里的内容
            resp.setContentType("application/vnd.ms-excel");
            resp.setCharacterEncoding("utf-8");
            try {
                LocalDateTime now = LocalDateTime.now();
                //Format 格式化日期时间
                String format = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                //文件名
                //replaceAll() 方法使用给定的参数 replacement 替换字符串所有匹配给定的正则表达式的子字符串
                //所以这条语句是把字符串中所有的'+'替换成'%20'，在URL中%20代表空格
                String fileName = URLEncoder.encode("书籍列表导出" + format, "UTF-8").replaceAll("\\+", "%20");
                resp.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

                //导出
                //只传pathName（文件绝对路径，带后缀名）可以生成Excel文件
                //传pathName和Class类对象后，不仅可以生成Excel文件，还可以以类对象为模板生成与类的属性相对应的Excel表格头（列名）。
                EasyExcel.write(resp.getOutputStream(), Book.class).sheet("模板").doWrite(books);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
