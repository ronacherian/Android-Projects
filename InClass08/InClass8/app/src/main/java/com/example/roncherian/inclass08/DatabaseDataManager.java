package com.example.roncherian.inclass08;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by roncherian on 30/10/17.
 */

public class DatabaseDataManager {




    private Context mContext;
    private DatabaseOpenHelper databaseOpenHelper;
    private SQLiteDatabase db;
    private ItuneAppsDao ituneAppsDao;

    public DatabaseDataManager(Context context) {
        this.mContext = context;
        this.databaseOpenHelper = new DatabaseOpenHelper(this.mContext);
        this.db = this.databaseOpenHelper.getWritableDatabase();
        this.ituneAppsDao = new ItuneAppsDao(db);

    }

    public  void close(){
        if(db!=null){
            db.close();
        }

    }

    public ItuneAppsDao getItuneAppsDao(){
        return  this.ituneAppsDao;
    }

    public  long saveNote(ItunesApp note){

        return this.ituneAppsDao.save(note);
    }

    public boolean updateNote(ItunesApp note){

        return this.ituneAppsDao.update(note);
    }

    public boolean deleteNote(ItunesApp note){

        return  this.ituneAppsDao.delete(note);
    }

    public ItunesApp get(long id){
        return this.ituneAppsDao.get(id);
    }

    public List<ItunesApp> getAll(){
        return this.ituneAppsDao.getAll();
    }
}
