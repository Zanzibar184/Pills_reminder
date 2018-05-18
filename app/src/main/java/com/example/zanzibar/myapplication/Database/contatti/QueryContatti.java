package com.example.zanzibar.myapplication.Database.contatti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class QueryContatti extends SQLiteOpenHelper {

    public static final String TABLE_CONTATTI = "contatti";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_RUOLO = "ruoloa";
    public static final String COLUMN_NUMERO = "numero";
    public static final String DATABASE_NAME = "pills_reminder.db";

    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_CONTATTI + "( "
            + COLUMN_NOME + " text not null, "
            + COLUMN_RUOLO + " text not null,"
            + COLUMN_NUMERO + " text primary key);" ;
    public QueryContatti(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(QueryContatti.class.getName(), "Upgrade database"+
                oldVersion + "to" + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTATTI);
        onCreate(db);
    }
}