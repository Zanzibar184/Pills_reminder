package com.example.zanzibar.myapplication.Database.cure;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class QueryCure extends SQLiteOpenHelper {

    public static final String TABLE_CURE = "cure";
    public static final String COLUMN_NOME = "NOME";
    public static final String COLUMN_QTA_ASS = "QTA_ASS";
    public static final String COLUMN_SCORTA = "SCORTA";
    public static final String COLUMN_RIMANENZE = "RIMANENZE";
    public static final String COLUMN_INIZIO_CURA = "INIZIO_CURA";
    public static final String COLUMN_FINE_CURA = "FINE_CURA";
    public static final String COLUMN_TIPO_CURA = "TIPO_CURA";
    public static final String COLUMN_ORARIO_ASSUNZIONE = "ORARIO_ASSUNZIONE";
    public static final String DATABASE_NAME = "pills_reminder.db";

    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_CURE + "( "
            + COLUMN_NOME + " text primary key, "
            + COLUMN_QTA_ASS + " integer not null, "
            + COLUMN_SCORTA + " integer not null, "
            + COLUMN_RIMANENZE + " integer not null,"
            + COLUMN_INIZIO_CURA + " text not null,"
            + COLUMN_FINE_CURA + " text not null,"
            + COLUMN_ORARIO_ASSUNZIONE + " text not null,"
            + COLUMN_TIPO_CURA + " integer not null);" ;
    public QueryCure(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(QueryCure.class.getName(), "Upgrade database"+
                oldVersion + "to" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURE);
        onCreate(db);
    }
}