package com.example.faculdadesapienssql.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DBConection conection;
    private SQLiteDatabase database;

    public UserDAO(Context context) {
        this.conection = new DBConection(context);
        this.database = conection.getWritableDatabase();
    }

    public long Insert(User usr) {
        //Creating Content Values Map
        ContentValues values = new ContentValues();
        values.put(DB.UserTable.COLUMN_NAME, usr.getName());
        values.put(DB.UserTable.COLUMN_RA, usr.getRa());
        values.put(DB.UserTable.COLUMN_CURSO, usr.getCurso());
        values.put(DB.UserTable.COLUMN_IMAGE, usr.getImage());

        //Inserting Values on Table
        return database.insert("users", null, values);
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String[] projection = {
                DB.UserTable._ID, //i=0
                DB.UserTable.COLUMN_NAME,//i=1
                DB.UserTable.COLUMN_RA, //i=2
                DB.UserTable.COLUMN_CURSO,//i=3
                DB.UserTable.COLUMN_IMAGE


        };
        Cursor cursor = database.query(DB.UserTable.TABLE_NAME, projection,
                null, null, null, null, null);
        //List Filling
        while (cursor.moveToNext()) {
            User usr = new User();
            usr.setId(cursor.getInt(0));
            usr.setName(cursor.getString(cursor.getColumnIndex(DB.UserTable.COLUMN_NAME)));
            usr.setRa(cursor.getString(cursor.getColumnIndex(DB.UserTable.COLUMN_RA)));
            usr.setCurso(cursor.getString(cursor.getColumnIndex(DB.UserTable.COLUMN_CURSO)));
            usr.setImage(cursor.getBlob(cursor.getColumnIndex(DB.UserTable.COLUMN_IMAGE)));

            //List Add
            users.add(usr);
        }
        //Closing Cursor
        cursor.close();
        return users;
    }


    public int Delete(Integer id) {
        //Defing Sql
        String selection = DB.UserTable._ID + " LIKE ?";
        //Defing Argument
        String[] selectionAgrs = {id.toString()};
        //Execultando a consulta de deletamento do Database
        return database.delete(DB.UserTable.TABLE_NAME, selection, selectionAgrs);
    }
    public long update (User user){
        //Creating map values from object
        ContentValues values = new ContentValues();
        values.put(DB.UserTable.COLUMN_NAME, user.getName());
        values.put(DB.UserTable.COLUMN_RA, user.getRa());
        values.put(DB.UserTable.COLUMN_CURSO, user.getCurso());
        values.put(DB.UserTable.COLUMN_IMAGE, user.getImage());

        //Creating Selection e SelectionArgs
        String selection = DB.UserTable._ID + " LIKE ?";
        String[] seçectionArgs = {user.getId().toString()};
        //Update on DataBase
        return database.update(DB.UserTable.TABLE_NAME,
                values, selection, seçectionArgs);

    }


}
