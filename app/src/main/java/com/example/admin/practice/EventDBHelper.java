package com.example.admin.practice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventDBHelper extends SQLiteOpenHelper {

    public static int DBVersion = 1;

    public static String DBName = "Contacts";

    public static String TBName = "ContactTable";

    public static String Title = "title";
    public static String StartDate = "start_date";
    public static String EndDate = "end_date";
    public static String Time = "time";

    public static String create =
            "CREATE TABLE " + TBName + " ( " + Title + " text primary key, "
                    + StartDate + " text not null, "
                    + EndDate + " text not null, "
                    + Time + " integer not null);";

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
