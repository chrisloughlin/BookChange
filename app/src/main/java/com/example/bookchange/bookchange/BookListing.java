package com.example.bookchange.bookchange;

import android.graphics.Bitmap;

/**
 * Created by christopher on 2/26/17.
 */

/*
This is where the actual post information is stored for each post. I.E. Price, Title, Class etc.
 */

public class BookListing {
    private String mId;
    private String mPosterUsername;
    private double mPrice;
    private String mBookTitle;
    private String mClassName;
    private String mEmail;
    private String bookPic = "";

    /*

     */
    public BookListing(String posterUsername, double price, String bookTitle, String className, String email){
        mPosterUsername = posterUsername;
        mPrice = price;
        mBookTitle = bookTitle;
        mClassName = className;
        mEmail = email;
    }

    /*
    Default constructor for debugging only
     */
    public BookListing(){
        mId = "";
        mPosterUsername = "Phill";
        mPrice = 3.50;
        mBookTitle = "book";
        mClassName = "Courses";
        mEmail= "email@email.com";
    }

    public void setId(String id){mId = id;}
    public void setPosterUsername(String username){mPosterUsername = username;}
    public void setPrice(double price){mPrice = price;}
    public void setBookTitle(String bookTitle){mBookTitle = bookTitle;}
    public void setClassName(String className){mClassName = className;}
    public void setEmail(String email){mEmail = email;}
    public void setPic(String base64){ bookPic = base64;}

    public String getId(){return mId;}
    public String getPosterUsername(){return mPosterUsername;}
    public double getPrice(){return mPrice;}
    public String getBookTitle(){return mBookTitle;}
    public String getClassName(){return mClassName;}
    public String getEmail(){return mEmail;}
    public String getBookPic() {return bookPic;}
}
