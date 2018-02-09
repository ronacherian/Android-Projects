package com.example.roncherian.inclass07;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by roncherian on 23/10/17.
 */

public class NotesTable {

    static final String TABLE_NAME = "notes";
    static final String COLUMN_ID = "id";
    static final String COLUMN_SUBJECT = "subject";
    static final String COLUMN_TEXT = "text";

    static public void onCreate(SQLiteDatabase db){

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(TABLE_NAME).append(" ( ");
        sb.append(COLUMN_ID).append(" integer primary key autoincrement, ");
        sb.append(COLUMN_SUBJECT).append(" text not null, ");
        sb.append(COLUMN_TEXT).append(" text not null ); ");
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

        NotesTable.onCreate(db);
    }
}
