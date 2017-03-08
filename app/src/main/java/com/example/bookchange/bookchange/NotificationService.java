package com.example.bookchange.bookchange;

import android.app.Service;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Ted on 3/8/2017.
 */

public class NotificationService extends Service{
    private NotificationBinder notificationBinder = new NotificationBinder();
    private boolean serviceIsStarted;
    private NotificationManager notificationManager;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String userId;

    public void onCreate() {
        super.onCreate();
        serviceIsStarted = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userId = mUser.getUid();
        mDatabase.child("users").child(userId).child("subscriptions").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mDatabase.child("courses").child(dataSnapshot.getValue(String.class)).child("listings")
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                // send notification
                                showNotification();
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {
                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    // binder to return
    public class NotificationBinder extends Binder {
    }

    // returns a binder
    @Override
    public IBinder onBind(Intent intent) {
        if (!serviceIsStarted) {
            serviceIsStarted = true;
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    showNotification();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("NotificationError", databaseError.getMessage());
                }
            };

        }
        return notificationBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        if (!serviceIsStarted) {
            serviceIsStarted = true;

            showNotification();
        }
        // makes sure onStartCommand is called after a service is recreated
        return START_NOT_STICKY;
    }

    // handle service shutdown
    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceIsStarted = false;
        notificationManager.cancelAll();
    }

    // shows a notification
    private void showNotification() {
        Intent i = new Intent(this, MainActivity.class);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("BookChange");
        builder.setContentText("New listing!");
        builder.setSmallIcon(R.drawable.bookchange_small); //placeholder icon taken from example code
        builder.setContentIntent(PendingIntent.getActivity(this, 0, i, 0));
        Notification notification = builder.build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags |= 2;
        notificationManager.notify(0, notification);
        stopSelf();
    }
}
