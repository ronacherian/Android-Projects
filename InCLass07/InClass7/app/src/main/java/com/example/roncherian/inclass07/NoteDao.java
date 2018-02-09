package com.example.roncherian.inclass07;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roncherian on 23/10/17.
 */

public class NoteDao {


    private SQLiteDatabase db;

    public NoteDao(SQLiteDatabase db) {
        this.db = db;
    }

    public long save(Note note){

        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesTable.COLUMN_TEXT, note.getText());

        return db.insert(NotesTable.TABLE_NAME,null,values);
    }

    public boolean update(Note note){

        ContentValues values = new ContentValues();
        values.put(NotesTable.COLUMN_SUBJECT, note.getSubject());
        values.put(NotesTable.COLUMN_TEXT, note.getText());

        return db.update(NotesTable.TABLE_NAME,values,NotesTable.COLUMN_ID + " = ?",new String[]{note.get_id()+""}) > 0;
    }

    public boolean delete(Note note){


        return db.delete(NotesTable.TABLE_NAME,NotesTable.COLUMN_ID + " = ?", new String[]{note.get_id()+""}) > 0;
    }

    public Note get(long id){

        Note note = null;
        Cursor cursor = db.query(true, NotesTable.TABLE_NAME, new String[]{NotesTable.COLUMN_ID,NotesTable.COLUMN_SUBJECT,NotesTable.COLUMN_TEXT}
                ,NotesTable.COLUMN_ID+"=?",new String[]{id+""},null,null,null,null,null);

        if (cursor != null && cursor.moveToFirst()){
            note = buildNoteFromCursor(cursor);
            if (!cursor.isClosed()){
                cursor.close();
            }

        }
        return note;
    }

    public List<Note> getAll(){

        ArrayList<Note> notes = new ArrayList<Note>();
        Cursor cursor = db.query(NotesTable.TABLE_NAME,new String[]{NotesTable.COLUMN_ID,NotesTable.COLUMN_SUBJECT,NotesTable.COLUMN_TEXT},
                null,null,null,null,null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                Note note = buildNoteFromCursor(cursor);
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

    private Note buildNoteFromCursor(Cursor cursor){

        Note note = null;
        if (cursor != null){
            note = new Note();
            note.set_id(cursor.getLong(0));
            note.setSubject(cursor.getString(1));
            note.setText(cursor.getString(2));
        }

        return note;
    }
}
