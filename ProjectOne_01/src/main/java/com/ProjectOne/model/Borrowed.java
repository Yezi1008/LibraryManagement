package com.ProjectOne.model;

import java.time.LocalDate;
import java.util.Date;

public class Borrowed {
    private int id;
    private String name;
    private String sex;
    private  int age;
    private String bookname;
    private LocalDate borrowtime;
    private String phone;

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public LocalDate getBorrowtime() {
        return borrowtime;
    }

    public void setBorrowtime(LocalDate borrowtime) {
        this.borrowtime = borrowtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
