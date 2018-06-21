package com.example.zanzibar.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class QueryHelper extends SQLiteOpenHelper {

    //-----CURE
    public static final String TABLE_CURE = "cure";
    public static final String COLUMN_NOME_CURE = "NOME";
    public static final String COLUMN_QTA_ASS = "QTA_ASS";
    public static final String COLUMN_SCORTA = "SCORTA";
    public static final String COLUMN_RIMANENZE = "RIMANENZE";
    public static final String COLUMN_INIZIO_CURA = "INIZIO_CURA";
    public static final String COLUMN_FINE_CURA = "FINE_CURA";
    public static final String COLUMN_TIPO_CURA = "TIPO_CURA";
    public static final String COLUMN_ORARIO_ASSUNZIONE = "ORARIO_ASSUNZIONE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FOTO = "FOTO";
    public static final String COLUMN_UDM = "UNITA_MISURA";
    public static final String COLUMN_CURA_IMPORTANTE = "CURA_IMPORTANTE";
    public static final String COLUMN_RIPETIZIONE = "RIPETIZIONE";

    //------DOSI
    public static final String TABLE_DOSI = "dosi";
    public static final String COLUMN_ID_DOSI = "ID";
    public static final String COLUMN_GIORNO = "GIORNO";
    public static final String COLUMN_STATO = "STATO";

    //-----CONTATTI
    public static final String TABLE_CONTATTI = "contatti";
    public static final String COLUMN_NOME_CONTATTI = "NOME";
    public static final String COLUMN_RUOLO = "RUOLO";
    public static final String COLUMN_NUMERO = "NUMERO";
    public static final String COLUMN_FOTO_CONTATTO = "FOTO";
    public static final String COLUMN_IMPORTANTE = "CONTATTO_IMPORTANTE";

    //-----NOTE
    public static final String TABLE_NOTE = "note";
    public static final String COLUMN_TITOLO = "TITOLO";
    public static final String COLUMN_TESTO = "TESTO";
    public static final String COLUMN_DATA_NOTA = "DATA";
    public static final String COLUMN_ORA_NOTA = "ORA";
    public static final String COLUMN_TIPO_MEMO = "TIPO_MEMO";
    public static final String COLUMN_ID_MEMO = "ID_MEMO";

    public static final String DATABASE_NAME = "pills_reminder.db";
    public static int DATABASE_VERSION = 6;


    private static final String DATABASE_CREATE_CONTATTI = "create table "
            + TABLE_CONTATTI + "( "
            + COLUMN_NOME_CONTATTI + " text not null, "
            + COLUMN_RUOLO + " text,"
            + COLUMN_NUMERO + " text primary key,"
            + COLUMN_FOTO_CONTATTO + " text,"
            + COLUMN_IMPORTANTE + " integer not null);" ;

    private static final String DATABASE_CREATE_DOSI = "create table "
            + TABLE_DOSI + "( "
            + COLUMN_ID_DOSI + " integer not null, "
            + COLUMN_GIORNO + " text,"
            + COLUMN_STATO + " text);" ;

    private static final String DATABASE_CREATE_NOTE = "create table "
            + TABLE_NOTE + "( "
            + COLUMN_TITOLO + " text, "
            + COLUMN_TESTO + " text not null,"
            + COLUMN_DATA_NOTA + " text,"
            + COLUMN_ORA_NOTA + " text,"
            + COLUMN_TIPO_MEMO + " integer not null,"
            + COLUMN_ID_MEMO + " integer primary key autoincrement);" ;


    private static final String DATABASE_CREATE_CURE = "create table "
            + TABLE_CURE + "( "
            + COLUMN_NOME_CURE + " text not null, "
            + COLUMN_QTA_ASS + " integer not null, "
            + COLUMN_SCORTA + " integer, "
            + COLUMN_RIMANENZE + " integer,"
            + COLUMN_INIZIO_CURA + " text not null,"
            + COLUMN_FINE_CURA + " text not null,"
            + COLUMN_ORARIO_ASSUNZIONE + " text not null,"
            + COLUMN_TIPO_CURA + " integer not null,"
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_UDM + " text,"
            + COLUMN_FOTO + " text,"
            + COLUMN_CURA_IMPORTANTE + " integer not null,"
            + COLUMN_RIPETIZIONE+ " string not null);" ;

    public QueryHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE_CONTATTI);
        database.execSQL(DATABASE_CREATE_CURE);
        database.execSQL(DATABASE_CREATE_NOTE);
        database.execSQL(DATABASE_CREATE_DOSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTATTI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOSI);
        onCreate(db);
    }
}