package com.example.admin.practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.RxContacts.Contact;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class  DBHandler {
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

    public boolean isExist(String key){
        Cursor curs = db.query(DBHelper.TBName, null
                ,DBHelper.ColId + " = " + "'"+ key+"'",null,null,null,null,null);
        if(curs.getCount()== 0) return false;
        return true;
    }

    public long insert(ContactsItem con) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.ColId, con.get_id());
        cv.put(DBHelper.ColName, con.getName());
        cv.put(DBHelper.ColPhone, con.getPhone());
        cv.put(DBHelper.ColPoint, con.getPoint());
        cv.put(DBHelper.ColLevel, con.getLevel());
        cv.put(DBHelper.ColFeat, con.getFeat());
        cv.put(DBHelper.ColGroup, con.getGroup());
        cv.put(DBHelper.ColBluth, con.getBluth());
        // cv.put(DBHelper.ColPhoto, con.getPhoto());

        long i = db.insert(DBHelper.TBName, null, cv);
        return i;
    }

    public void update(ContactsItem con){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.ColId, con.get_id());
        cv.put(DBHelper.ColName, con.getName());
        cv.put(DBHelper.ColPhone, con.getPhone());
        cv.put(DBHelper.ColPoint, con.getPoint());
        cv.put(DBHelper.ColLevel, con.getLevel());
        cv.put(DBHelper.ColFeat, con.getFeat());
        cv.put(DBHelper.ColGroup, con.getGroup());
        cv.put(DBHelper.ColBluth, con.getBluth());
        //cv.put(DBHelper.ColPhoto, con.getPhoto());

        db.update(DBHelper.TBName, cv, DBHelper.ColId + " = " + "'"+ con.get_id()+"'",null);
    }

    public void delete(String i) {
        db.delete(DBHelper.TBName , DBHelper.ColId + " = " + i , null);
    }

    public ContactsItem getData(String cid){
        ContactsItem c = new ContactsItem();
        Cursor curs = db.query(DBHelper.TBName,null,DBHelper.ColId + " = " + "'"+cid+"'",null,null,null,null,null);
        curs.moveToFirst();
        c.set_id(curs.getString(0));
        c.setName(curs.getString(1));
        c.setPhone(curs.getString(2));
        c.setPoint(curs.getInt(3));
        c.setLevel(curs.getInt(4));
        c.setFeat(curs.getString(5));
        c.setGroup(curs.getString(6));
        c.setBluth(curs.getString(7));
        curs.close();
        return c;
    }
    public List<ContactsItem> getData(){
        ContactsItem c;
        List<ContactsItem> con = new ArrayList<ContactsItem>();

        Cursor curs = db.query(DBHelper.TBName, null
                ,null,null,null,null,DBHelper.ColName + " asc ",null);

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

        c.set_id(curs.getString(0));
        c.setName(curs.getString(1));
        c.setPhone(curs.getString(2));
        c.setPoint(curs.getInt(3));
        c.setLevel(curs.getInt(4));
        c.setFeat(curs.getString(5));
        c.setGroup(curs.getString(6));
        c.setBluth(curs.getString(7));
        //c.setPhoto(curs.getBlob(7));
        return c;
    }

    public int sizeofData(String args, int which){
        int ret = 0;

        switch (which){
            case 0:
                Cursor curs = db.query(DBHelper.TBName, null
                        ,DBHelper.ColGroup + " = " + "'" + args+ "'",null,null,null,null,null);
                ret = curs.getCount();
                curs.close();
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            default:

                break;
        }
        return ret;
    }
}

