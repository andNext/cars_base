package com.example.prime.cars;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prime on 025 25.12.16.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "carsDb";
    public static final String TABLE_CARS = "cars";

    public static final String KEY_ID = "_id";
    public static final String KEY_MAKE = "make";
    public static final String KEY_MODEL = "model";
    public static final String KEY_LICENCE= "licence";
    public static final String KEY_DATE = "date";
    public static final String KEY_COLOR = "color";
    public static final String KEY_ENGINE = "engine";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CARS + "(" + KEY_ID + " integer primary key,"
                + KEY_MAKE + " text," + KEY_MODEL + " text," + KEY_LICENCE + " text,"
                + KEY_DATE + " text," + KEY_COLOR + " text," + KEY_ENGINE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CARS);

        onCreate(db);
    }
}
