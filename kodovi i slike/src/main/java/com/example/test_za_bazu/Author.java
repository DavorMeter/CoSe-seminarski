package com.example.test_za_bazu;

import android.content.Context;

public class Author {
    private int author_ID;
    private String author_name;

    public int getAuthor_ID(){return author_ID;}

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_ID(int author_ID) {
        this.author_ID = author_ID;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }
}