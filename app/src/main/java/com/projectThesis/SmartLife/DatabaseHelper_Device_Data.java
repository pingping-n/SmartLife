package com.projectThesis.SmartLife;

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
}
