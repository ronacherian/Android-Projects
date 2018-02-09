package com.example.roncherian.inclass08;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by roncherian on 30/10/17.
 */

public class ItunesAppTable {

    static final String TABLE_NAME = "Filter";
    static final String COLUMN_ID = "id";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_APP_NAME = "app_name";

    static public void onCreate(SQLiteDatabase db){

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(TABLE_NAME).append(" ( ");
        sb.append(COLUMN_ID).append(" integer primary key autoincrement, ");
        sb.append(COLUMN_PRICE).append(" text not null, ");
        sb.append(COLUMN_APP_NAME).append(" text not null ); ");
        Log.d("demo: On Create Query",sb.toString());
        try{
            db.execSQL(sb.toString());
        } catch (SQLiteException e){
            Log.d("demo","Exception in creating database");
            e.printStackTrace();
        }
    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        try {
            db.execSQL("DROP TABLE "+TABLE_NAME+" if exists;");
        } catch (Exception e){
            Log.d("demo","Exception on upgrading  database");
            e.printStackTrace();
        }

        ItunesAppTable.onCreate(db);
    }
}
