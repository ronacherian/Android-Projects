package com.example.roncherian.inclass07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by roncherian on 23/10/17.
 */

public class DatabaseDataManager {


    private Context mContext;
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;
    private NoteDao noteDao;

    public DatabaseDataManager(Context context) {
        this.mContext = context;
        this.databaseOpenHelper = new DatabaseOpenHelper(this.mContext);
        this.db = this.databaseOpenHelper.getWritableDatabase();
        this.noteDao = new NoteDao(db);

    }

    public  void close(){
        if(db!=null){
            db.close();
        }

    }

    public NoteDao getNoteDao(){
        return  this.noteDao;
    }

    public  long saveNote(Note note){

        return this.noteDao.save(note);
    }

    public boolean updateNote(Note note){

        return this.noteDao.update(note);
    }

    public boolean deleteNote(Note note){

        return  this.noteDao.delete(note);
    }

    public Note get(long id){
        return this.noteDao.get(id);
    }

    public List<Note> getAll(){
        return this.noteDao.getAll();
    }
}
