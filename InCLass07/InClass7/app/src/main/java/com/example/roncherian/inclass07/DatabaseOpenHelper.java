package com.example.roncherian.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roncherian on 23/10/17.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "mynotes.db";
    static final int DB_VERSION = 2;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION );

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        NotesTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        NotesTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
