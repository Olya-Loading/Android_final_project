package com.example.final_project;

import java.io.Serializable;

class Book implements Serializable {
    private String title;
    public Book(String title){
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
