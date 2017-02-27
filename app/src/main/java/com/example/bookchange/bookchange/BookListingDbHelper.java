package com.example.bookchange.bookchange;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by christopher on 2/26/17.
 */

public class BookListingDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "listings.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_ENTRIES = "listings";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_POSTER_USERNAME = "poster_username";
    public static final String KEY_PRICE = "price";
    public static final String KEY_BOOK_TITLE = "book_title";
    public static final String KEY_CLASS_NAME = "class_name";



    private static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_ENTRIES
            + " ("
            + KEY_ROWID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_POSTER_USERNAME
            + " STRING NOT NULL, "
            + KEY_PRICE
            + " DOUBLE NOT NULL, "
            + KEY_BOOK_TITLE
            + " STRING NOT NULL, "
            + KEY_CLASS_NAME
            + " STRING NOT NULL "
            + ");";

    public BookListingDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){db.execSQL(CREATE_TABLE_ENTRIES);}

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w("Tag", "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS ");
        onCreate(database);
    }
}
