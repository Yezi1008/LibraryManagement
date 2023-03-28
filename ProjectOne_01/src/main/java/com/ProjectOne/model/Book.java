package com.ProjectOne.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDate;
@Data
public class Book {
    //1.书的id
    @ExcelProperty("ID")
    public String id;
    //2.书名
    @ExcelProperty("书名")
    public String bookName;
    //3.作者
    @ExcelProperty("作者")
    public String author;
    @ExcelProperty("类型")
    //4.类型
    public String type;
    @ExcelProperty("内容简介")
    //5.内容简介
    public String briefIntroduction;
    //6.页数
    @ExcelProperty("页数")
    public int pages;
    //7.出版社
    @ExcelProperty("出版社")
    public String publishingHouse;
    //8.出版时间
    @ExcelProperty("出版时间")
    public LocalDate publishDate;
    //9.价格
    @ExcelProperty("价格")
    public float price;
    @ExcelProperty("借阅")
    public String borrowing;

    public String getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(String borrowing) {
        this.borrowing = borrowing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
