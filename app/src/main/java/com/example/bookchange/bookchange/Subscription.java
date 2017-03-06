package com.example.bookchange.bookchange;

import java.util.ArrayList;

/**
 * Created by sachinvadodaria on 3/5/17.
 *
 * Class to contain subscriptions, used to easily push to db in nested format
 */

public class Subscription {
    private String userID;
    private String courses;

    public Subscription(){
    }

    public Subscription(String id){
        userID = id;
        courses = new String();
    }

    public void setId(String id){ userID = id;}

    public void setClasses(String classes){ courses = classes;}

    public String getId(){return userID;}

    public String getClasses(){return courses;}

    public void addSubscription(String c){ courses = c; }
}
