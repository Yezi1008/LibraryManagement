package com.ProjectOne.dao.impl;

import com.ProjectOne.dao.BookDao;
import com.ProjectOne.global.Global;
import com.ProjectOne.model.Book;
import com.ProjectOne.model.BookSeachBean;
import com.ProjectOne.model.User;
import com.ProjectOne.util.PaginateInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BookDaoImpl implements BookDao {
    private final JdbcTemplate template=new JdbcTemplate(Global.getDataSource());

    //jdbcTemplate可以做数据库列名到Java驼峰式的转换，例如：s_name 可以自动转到 sName。
    @Override
    public List<Book> findAll(BookSeachBean bsb, PaginateInfo pi) {
        String count="select count(0) from book_info";
        String sql="select id,book_name,author,`type`,brief_introduction,pages,publishing_house,publish_date,price,borrowing from book_info";
        List<Object> args=new ArrayList<>();

        StringBuilder whereSql = new StringBuilder();
        if (bsb != null) {
            whereSql.append(" where 1=1");

            if (StringUtils.hasText(bsb.getId())){
                whereSql.append(" and id = ?");
                System.out.println(bsb.getId());
                args.add(bsb.getId());
            }
            if (StringUtils.hasText(bsb.getBookName())) {
                whereSql.append(" and book_name like ?");
                args.add("%" + bsb.getBookName() + "%");
            }
            if (StringUtils.hasText(bsb.getType())) {
                whereSql.append(" and `type` = ?");
                args.add(bsb.getType());
            }
            if (StringUtils.hasText(bsb.getAuthor())) {
                whereSql.append(" and author like ?");
                args.add("%" + bsb.getAuthor() + "%");
            }
            if (bsb.getPublishDataFrom() != null) {
                whereSql.append(" and publish_date >= ?");
                args.add(bsb.getPublishDataFrom());
            }
            if (bsb.getPublishDataTo() != null) {
                whereSql.append(" and publish_date < ?");
                args.add(bsb.getPublishDataTo());
            }
        }
//        System.out.println("1111"+whereSql);
        count += whereSql.toString();
        //con返回long类型
        Long con=template.queryForObject(count,Long.class,args.toArray());
        pi.setCount(con);

        whereSql.append(" limit ?,?");
//        System.out.println("22222"+whereSql);
        args.add(pi.getOffset());
        args.add(pi.getLimit());

        sql+=whereSql.toString();
        List<Book> books=template.query(sql,new BeanPropertyRowMapper(Book.class),args.toArray());
        //执行给定的 SQL 并返回所需类型的对象。
        //设pi中的count为con
        return books;
    }


    @Override
    public boolean deleteById(String id) {
        String presql="UPDATE typelist,book_info SET all_book=(all_book-1) WHERE book_info.type=typelist.typename and typename IN (SELECT type FROM book_info WHERE id=?)";
        String nextsql="UPDATE typelist,book_info SET  borrowed=(borrowed-1) WHERE borrowing='是' AND book_info.type = typelist.typename AND book_info.id=?";
        String lastsql="UPDATE typelist,book_info SET  remaining=(remaining-1) WHERE borrowing='否' AND book_info.type = typelist.typename AND book_info.id=?";
        String sql = "delete from book_info where id = ?";
        int prerows=template.update(presql,id);
        int nextrows=template.update(nextsql,id);
        int lastrows=template.update(lastsql,id);
        int rows = template.update(sql, id);
        return (rows > 0) && (prerows>0) && (nextrows>0 || lastrows>0);
    }

    @Override
    public boolean save(Book book) {
        String sql = "insert into book_info " +
                "(id,book_name,author,`type`,brief_introduction,pages,publishing_house,publish_date,price,borrowing) " +
                "values (?,?,?,?,?,?,?,?,?,?)";
        String sql1="UPDATE typelist,book_info SET  borrowed=(borrowed+1) " +
                "WHERE borrowing='是' AND book_info.type = typelist.typename AND book_info.id=? AND type=?";
        String sql2="UPDATE typelist,book_info SET  remaining=(remaining+1) " +
                "WHERE borrowing='否' AND book_info.type = typelist.typename AND book_info.id=? AND type=?";
        String sql3="UPDATE typelist,book_info SET  all_book=(all_book+1) " +
                "WHERE book_info.type = typelist.typename AND type=?";

        int rows = template.update(sql, book.getId(),book.getBookName(),book.getAuthor(),book.getType(),
                book.getBriefIntroduction(),book.getPages(),book.getPublishingHouse(),
                book.getPublishDate(),book.getPrice(),book.getBorrowing());
        int row1=template.update(sql1,book.getId(),book.getType());
        int row2=template.update(sql2,book.getId(),book.getType());
        int row3=template.update(sql3,book.getType());
        return (rows>0) && ((row1>0)||(row2>0)) && (row3>0);
    }

    @Override
    public boolean update(Book book) {
//        String sql="update book_info set book_name=?,author=?,`type`=?,pages=?,publishing_house=?,publish_date=?,price=?,borrowing=? where id=?";
//
//        Book book1=(Book)req..getAttribute("bookn");
//        String type=book1.getType();
//        String borrow=book1.getBorrowing();
//        String sql1="update typelist set all_book=(all_book-1) where typename=?";
//        String sql2="update typelist set borrowed=(borrowed-1) where '是'=?";
//        String sql3="update typelist set remaining=(remaining-1) where '否'=?";
//        int row1=template.update(sql1,type);
//        int row2=template.update(sql2,borrow);
//        int row3=template.update(sql3,borrow);
//        deleteById(book.getId());
//        String sql4="update typelist set all_book=(all_book+1) where typename=?";
//        String sql5="update typelist set borrowed=(borrowed+1) where '是'=?";
//        String sql6="update typelist set remaining=(remaining+1) where '否'=?";
//        int row4=template.update(sql4,book.getType());
//        int row5=template.update(sql5,book.getBorrowing());
//        int row6=template.update(sql6,book.getBorrowing());
//        save(book);
//        System.out.println(sql);
//        int rows=template.update(sql,book.getBookName(),book.getAuthor(),book.getType(),
//                book.getPages(),book.getPublishingHouse(),
//                book.getPublishDate(),book.getPrice(),book.getBorrowing(),book.getId());
//        return (rows>0)&&(row4>0)&&(row5>0||row6>0);
        deleteById(book.getId());
        save(book);
        return save(book);
    }

    @Override
    public List<Book> findAllexport(String[] ids) {
        String str = Stream.of(ids).map(String::valueOf).collect(Collectors.joining(","));
        String sql = "select id,book_name,author,`type`,brief_introduction,pages,publishing_house,publish_date,price,borrowing from book_info where id in (" + str + ")";
        return template.query(sql, new BeanPropertyRowMapper<>(Book.class));

    }

}
