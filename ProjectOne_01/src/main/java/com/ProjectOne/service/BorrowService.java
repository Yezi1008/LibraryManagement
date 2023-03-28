package com.ProjectOne.service;

import com.ProjectOne.model.Book;
import com.ProjectOne.model.BookSeachBean;
import com.ProjectOne.model.Borrowed;
import com.ProjectOne.util.PaginateInfo;

import java.util.List;

public interface BorrowService {
    List<Borrowed> findAll(Borrowed borrowed, PaginateInfo pi);

    int deleteByIds(String[] ids);
}
