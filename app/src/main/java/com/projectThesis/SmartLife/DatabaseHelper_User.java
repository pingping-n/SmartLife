package com.projectThesis.SmartLife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper_User extends SQLiteOpenHelper {

    private static final String DB_NAME = "myUser";
    private static final String TABLE_NAME = "myUser_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "username";
    private static final String COL3 = "password";

    public DatabaseHelper_User(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +
                COL3 + " TEXT)";
        db.execSQL(createTable);
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, username);
        contentValues.put(COL3, password);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public void deleteUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL2 + " = '" + username + "'" +
                " AND " + COL3 + " = '" + password + "'";
        db.execSQL(query);
    }

    public boolean updateUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, username);
        contentValues.put(COL3, password);

        long result = db.update(TABLE_NAME, contentValues, COL2 + " = ?", new String[]{username});

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public String getUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2 + " FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        String output = "";
        if (data.moveToFirst()) {
            output = data.getString(data.getColumnIndex(COL2));
        }
        return output;
    }
    public String getPassword(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        String output = "";
        if (data.moveToFirst()) {
            output = data.getString(data.getColumnIndex(COL3));
        }
        return output;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
