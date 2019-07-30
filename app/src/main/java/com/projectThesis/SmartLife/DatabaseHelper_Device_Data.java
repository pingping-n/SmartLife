package com.projectThesis.SmartLife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper_Device_Data extends SQLiteOpenHelper {

    private static final String DB_NAME = "myDevicesData";
    private static final String TABLE_NAME = "myDevicesData_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "id_device";
    private static final String COL3 = "type_data_v1";
    private static final String COL4 = "type_data_v2";
    private static final String COL5 = "type_data_v3";
    private static final String COL6 = "type_data_v4";
    private static final String COL7 = "type_data_v5";

    public DatabaseHelper_Device_Data(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT DEFAULT '0', " +
                COL3 + " TEXT DEFAULT '0', " +
                COL4 + " TEXT DEFAULT '0', " +
                COL5 + " TEXT DEFAULT '0', " +
                COL6 + " TEXT DEFAULT '0', " +
                COL7 + " TEXT DEFAULT '0')";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String id_device, String v1, String v2, String v3, String v4, String v5) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, id_device);
        contentValues.put(COL3, v1);
        contentValues.put(COL4, v2);
        contentValues.put(COL5, v3);
        contentValues.put(COL6, v4);
        contentValues.put(COL7, v5);

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

    public boolean updateName(String id_device, String v1, String v2, String v3, String v4, String v5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, id_device);
        contentValues.put(COL3, v1);
        contentValues.put(COL4, v2);
        contentValues.put(COL5, v3);
        contentValues.put(COL6, v4);
        contentValues.put(COL7, v5);

        long result = db.update(TABLE_NAME, contentValues, COL2 + " = ?", new String[]{id_device});
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    /**
     * Delete from database
     */
    public void deleteName(String id_device, String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL2 + " = '" + id_device + "'" +
                " AND " + COL4 + " = '" + title + "'";
        db.execSQL(query);
    }

    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    public String getTypeV1(String id_device){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + id_device +"'";
        Cursor data = db.rawQuery(query, null);
        String output = "0";
        if (data.moveToFirst()) {
            output = data.getString(data.getColumnIndex(COL3));
        }
        return output;
    }
    public String getTypeV2(String id_device){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL4 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + id_device +"'";
        Cursor data = db.rawQuery(query, null);
        String output = "0";
        if (data.moveToFirst()) {
            output = data.getString(data.getColumnIndex(COL4));
        }
        return output;
    }
    public String getTypeV3(String id_device){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL5 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + id_device +"'";
        Cursor data = db.rawQuery(query, null);
        String output = "0";
        if (data.moveToFirst()) {
            output = data.getString(data.getColumnIndex(COL5));
        }
        return output;
    }
    public String getTypeV4(String id_device){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL6 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + id_device +"'";
        Cursor data = db.rawQuery(query, null);
        String output = "0";
        if (data.moveToFirst()) {
            output = data.getString(data.getColumnIndex(COL6));
        }
        return output;
    }
    public String getTypeV5(String id_device){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL7 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + id_device +"'";
        Cursor data = db.rawQuery(query, null);
        String output = "0";
        if (data.moveToFirst()) {
            output = data.getString(data.getColumnIndex(COL7));
        }
        return output;
    }

    public boolean CheckIsDataAlreadyInDBorNot(String id_device) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL7 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + id_device +"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}
