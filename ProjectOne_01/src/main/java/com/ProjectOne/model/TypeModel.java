package com.ProjectOne.model;

public class TypeModel {
    private String typename;
    private Integer allBook;
    private Integer remaining;//剩余书
    private Integer borrowed;//已经借出的书

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Integer getAllBook() {
        return allBook;
    }

    public void setAllBook(Integer allBook) {
        this.allBook = allBook;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Integer borrowed) {
        this.borrowed = borrowed;
    }
}
