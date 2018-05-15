package com.example.admin.practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class EventDBHandler {
    EventDBHelper dh;
    SQLiteDatabase db;
    public EventDBHandler(Context context){
        dh = new EventDBHelper(context);
    }

    public void open() throws SQLException {
        db = dh.getWritableDatabase();
    }

    public void close(){
        dh.close();
    }

    public long insert(EventItem con) {
        ContentValues cv = new ContentValues();
        cv.put(EventDBHelper.Title, con.getTitle());
        cv.put(EventDBHelper.StartDate, con.getStartDate());
        cv.put(EventDBHelper.EndDate, con.getEndDate());
        cv.put(EventDBHelper.Time, con.getTime());

        long i = db.insert(EventDBHelper.TBName, null, cv);
        return i;
    }

    public void update(EventItem con){
        ContentValues cv = new ContentValues();
        cv.put(EventDBHelper.Title, con.getTitle());
        cv.put(EventDBHelper.StartDate, con.getStartDate());
        cv.put(EventDBHelper.EndDate, con.getEndDate());
        cv.put(EventDBHelper.Time, con.getTime());

        db.update(EventDBHelper.TBName, cv, EventDBHelper.Title + " = " + "'"+ con.getTitle()+"'",null);
    }

    public void delete(String i) {
        db.delete(EventDBHelper.TBName , EventDBHelper.Title + " = " + i , null);
    }

//    public EventItem getData(String title){
//        EventItem c = new EventItem();
//        Cursor curs = db.query(EventDBHelper.TBName,null,EventDBHelper.Title + " = " + "'"+title+"'",null,null,null,null,null);
//        curs.moveToFirst();
//        c.setTitle(curs.getString(0));
//        c.setStartDate(curs.getString(1));
//        c.setEndDate(curs.getString(2));
//        c.setTime(curs.getString(3));
//        curs.close();
//        return c;
//    }
//
//    public List<EventItem> getData(){
//        EventItem c;
//        List<EventItem> con = new ArrayList<EventItem>();
//
//        Cursor curs = db.query(EventDBHelper.TBName, null
//                ,null,null,null,null,EventDBHelper.ColName + " asc ",null);
//
//        curs.moveToFirst();
//        while(!curs.isAfterLast()){
//            c = cursorToContact(curs);
//            con.add(c);
//            curs.moveToNext();
//        }
//        curs.close();
//        return con;
//    }

    public EventItem cursorToContact(Cursor curs){
        EventItem c = new EventItem();

        c.setTitle(curs.getString(0));
        c.setStartDate(curs.getString(1));
        c.setEndDate(curs.getString(2));
        c.setTime(curs.getString(3));
        //c.setPhoto(curs.getBlob(7));
        return c;
    }

}
