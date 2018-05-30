package com.example.admin.practice.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.practice.ContactRecord;

import java.util.ArrayList;
import java.util.List;

public class EventDBHandler{

    EventDBHelper dh;
    SQLiteDatabase db;
    public EventDBHandler(Context context){ dh = new EventDBHelper(context);}

    public void open() throws SQLException {
        db = dh.getWritableDatabase();
    }
    public void close(){
        dh.close();
    }

    public boolean isExist(String key){
        Cursor curs = db.query(EventDBHelper.TBName, null,
                EventDBHelper.ETitle + " = " + "'" + key + "'", null, null,null,null,null);
        if(curs.getCount() == 0) return false;
        return true;
    }

    public long insert(ContactRecord con){
        ContentValues cv = new ContentValues();
        cv.put(EventDBHelper.ETitle, con.get_id());
        cv.put(EventDBHelper.EStart, con.getLastmeet());
        cv.put(EventDBHelper.EEnd, con.getLastcall());
        cv.put(EventDBHelper.ETime, con.getTotalmeet());

        long i = db.insert(CRDBHelper.TBName,null, cv);
        return i;
    }

    public void update(ContactRecord con){
        ContentValues cv = new ContentValues();
        cv.put(EventDBHelper.ETitle, con.get_id());
        cv.put(EventDBHelper.EStart, con.getLastmeet());
        cv.put(EventDBHelper.EEnd, con.getLastcall());
        cv.put(EventDBHelper.ETime, con.getTotalmeet());

        db.update(EventDBHelper.TBName,cv, EventDBHelper.ETitle + " = " + "'" + con.get_id() + "'", null);
    }

    public void delete(String i){
        db.delete(EventDBHelper.TBName, EventDBHelper.ETitle + " = " + i,null);
    }

    public ContactRecord getData(String cid){
        ContactRecord c = new ContactRecord();
        Cursor curs = db.query(EventDBHelper.TBName,null, EventDBHelper.ETitle + " = " + "'"+cid+"'",null,null,null,null,null);
        curs.moveToFirst();
        c.set_id(curs.getString(0));
        c.setLastmeet(curs.getLong(1));
        c.setLastcall(curs.getLong(2));
        c.setTotalmeet(curs.getLong(3));
        c.setTotalcall(curs.getLong(4));
        c.setTotal(curs.getLong(5));
        curs.close();
        return c;
    }
    public List<ContactRecord> getData(){
        ContactRecord c;
        List<ContactRecord> con = new ArrayList<>();

        Cursor curs = db.query(CRDBHelper.TBName, null
                ,null,null,null,null, null,null);

        curs.moveToFirst();
        while(!curs.isAfterLast()){
            c = cursorToRecord(curs);
            con.add(c);
            curs.moveToNext();
        }
        curs.close();
        return con;
    }

    public ContactRecord cursorToRecord(Cursor curs){
        ContactRecord c = new ContactRecord();

        c.set_id(curs.getString(0));
        c.setLastmeet(curs.getLong(1));
        c.setLastcall(curs.getLong(2));
        c.setTotalmeet(curs.getLong(3));
        c.setTotalcall(curs.getLong(4));
        c.setTotal(curs.getLong(5));
        return c;
    }
}
