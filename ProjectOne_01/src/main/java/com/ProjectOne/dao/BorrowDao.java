package com.ProjectOne.dao;

import com.ProjectOne.model.Borrowed;
import com.ProjectOne.util.PaginateInfo;

import java.util.List;

public interface BorrowDao {

    List<Borrowed> findAll(Borrowed borrowed, PaginateInfo pi);

    boolean deleteById(String id);

}
