package com.example.zanzibar.myapplication.Database.cure;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.zanzibar.myapplication.Database.Pills_reminder;

import java.util.ArrayList;
import java.util.List;

public class CureDao_DB implements CureDAO {

    private SQLiteDatabase database;
    private QueryCure query;
    private String[] allColumns =
            {
                    query.COLUMN_NOME,
                    query.COLUMN_QTA_ASS,
                    query.COLUMN_SCORTA,
                    query.COLUMN_RIMANENZE,
                    query.COLUMN_INIZIO_CURA,
                    query.COLUMN_FINE_CURA,
                    query.COLUMN_TIPO_CURA,
                    query.COLUMN_ORARIO_ASSUNZIONE,
            };

    @Override
    public void open() throws SQLException {

        if (query ==  null)
            query = new QueryCure(Pills_reminder.getAppContext());
        database = query.getWritableDatabase();
    }

    @Override
    public void close() {
        query.close();
    }

    private ContentValues curaToValues(Cura cura){

        ContentValues values = new ContentValues();

        values.put(query.COLUMN_NOME, cura.getNome());
        values.put(query.COLUMN_QTA_ASS, cura.getQuantità_assunzione());
        values.put(query.COLUMN_SCORTA, cura.getScorta());
        values.put(query.COLUMN_RIMANENZE, cura.getRimanenze());
        values.put(query.COLUMN_INIZIO_CURA, cura.getInizio_cura());
        values.put(query.COLUMN_FINE_CURA, cura.getFine_cura());
        values.put(query.COLUMN_TIPO_CURA, cura.getTipo_cura());
        values.put(query.COLUMN_ORARIO_ASSUNZIONE, cura.getOrario_assunzione());
        return values;
    }



    private Cura cursorToCura(Cursor cursor){

        String nome = cursor.getString(0);
        int quantità_assunzione = cursor.getInt(1);
        int scorta = cursor.getInt(2);
        int rimanenze = cursor.getInt(3);
        String inizio_cura = cursor.getString(4);
        String fine_cura = cursor.getString(5);
        int tipo_cura = cursor.getInt(6);
        String orario_assunzione = cursor.getString(7);

        return  new Cura(nome, quantità_assunzione, scorta, rimanenze,inizio_cura,fine_cura, tipo_cura, orario_assunzione);
    }

    @Override
    public Cura insertCura(Cura cura) {

        database.insert(query.TABLE_CURE,null,curaToValues(cura));
        return cura;
    }

    @Override
    public void deleteCura(Cura cura) {

        String id = cura.getNome();

        database.delete(
                query.TABLE_CURE,
                query.COLUMN_NOME + " =? ",
                new String[]{ ""+id}
        );

    }

    @Override
    public List<Cura> getAllCure() {

        List<Cura> people = new ArrayList<Cura>();
        Cursor cursor = database.query(
                query.TABLE_CURE,
                allColumns,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Cura person = cursorToCura(cursor);
            people.add(person);
            cursor.moveToNext();
        }

        cursor.close();

        return people;

    }    public List<Cura> getTodayCure() {

        List<Cura> people = new ArrayList<Cura>();
        Cursor cursor = database.query(
                query.TABLE_CURE,
                allColumns,
                "date('now') between INIZIO_CURA and FINE_CURA",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Cura person = cursorToCura(cursor);
            people.add(person);
            cursor.moveToNext();
        }

        cursor.close();

        return people;

    }


}
