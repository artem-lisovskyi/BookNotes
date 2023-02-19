package com.example.booknotes;

import java.util.List;


public class Books {
    private String mTitle;
    private List<String> mAuthor;
    private String mDescription;
    private String mImage;
    private int mYear;

    public Books(String title, List<String> author, String image) {
        mTitle = title;
        mAuthor = author;
        mImage = image;
    }

    public Books(String title, List<String> author, String description, String image, int year) {
        new Books(title, author, image);
        this.mDescription = description;
        this.mYear = year;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<String> getAuthor() {
        return mAuthor;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImage() {
        return mImage;
    }

    public int getYear() {
        return mYear;
    }
}
