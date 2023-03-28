package com.ProjectOne.service;

import com.ProjectOne.model.Book;
import com.ProjectOne.model.BookSeachBean;
import com.ProjectOne.util.PaginateInfo;

import java.util.List;
//业务
public interface BookService {
    List<Book> findAll(BookSeachBean ssb, PaginateInfo pi);

    int deleteByIds(String[] ids);

    boolean save(Book book);
//Java接口的时候，是不能有方法体的函数，就类似于C++中的虚函数，default关键字在接口中修饰方法时，方法可以有方法体。
// 可以调用其他函数
    //业务类注重写业务
    //DAO里面注重存取
    default Book findById(String id){
        //默认方法
        BookSeachBean bsb=new BookSeachBean();
        bsb.setId(id);
        PaginateInfo pi=new PaginateInfo(1,1);
        //只一条
        List<Book> books=findAll(bsb,pi);
        //如果size大于0，取出第一条，否则返回空
        return books.size()>0?books.get(0):null;
    }

    boolean update(Book book);

    boolean hasId(String ID);

    List<Book> findAllexport(String[] ids);


}
