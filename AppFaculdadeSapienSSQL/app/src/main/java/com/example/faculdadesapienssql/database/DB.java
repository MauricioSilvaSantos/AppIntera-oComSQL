package com.example.faculdadesapienssql.database;

import android.provider.BaseColumns;

public final class DB {

    public static final String DATABASE_NAME = "MyAppDb.db";
    public static final int VERSION = 1;
    private static final String COMMA_SEP = ",";

    //To prevent Instatiation
    private DB() {
    }
    //Inner Class that defines table contents
    public static class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String _ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RA = "ra";
        public static final String COLUMN_CURSO = "curso";
        public static final String COLUMN_IMAGE = "image";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                        " ("+_ID+" integer primary key autoincrement"+COMMA_SEP+
                        COLUMN_NAME+" varchar(50)"+COMMA_SEP+
                        COLUMN_RA+" varchar(10)"+COMMA_SEP+
                        COLUMN_CURSO+" vachar(50)"+COMMA_SEP+
                        COLUMN_IMAGE+" blob"+")";
    }

}//end-contract-class
