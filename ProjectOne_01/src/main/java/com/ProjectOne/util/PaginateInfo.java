package com.ProjectOne.util;

public class PaginateInfo {
    private int pageNo;
    private final int pageSize;
    private long count;//总数
    private int pages;//总页数
    private int navFirst;//相对位置第一页
    private int navLast;//相对位置最后一页
    private int navPages=5;//显示页数

    public PaginateInfo(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return (this.pageNo - 1) * pageSize;
    }

    public int getLimit() {
        return this.pageSize;
    }

    public long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
        this.pages=(int)this.count/10;
        if (this.count % 10 >0){
            this.pages++;
        }
        if (this.pageNo<1){
            this.pageNo=1;
        }
        if (this.pageNo>pages){
            pageNo=pages;
        }
        int half=this.navPages/2;
        navFirst=pageNo-half;
        if (navFirst<1){
            navFirst=1;
        }
        navLast=navFirst+navPages-1;
        if (navLast>pages){
            navLast=pages;
            navFirst=navLast-navPages+1;
            if (navFirst<1){//不够5页，确保首页是1
                navFirst=1;
            }
        }

    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getNavFirst() {
        return navFirst;
    }

    public void setNavFirst(int navFirst) {
        this.navFirst = navFirst;
    }

    public int getNavLast() {
        return navLast;
    }

    public void setNavLast(int navLast) {
        this.navLast = navLast;
    }
}
