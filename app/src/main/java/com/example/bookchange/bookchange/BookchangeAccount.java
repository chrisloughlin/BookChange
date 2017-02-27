package com.example.bookchange.bookchange;

import java.util.ArrayList;

/**
 * Created by christopher on 2/26/17.
 */

public class BookchangeAccount {
    private String accountName;
    private String emailAddress;
    private ArrayList<String> subscriptions = new ArrayList<String>();

    public BookchangeAccount(String accountName, String emailAddress){


    }

    public void addSubscription(String subscription){
        if(!subscriptions.contains(subscription)){
          subscriptions.add(subscription);
        }
    }

}
