package com.ProjectOne.service.impl;

import com.ProjectOne.dao.BookDao;
import com.ProjectOne.dao.BorrowDao;
import com.ProjectOne.dao.impl.BookDaoImpl;
import com.ProjectOne.dao.impl.BorrowDaoImpl;
import com.ProjectOne.model.Book;
import com.ProjectOne.model.BookSeachBean;
import com.ProjectOne.model.Borrowed;
import com.ProjectOne.service.BorrowService;
import com.ProjectOne.util.BeanFactory;
import com.ProjectOne.util.PaginateInfo;

import java.util.List;

public class BorrowServiceImpl implements BorrowService {

    private final BorrowDao dao= BeanFactory.getBean(BorrowDaoImpl.class);
    @Override
    public List<Borrowed> findAll(Borrowed borrowed, PaginateInfo pi) {
        return dao.findAll(borrowed,pi);
    }

    @Override
    public int deleteByIds(String[] ids) {
        int count = 0;
        for (String id : ids) {
            boolean b = dao.deleteById(id);
            if (b) {
                count++;
            }
        }
        return count;
    }
}
