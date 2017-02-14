package com.projectxi.berlemstudio.contentmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.projectxi.berlemstudio.contentmanagement.res.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patawat on 2/9/2017 AD.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+Story_db.column.TABLE_NAME + " ("+Story_db.column._ID+ "  INTEGER PRIMARY KEY,"+
            Story_db.column.STORY_NAME + " TEXT,"+
            Story_db.column.STORY_Description + " TEXT,"+
            Story_db.column.Created_by + " TEXT,"+
            Story_db.column.SCENE_LIST + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Story_db.column.TABLE_NAME;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FeedReader.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertStory(String name, String des, String Creator, String Scene){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Story_db.column.STORY_NAME, name);
        values.put(Story_db.column.STORY_Description, des);
        values.put(Story_db.column.Created_by, Creator);
        values.put(Story_db.column.SCENE_LIST, Scene);

        long key = db.insert(Story_db.column.TABLE_NAME,null,values);
    }

    public List<Story> getStoryList(){
        SQLiteDatabase db = this.getReadableDatabase();

        List data = new ArrayList();
        Cursor cursor = db.query(Story_db.column.TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            long id = cursor.getLong(0);
            String name = cursor.getString(1);
            String des = cursor.getString(2);
            String creator = cursor.getString(3);
            Story newStory = new Story(id, name, des, creator);
            data.add(newStory);
        }

        db.close();
        return data;
    }

    public List<String> getStoryNameList(){
        SQLiteDatabase db = this.getReadableDatabase();

        List data = new ArrayList();
        Cursor cursor = db.query(Story_db.column.TABLE_NAME,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String name = cursor.getString(1);

            data.add(name);
        }

        db.close();
        return data;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Story_db.column.TABLE_NAME);
    }
}
