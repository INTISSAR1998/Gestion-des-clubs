package com.example.alaanadanesrine.projetNoSQL.Events;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;



public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "events_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create events table
        db.execSQL(Event.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Event.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertEvent(String name,String beginDate,String endDate,String place,String description) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add them
        values.put(Event.COLUMN_NAME, name);
        values.put(Event.COLUMN_BEGINDATE, beginDate);
        values.put(Event.COLUMN_ENDDATE, endDate);
        values.put(Event.COLUMN_PLACE, place);
        values.put(Event.COLUMN_DESCRIPTION, description);

        // insert row
        long id = db.insert(Event.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public boolean getEvent(String name) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Event.TABLE_NAME,
                new String[]{Event.COLUMN_NAME},
                Event.COLUMN_NAME + "=?",
                new String[]{name}, null, null, null, null);

        if (cursor.getCount()>0)
            return true;

        return false;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Event.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setEventName(cursor.getString(cursor.getColumnIndex(Event.COLUMN_NAME)));
                event.setBeginDate(cursor.getString(cursor.getColumnIndex(Event.COLUMN_BEGINDATE)));
                event.setEndDate(cursor.getString(cursor.getColumnIndex(Event.COLUMN_ENDDATE)));
                event.setEventPlace(cursor.getString(cursor.getColumnIndex(Event.COLUMN_PLACE)));
                event.setEventDescription(cursor.getString(cursor.getColumnIndex(Event.COLUMN_DESCRIPTION)));

                events.add(event);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return events list
        return events;
    }

    public int getEventCount() {
        String countQuery = "SELECT  * FROM " + Event.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Event.TABLE_NAME, Event.COLUMN_NAME + " = ?",
                new String[]{String.valueOf(event.getEventName())});
        db.close();
    }
}
