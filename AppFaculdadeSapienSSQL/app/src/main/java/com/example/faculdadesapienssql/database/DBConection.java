package com.example.faculdadesapienssql.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBConection extends SQLiteOpenHelper {

    public DBConection(@Nullable Context context) {
        super(context, DB.DATABASE_NAME, null, DB.VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating Database Tables
        db.execSQL(DB.UserTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
