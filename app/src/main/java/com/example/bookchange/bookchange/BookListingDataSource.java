package com.example.bookchange.bookchange;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by christopher on 2/26/17.
 */

public class BookListingDataSource {
    private SQLiteDatabase db;
    private BookListingDbHelper helper;

    public BookListingDataSource(Context context){ helper = new BookListingDbHelper(context);}

    public void open() throws SQLException{
        db = helper.getWritableDatabase();
    }

    public void close(){helper.close();}

    private String[] allColumns = {BookListingDbHelper.KEY_ROWID, BookListingDbHelper.KEY_POSTER_USERNAME,
            BookListingDbHelper.KEY_PRICE, BookListingDbHelper.KEY_BOOK_TITLE, BookListingDbHelper.KEY_CLASS_NAME};

    public long insertEntry(BookListing listing){
        ContentValues values = new ContentValues();
        values.put(BookListingDbHelper.KEY_POSTER_USERNAME,listing.getPosterUsername());
        values.put(BookListingDbHelper.KEY_PRICE, listing.getPrice());
        values.put(BookListingDbHelper.KEY_BOOK_TITLE, listing.getBookTitle());
        values.put(BookListingDbHelper.KEY_CLASS_NAME, listing.getClassName());
        long id = db.insert(BookListingDbHelper.TABLE_NAME_ENTRIES, null, values);
        return id;
    }

    public void removeEntry(long rowIndex){
        db.delete(BookListingDbHelper.TABLE_NAME_ENTRIES,BookListingDbHelper.KEY_ROWID+"="+rowIndex, null);
    }

    public BookListing fetchEntryByIndex(long rowId) {
        Cursor cursor = db.query(BookListingDbHelper.TABLE_NAME_ENTRIES, allColumns,
                BookListingDbHelper.KEY_ROWID + "=" + rowId , null, null, null, null);
        cursor.moveToFirst();
        BookListing newEntry = cursorToEntry(cursor , true);
        cursor.close();
        return newEntry;
    }

    //provide a username, provides all listings for that username
    public ArrayList<BookListing> fetchEntriesByPosterUsername(String posterUsername){
        ArrayList<BookListing> allEntries = fetchEntries();
        ArrayList<BookListing> matchingEntries = new ArrayList<>();
        Iterator<BookListing> iterator = allEntries.iterator();
        while(iterator.hasNext()){
            BookListing toAdd = iterator.next();
            if(toAdd.getPosterUsername().equals(posterUsername)){
                matchingEntries.add(toAdd);
            }
        }
        return matchingEntries;
    }

    //Provide a classname, provides all listings for that class
    public ArrayList<BookListing> fetchEntriesByClass(String className){
        ArrayList<BookListing> allEntries = fetchEntries();
        ArrayList<BookListing> matchingEntries = new ArrayList<>();
        Iterator<BookListing> iterator = allEntries.iterator();
        while(iterator.hasNext()){
            BookListing toAdd = iterator.next();
            if(toAdd.getClassName().equals(className)){
                matchingEntries.add(toAdd);
            }
        }
        return matchingEntries;
    }

    //Provide a ArrayList of classes that the user is subscribed to, returns an arraylist of listings from those classes
    public ArrayList<BookListing> fetchSubscriptions(ArrayList<String> subscriptions){
        ArrayList<BookListing> allEntries = fetchEntries();
        ArrayList<BookListing> matchingEntries = new ArrayList<>();
        Iterator<BookListing> iterator = allEntries.iterator();
        while(iterator.hasNext()){
            BookListing toAdd = iterator.next();
            Iterator<String> subIterator = subscriptions.iterator();
            while(subIterator.hasNext()) {
                String currSub = subIterator.next();
                if (toAdd.getClassName().equals(currSub)) {
                    matchingEntries.add(toAdd);
                    break;
                }
            }
        }
        return matchingEntries;
    }

    // Query the entire table, return all rows
    public ArrayList<BookListing> fetchEntries() {
        ArrayList<BookListing> allEntries = new ArrayList<BookListing>();
        Cursor cursor = db.query(BookListingDbHelper.TABLE_NAME_ENTRIES, null, null, null, null,
                null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            BookListing entry = cursorToEntry(cursor, false);
            allEntries.add(entry);
            cursor.moveToNext();
        }
        cursor.close();
        return allEntries;
    }

    public BookListing cursorToEntry(Cursor cursor, boolean gpsData){
        String posterUsername = cursor.getString(cursor.getColumnIndex(BookListingDbHelper.KEY_POSTER_USERNAME));
        double price = cursor.getDouble(cursor.getColumnIndex(BookListingDbHelper.KEY_PRICE));
        String bookTitle = cursor.getString(cursor.getColumnIndex(BookListingDbHelper.KEY_BOOK_TITLE));
        String className = cursor.getString(cursor.getColumnIndex(BookListingDbHelper.KEY_CLASS_NAME));
        BookListing entry = new BookListing(posterUsername, price, bookTitle, className);
        return entry;
    }

    public void clearDb(){ db.delete(BookListingDbHelper.TABLE_NAME_ENTRIES, null , null);}
}