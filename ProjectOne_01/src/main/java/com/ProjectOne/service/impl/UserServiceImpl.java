package com.ProjectOne.service.impl;

import com.ProjectOne.dao.UserDao;
import com.ProjectOne.dao.impl.UserDaoImpl;
import com.ProjectOne.model.User;
import com.ProjectOne.service.UserService;
import com.ProjectOne.util.BeanFactory;
import com.ProjectOne.util.Md5Utils;

public class UserServiceImpl implements UserService {

    private final UserDao dao= BeanFactory.getBean(UserDaoImpl.class);
    //返回唯一实例
    @Override
    public boolean checkLogin(User user, String password) {
        if (user==null){
            return false;
        }
        String encrypt= Md5Utils.encrypt(password+"{"+user.getUsername()+"}");
        if (encrypt.equals(user.getPassword())){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public boolean updateuse(User user) {
        return dao.updateuse(user);
    }
}
