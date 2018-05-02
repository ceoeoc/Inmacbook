package com.example.admin.practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

public class DBHandler {
    DBHelper dh;
    SQLiteDatabase db;
    public DBHandler(Context context){
        dh = new DBHelper(context);
    }

    public void open() throws SQLException{
        db = dh.getWritableDatabase();
    }

    public void close(){
        dh.close();
    }

    public long insert(ContactsItem con) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.ColId, con.get_Id());
        cv.put(DBHelper.ColPhone, con.getPhone());
        cv.put(DBHelper.ColPoint, con.getPoint());
        cv.put(DBHelper.ColFeat, con.getFeat());

        long i = db.insert(DBHelper.TBName, null, cv);
        return i;
    }

    public void update(ContactsItem con){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.ColId, con.get_Id());
        cv.put(DBHelper.ColPhone, con.getPhone());
        cv.put(DBHelper.ColPoint, con.getPoint());
        cv.put(DBHelper.ColFeat, con.getFeat());
        db.update(DBHelper.TBName, cv, DBHelper.ColId + "=" + con.get_Id(),null);
    }

    public void delete(long i) {
        db.delete(DBHelper.TBName , DBHelper.ColId + "=" + i , null);
    }

    public List<ContactsItem> getData(){
        ContactsItem c;
        List<ContactsItem> con = new ArrayList<ContactsItem>();
        Cursor curs = db.query(DBHelper.TBName, new String[] {DBHelper.ColId,DBHelper.ColPhone, DBHelper.ColPoint,DBHelper.ColFeat}
        ,null,null,null,null,null,null);
        curs.moveToFirst();
        while(!curs.isAfterLast()){
            c = cursorToContact(curs);
            con.add(c);
            curs.moveToNext();
        }

        curs.close();
        return con;
    }

    public ContactsItem cursorToContact(Cursor curs){
        ContactsItem c = new ContactsItem();
        c.set_Id(curs.getInt(0));
        c.setPhone(curs.getString(1));
        c.setPoint(curs.getInt(2));
        c.setFeat(curs.getString(3));
        return c;
    }

}

