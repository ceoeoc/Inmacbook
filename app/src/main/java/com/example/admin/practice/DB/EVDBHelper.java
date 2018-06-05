package com.example.admin.practice.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EVDBHelper extends SQLiteOpenHelper {
    public static int DBVersion = 1;

    public static String DBName = "Event";

    public static String TBName = "Event";

    public static String ColEid = "eid";
    public static String ColName = "ename";
    public static String ColStDate = "std";
    public static String ColEdDate = "edd";
    public static String ColHour = "hour";
    public static String ColPrg = "progrss";
    public static String ColMem = "member";

    public static String create =
            "CREATE TABLE " + TBName + " ( " + ColEid + " integer primary key, "
                    + ColName + " text not null,"
                    + ColStDate + " text not null, "
                    + ColEdDate + " text not null, "
                    + ColHour + " integer not null, "
                    + ColPrg + " integer not null, "
                    + ColMem + " text not null); ";

    public EVDBHelper(Context context){
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
