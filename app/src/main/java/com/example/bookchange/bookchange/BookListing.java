package com.example.bookchange.bookchange;

/**
 * Created by christopher on 2/26/17.
 */

public class BookListing {
    private String mId;
    private String mPosterUsername;
    private double mPrice;
    private String mBookTitle;
    private String mClassName;

    public BookListing(String posterUsername, double price, String bookTitle, String className){
        mPosterUsername = posterUsername;
        mPrice = price;
        mBookTitle = bookTitle;
        mClassName = className;
    }

    public void setId(String id){mId = id;}

    public String getId(){return mId;}
    public String getPosterUsername(){return mPosterUsername;}
    public double getPrice(){return mPrice;}
    public String getBookTitle(){return mBookTitle;}
    public String getClassName(){return mClassName;}
}
