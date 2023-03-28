package com.ProjectOne.dao.impl;

import com.ProjectOne.dao.BorrowDao;
import com.ProjectOne.global.Global;
import com.ProjectOne.model.Book;
import com.ProjectOne.model.BookSeachBean;
import com.ProjectOne.model.Borrowed;
import com.ProjectOne.util.PaginateInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BorrowDaoImpl implements BorrowDao {
    private final JdbcTemplate template=new JdbcTemplate(Global.getDataSource());

    public List<Borrowed> findAll(Borrowed borrowed, PaginateInfo pi) {
        String count="select count(0) from borrowlist";
        String sql="select * from borrowlist";
        List<Object> args=new ArrayList<>();

        StringBuilder whereSql = new StringBuilder();
        if (borrowed != null) {
            whereSql.append(" where 1=1");


            if (StringUtils.hasText(borrowed.getName())) {
                whereSql.append(" and name like ?");
                args.add("%" + borrowed.getName() + "%");
            }
            if (StringUtils.hasText(borrowed.getBookname())) {
                whereSql.append(" and bookname like ?");
                args.add("%" + borrowed.getBookname() + "%");
            }

        }
        count += whereSql.toString();
        //con返回long类型
        Long con=template.queryForObject(count,Long.class,args.toArray());
        pi.setCount(con);
        whereSql.append(" limit ?,?");
//        System.out.println("22222"+whereSql);
        args.add(pi.getOffset());
        args.add(pi.getLimit());

        sql+=whereSql.toString();
        List<Borrowed> borroweds=template.query(sql,new BeanPropertyRowMapper(Borrowed.class),args.toArray());
        System.out.println(borroweds.get(0).getBookname());
        //执行给定的 SQL 并返回所需类型的对象。
        //设pi中的count为con
        return borroweds;
    }

    @Override
    public boolean deleteById(String id) {
        String presql="update book_info set borrowing='否' where book_name in " +
                "(select bookname from borrowlist where id=?)";
        String nextsql="update typelist set remaining=(remaining+1)" +
                "where typename in (select `type` from book_info where book_name in (select bookname from borrowlist where id=?))";
        String nextsql2="update typelist set borrowed=(borrowed-1) " +
                "where typename in (select `type` from book_info where book_name in (select bookname from borrowlist where id=?))";
        String sql = "delete from borrowlist where id = ?";
//        int prerows=template.update(presql,id);

//        int lastrows=template.update(lastsql,id);
        int prerows=template.update(presql,id);
        int nextrows=template.update(nextsql,id);
        int nextrows2=template.update(nextsql2,id);
        int rows = template.update(sql, id);

        return (rows > 0) && (prerows>0) && (nextrows>0) && (nextrows2>0);
    }
}
