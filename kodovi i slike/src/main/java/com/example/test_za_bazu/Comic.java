package com.example.test_za_bazu;

import java.util.ArrayList;

public class Comic {
    private String comic_thumbnail;
    private String comic_title;
    private int comic_ID;
    private int comic_author_ID;
    private String comic_description;

    public int getComic_author_ID() {
        return comic_author_ID;
    }

    public int getComic_ID() {return comic_ID;}

    public String getComic_title() {return comic_title;}

    public String getComic_description() {return comic_description;}

    public String getComic_thumbnail() {
        return comic_thumbnail;
    }

    public void setComic_author_ID(int comic_author_ID) {
        this.comic_author_ID = comic_author_ID;
    }

    public void setComic_description(String comic_description) {
        this.comic_description = comic_description;
    }

    public void setComic_thumbnail(String comic_thumbnail) {
        this.comic_thumbnail = comic_thumbnail;
    }

    public void setComic_ID(int comic_ID) {
        this.comic_ID = comic_ID;
    }

    public void setComic_title(String comic_title) {
        this.comic_title = comic_title;
    }
}

