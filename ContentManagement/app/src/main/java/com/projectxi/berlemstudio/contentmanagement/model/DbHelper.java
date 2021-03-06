package com.projectxi.berlemstudio.contentmanagement.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.projectxi.berlemstudio.contentmanagement.convertArrays;
import com.projectxi.berlemstudio.contentmanagement.res.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patawat on 2/9/2017 AD.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ Story_db.column.TABLE_NAME + " ("+Story_db.column._ID+ "  INTEGER PRIMARY KEY,"+
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
        Cursor cursor = db.query(Story_db.column.TABLE_NAME,null, Story_db.column.STORY_NAME+"=?",new String[]{name},null,null,null);
        if (cursor.getCount()!=0){
            Log.d("cursorNull", "insertStory: "+cursor);
            this.updateStory(name, des, Creator, Scene);
        }else{
            ContentValues values = new ContentValues();
            values.put(Story_db.column.STORY_NAME, name);
            values.put(Story_db.column.STORY_Description, des);
            values.put(Story_db.column.Created_by, Creator);
            values.put(Story_db.column.SCENE_LIST, Scene);

            long key = db.insert(Story_db.column.TABLE_NAME,null,values);
        }

    }

    public void updateStory(String name, String des, String Creator, String Scene){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Story_db.column.STORY_NAME, name);
        values.put(Story_db.column.STORY_Description, des);
        values.put(Story_db.column.Created_by, Creator);
        values.put(Story_db.column.SCENE_LIST, Scene);
        db.update(Story_db.column.TABLE_NAME,values,Story_db.column.STORY_NAME+" = ?", new String[] {name});
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
            convertArrays convertor = new convertArrays();
            String[] scene = convertor.convertStringToArray(cursor.getString(4));
//            Story newStory = new Story(id, name, des, creator, scene);
//            data.add(newStory);
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
    public void delete(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(Story_db.column.TABLE_NAME,"_id=?", new String[]{id});
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ Story_db.column.TABLE_NAME);
    }
}
