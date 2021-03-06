package com.example.jerome.niche.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jerome on 9/10/2016.
 */

public class NicheSQLiteDb extends SQLiteOpenHelper {
    final static String DATABASE_NAME = "dbNiche";
    final static int DATABASE_VERSION = 1;
    final static String TABLE_NAME = "tbl_account";
    final static String COLUMN_USER_NAME = "_user_name";
    final static String COLUMN_USER_ID = "_user_Id";
    final static String COLUMN_PASSWORD = "_user_password";

    final static String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_USER_ID + " TEXT, "
            + COLUMN_PASSWORD + " TEXT)";

    public NicheSQLiteDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        db.execSQL(CREATE_TABLE);
    }

    public boolean insertAccount(Users aUsers){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, aUsers.getUser_name());
        cv.put(COLUMN_USER_ID, aUsers.getUser_ID());
        cv.put(COLUMN_PASSWORD, aUsers.getUser_password());

        db.insert(TABLE_NAME, null, cv);
        db.close();
        return true;
    }

    public boolean validateAccount(Users aUsers){
        boolean result = false;
        String username;
        String password;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select " + COLUMN_USER_NAME + ", " + COLUMN_PASSWORD
                + " from " + TABLE_NAME
                + " WHERE " + COLUMN_USER_NAME + " = '" + aUsers.getUser_name() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                username = cursor.getString(0);
                password = cursor.getString(1);
                Log.d("Username ", username);
                Log.d("Password ", password);
                Log.d("aUsers ", aUsers.getUser_name());
                Log.d("aPass ", aUsers.getUser_password());
                if((username.equals(aUsers.getUser_name())) && (password.equals(aUsers.getUser_password()))){
                    result = true;
                }
                else{
                    result = false;
                }
            }
        }
        return result;
    }

}
