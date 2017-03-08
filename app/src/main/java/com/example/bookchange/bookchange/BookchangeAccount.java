package com.example.bookchange.bookchange;

import java.util.ArrayList;

/**
 * Created by christopher on 2/26/17.
 */

/*
This class is the bas
 */
public class BookchangeAccount {
    private String accountName;
    private String emailAddress;
//    private ArrayList<String> subscriptions = new ArrayList<>();
    private String acctPic = "";
    private int totalRatings;
    private int avgRating;
    private ArrayList<String> pendingTransactions = new ArrayList<>();
    private ArrayList<String> contactedBy = new ArrayList<>();

    public BookchangeAccount(String name, String address){
        accountName = name;
        emailAddress = address;
        totalRatings = 0;
        avgRating = 0;
    }

    public void setAcctPic(String pic) { acctPic = pic; }
    public String getAcctPic() { return acctPic; }
    public String getAccountName(){ return accountName; }

    public String getEmailAddress() { return emailAddress; }
}
