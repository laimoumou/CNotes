package com.example.laikaimin.cnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by laikaimin on 2016/5/21.
 */
public class NotesDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_NOTES = "create table Notes ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "content text,"
            + "time text)";

    private Context mContext;


    public NotesDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTES);
        Log.d("xys","NotesDBonCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
