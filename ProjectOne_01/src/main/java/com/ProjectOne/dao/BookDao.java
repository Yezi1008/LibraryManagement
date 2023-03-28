package com.ProjectOne.dao;

import com.ProjectOne.model.Book;
import com.ProjectOne.model.BookSeachBean;
import com.ProjectOne.util.PaginateInfo;

import java.util.List;

public interface BookDao {
    List<Book> findAll(BookSeachBean ssb, PaginateInfo pi);



    boolean deleteById(String id);

    boolean save(Book book);

    boolean update(Book book);

    List<Book> findAllexport(String[] ids);

}
