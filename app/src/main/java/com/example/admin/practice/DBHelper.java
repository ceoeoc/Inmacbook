package com.example.admin.practice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static String DBName = "Contacts";
    public static int DBVersion = 1;
    public static String TBName = "Contact";
    public static String ColId = "_id";
    public static String ColPhone = "phone";
    public static String ColPoint = "point";
    public static String ColFeat = "feat";

    public static String create =
            "CREATE TABLE " + TBName + "(" + ColId + "integer primary key autoincrement, "
            + ColPhone + " text not null, "
            + ColPoint + " integer not null, "
            + ColFeat + "text not null);";

    public DBHelper(Context context){
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
