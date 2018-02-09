package com.example.roncherian.inclass08;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by roncherian on 30/10/17.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME = "mynotes.db";
    static final int DB_VERSION = 2;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION );

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        ItunesAppTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        ItunesAppTable.onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
