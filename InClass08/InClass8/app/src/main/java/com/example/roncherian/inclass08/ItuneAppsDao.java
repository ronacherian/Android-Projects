package com.example.roncherian.inclass08;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roncherian on 30/10/17.
 */

public class ItuneAppsDao {


    private SQLiteDatabase db;

    public ItuneAppsDao(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(ItunesApp itunesApp){

        ContentValues values = new ContentValues();
        values.put(ItunesAppTable.COLUMN_PRICE, itunesApp.getAppPrice());
        values.put(ItunesAppTable.COLUMN_APP_NAME, itunesApp.getAppName());

        return db.insert(ItunesAppTable.TABLE_NAME,null,values);
    }

    public boolean update(ItunesApp note){

        ContentValues values = new ContentValues();
        values.put(ItunesAppTable.COLUMN_PRICE, note.getAppPrice());
        values.put(ItunesAppTable.COLUMN_APP_NAME, note.getAppName());

        return db.update(ItunesAppTable.TABLE_NAME,values,ItunesAppTable.COLUMN_ID + " = ?",new String[]{note.get_id()+""}) > 0;
    }

    public boolean delete(ItunesApp note){


        return db.delete(ItunesAppTable.TABLE_NAME,ItunesAppTable.COLUMN_ID + " = ?", new String[]{note.get_id()+""}) > 0;
    }

    public ItunesApp get(long id){

        ItunesApp note = null;
        Cursor cursor = db.query(true, ItunesAppTable.TABLE_NAME, new String[]{ItunesAppTable.COLUMN_ID,ItunesAppTable.COLUMN_PRICE,ItunesAppTable.COLUMN_APP_NAME}
                ,ItunesAppTable.COLUMN_ID+"=?",new String[]{id+""},null,null,null,null,null);

        if (cursor != null && cursor.moveToFirst()){
            note = buildNoteFromCursor(cursor);
            if (!cursor.isClosed()){
                cursor.close();
            }

        }
        return note;
    }

    public List<ItunesApp> getAll(){

        ArrayList<ItunesApp> notes = new ArrayList<ItunesApp>();
        Cursor cursor = db.query(ItunesAppTable.TABLE_NAME,new String[]{ItunesAppTable.COLUMN_ID,ItunesAppTable.COLUMN_PRICE,ItunesAppTable.COLUMN_APP_NAME},
                null,null,null,null,null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                ItunesApp note = buildNoteFromCursor(cursor);
                if (note != null){
                    notes.add(note);
                }

            }while (cursor.moveToNext());
            if (!cursor.isClosed()){
                cursor.close();
            }
            return notes;
        }
        return  null;
    }

    private ItunesApp buildNoteFromCursor(Cursor cursor){

        ItunesApp itunesApp = null;
        if (cursor != null){
            itunesApp = new ItunesApp();
            itunesApp.set_id(cursor.getLong(0));
            itunesApp.setAppPrice(cursor.getString(1));
            itunesApp.setAppName(cursor.getString(2));
        }

        return itunesApp;
    }
}
