package com.ProjectOne.dao.impl;

import com.ProjectOne.dao.TypeDao;
import com.ProjectOne.global.Global;
import com.ProjectOne.model.TypeModel;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TypeDaoImpl implements TypeDao {
    //执行sql语句,实现方法
    //JdbcTemplate是Spring JDBC的核心类，借助该类提供的方法可以很方便的实现数据的增删改查
    private final JdbcTemplate template=new JdbcTemplate(Global.getDataSource());
    @Override
    public List<TypeModel> findAll() {
        String sql="select typename,all_book,remaining,borrowed from typelist";
        List<TypeModel> types=template.query(sql,new BeanPropertyRowMapper(TypeModel.class));
        System.out.println(types);
        return types;
    }

    @Override
    public boolean deleteBytypes(String type) {
        String sql1="update book_info set type='无类型' where  type =?";
        String sql2="delete from typelist where typename=?";
        int row1=template.update(sql1,type);
        int row2=template.update(sql2,type);

        return (row1>0 && row2>0);
    }

    @Override
    public int save(TypeModel typeModel) {
        String sql = "insert into typelist (typename,all_book,remaining,borrowed) values (?,?,?,?)";
        int rows = template.update(sql,typeModel.getTypename(),typeModel.getAllBook(),typeModel.getRemaining(),typeModel.getBorrowed());
        return rows;
    }

    @Override
    public boolean change(String typename) {
        String sql="update typelist set typename=? where typename=?";


        int rows = template.update(sql,typename);
        return rows>0;
    }

}
