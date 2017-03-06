package com.example.bookchange.bookchange;

/**
 * Created by sachinvadodaria on 3/5/17.
 *
 * Class to contain subscriptions, used to easily push to db in nested format
 */

public class Subscription {
    private String id;
    private String courses;

    public Subscription(){
    }

    public Subscription(String id){
        this.id = id;
        courses = new String();
    }

    public void setId(String id){ this.id = id;}

    public void setClasses(String classes){ courses = classes;}

    public String getId(){return id;}

    public String getClasses(){return courses;}

    public void addSubscription(String c){ courses = c; }
}
