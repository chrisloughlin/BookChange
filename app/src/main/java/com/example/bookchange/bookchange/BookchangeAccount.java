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
    private int totalStars;
    private int avgRating;

    public BookchangeAccount(String name, String address){
        accountName = name;
        emailAddress = address;
        totalStars = 0;
    }

//    public void addSubscription(String subscription){
//        if(!subscriptions.contains(subscription)){
//          subscriptions.add(subscription);
//        }
//    }
//
//    public void removeSubscription(String subscription){
//        if(subscriptions.contains(subscription)){
//            subscriptions.remove(subscription);
//        }
//    }

    public void setAcctPic(String pic) { acctPic = pic; }
    public String getAcctPic(){ return acctPic; }
    public String getAccountName(){return accountName;}

    public String getEmailAddress() {
        return emailAddress;
    }

//    public ArrayList<String> getSubscriptions() {
//        return subscriptions;
//    }

}
