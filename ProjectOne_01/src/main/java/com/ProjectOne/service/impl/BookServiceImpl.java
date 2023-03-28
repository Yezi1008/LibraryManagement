package com.ProjectOne.service.impl;

import com.ProjectOne.dao.BookDao;
import com.ProjectOne.dao.impl.BookDaoImpl;
import com.ProjectOne.model.Book;
import com.ProjectOne.model.BookSeachBean;
import com.ProjectOne.service.BookService;
import com.ProjectOne.util.BeanFactory;
import com.ProjectOne.util.PaginateInfo;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final BookDao dao= BeanFactory.getBean(BookDaoImpl.class);
    @Override
    public List<Book> findAll(BookSeachBean bsb, PaginateInfo pi) {
        return dao.findAll(bsb,pi);
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

    @Override
    public boolean save(Book book) {
        return dao.save(book);
        //大于0成功
    }

    @Override
    public boolean update(Book book) {
        return dao.update(book);
    }

    @Override
    public boolean hasId(String ID) {
        BookSeachBean bsb=new BookSeachBean();
        bsb.setId(ID);
        List<Book> books=dao.findAll(bsb,new PaginateInfo(1,Integer.MAX_VALUE));
        return books.size()>0;
    }

    @Override
    public List<Book> findAllexport(String[] ids) {
        return dao.findAllexport(ids);
    }


}
