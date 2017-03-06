package com.example.bookchange.bookchange;

import java.util.ArrayList;

/**
 * Created by sachinvadodaria on 3/5/17.
 *
 * Class to contain subscriptions, used to easily push to db in nested format
 */

public class Subscription {
    private String userID;
    private ArrayList<String> courses;

    public Subscription(){
    }

    public Subscription(String id){
        userID = id;
        courses = new ArrayList<String>();
    }

    public void setId(String id){ userID = id;}

    public void setClasses(ArrayList<String> classes){ courses = classes;}

    public String getId(){return userID;}

    public ArrayList<String> getClasses(){return courses;}

    public void addToClasses(String c){ courses.add(c); }
}
