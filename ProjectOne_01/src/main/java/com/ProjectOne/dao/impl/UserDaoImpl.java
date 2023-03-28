package com.ProjectOne.dao.impl;

import com.ProjectOne.dao.UserDao;
import com.ProjectOne.global.Global;
import com.ProjectOne.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final JdbcTemplate template=new JdbcTemplate(Global.getDataSource());
    @Override
    public User findByUsername(String username) {
        String sql="select username,password,`name`,sex,age from user where username=?";
        List<User> users=template.query(sql, new BeanPropertyRowMapper(User.class),username);
        return users.size()>0 ? users.get(0) :null ;
    }

    @Override
    public boolean updateuse(User user) {
        String sql="update user set password=? ,`name`=?,`sex`=?,`age`=? where username=?";
        int rows=template.update(sql,user.getPassword(),user.getName(),user.getSex(),user.getAge(),user.getUsername());
        return rows>0;

    }


}
