package edu.jsu.mcis.cs408.project2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydatabase.db";
    public static final String TABLE_WORDS = "word";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WORD_KEY = "word_key";


    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_WORDS_TABLE = "CREATE TABLE word (_id integer primary key autoincrement, word_key text)";
        sqLiteDatabase.execSQL(CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
        onCreate(sqLiteDatabase);
    }

    public void addKey(Word word){
        String key = word.getBox() + word.getDirection().toString();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORD_KEY, key);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_WORDS, null, values);
        db.close();
    }

    public String getKey(int id){
        String query = "SELECT * FROM " + TABLE_WORDS + " WHERE " + COLUMN_ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String k = null;

        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            String newKey = cursor.getString(1);
            cursor.close();
            k = newKey;
        }

        db.close();
        return k;
    }

    public String getAllKeys(){
        String query = "SELECT * FROM " + TABLE_WORDS;

        StringBuilder s = new StringBuilder();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(0);
                s.append(getKey(id)).append("\n");
            }
            while (cursor.moveToNext());
        }

        db.close();
        return s.toString();
    }

    public ArrayList<String> getAllKeysAsList(){
        String query = "SELECT * FROM " + TABLE_WORDS;

        ArrayList<String> allKeys = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            do{
                String newKey = cursor.getString(1);
                allKeys.add(newKey);

            }while (cursor.moveToNext());

        }
        db.close();
        return allKeys;
    }

    public void restartTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, null, null);
    }
}
