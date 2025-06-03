package com.example.final_project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

public class SQL_helper extends SQLiteOpenHelper {
    private final String DATABASE_NAME = "BOOKS";
    private final int DATABASE_VERSION = 1;
    SQL_helper(Context context) {
        super(context, "BOOKS", null, 1); }
    private static final String TABLE_NAME = "BOOK";
    String ID = "Id";
    private static String TITLE = "Title";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " TEXT"+")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addBook(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    @SuppressLint("Range")
    public LinkedList<Book> getAllBooks() {
        LinkedList<Book> List = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book(cursor.getString(cursor.getColumnIndex(TITLE)));

                List.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return List;
    }
    public void deleteBook(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, TITLE + "=?", new String[]{String.valueOf(title)});
        db.close();
    }
}


