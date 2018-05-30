package com.example.admin.practice.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventDBHelper extends SQLiteOpenHelper {

    public static int DBVersion = 1;

    public static String DBName = "Contacts";

    public static String TBName = "ContactEvent";

    public static String ETitle;
    public static String EStart;
    public static String EEnd;
    public static String ETime;
    //public static String EFriends;

    public static String create =
            "CREATE TABLE " + TBName + " ( " + ETitle + " text primary key, "
                    + EStart + " integer , "
                    + EEnd + " integer ,"
                    + ETime + " integer not null);";

    public EventDBHelper(Context context){
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TBName);
        onCreate(db);
    }
}