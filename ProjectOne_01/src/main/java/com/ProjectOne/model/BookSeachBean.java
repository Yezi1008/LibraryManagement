package com.ProjectOne.model;

import java.time.LocalDate;

public class BookSeachBean extends Book{

    private LocalDate publishDataFrom;
    private LocalDate publishDataTo;

    public LocalDate getPublishDataFrom() {
        return publishDataFrom;
    }

    public void setPublishDataFrom(LocalDate publishDataFrom) {
        this.publishDataFrom = publishDataFrom;
    }

    public LocalDate getPublishDataTo() {
        return publishDataTo;
    }

    public void setPublishDataTo(LocalDate publishDataTo) {
        this.publishDataTo = publishDataTo;
    }
}
