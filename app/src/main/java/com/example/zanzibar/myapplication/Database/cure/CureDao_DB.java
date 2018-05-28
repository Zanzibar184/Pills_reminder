package com.example.zanzibar.myapplication.Database.cure;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.zanzibar.myapplication.Database.Pills_reminder;
import com.example.zanzibar.myapplication.Database.QueryHelper;

import java.util.ArrayList;
import java.util.List;

public class CureDao_DB implements CureDAO {

    private SQLiteDatabase database;
    private QueryHelper query;
    private String[] allColumns =
            {
                    query.COLUMN_NOME_CURE,
                    query.COLUMN_QTA_ASS,
                    query.COLUMN_SCORTA,
                    query.COLUMN_RIMANENZE,
                    query.COLUMN_INIZIO_CURA,
                    query.COLUMN_FINE_CURA,
                    query.COLUMN_TIPO_CURA,
                    query.COLUMN_ORARIO_ASSUNZIONE,
                    query.COLUMN_STATO_CURA,
                    query.COLUMN_ID,
                    query.COLUMN_FOTO,
                    query.COLUMN_UDM,
            };

    @Override
    public void open() throws SQLException {

        if (query ==  null)
            query = new QueryHelper(Pills_reminder.getAppContext());
        database = query.getWritableDatabase();
    }

    @Override
    public void close() {
        query.close();
    }

    private ContentValues curaToValues(Cura cura){

        ContentValues values = new ContentValues();

        values.put(query.COLUMN_NOME_CURE, cura.getNome());
        values.put(query.COLUMN_QTA_ASS, cura.getQuantità_assunzione());
        values.put(query.COLUMN_SCORTA, cura.getScorta());
        values.put(query.COLUMN_RIMANENZE, cura.getRimanenze());
        values.put(query.COLUMN_INIZIO_CURA, cura.getInizio_cura());
        values.put(query.COLUMN_FINE_CURA, cura.getFine_cura());
        values.put(query.COLUMN_TIPO_CURA, cura.getTipo_cura());
        values.put(query.COLUMN_ORARIO_ASSUNZIONE, cura.getOrario_assunzione());
        values.put(query.COLUMN_STATO_CURA, cura.getStato_cura());
        values.put(query.COLUMN_FOTO, cura.getFoto());
        values.put(query.COLUMN_UDM, cura.getUnità_misura());
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
        String stato_cura = cursor.getString(8);
        int id = cursor.getInt(9);
        String foto = cursor.getString(10);
        String udm = cursor.getString(11);

        return  new Cura(nome, quantità_assunzione, scorta, rimanenze,inizio_cura,fine_cura, tipo_cura, orario_assunzione, stato_cura, id, foto, udm);
    }

    @Override
    public Cura insertCura(Cura cura) {

        database.insert(query.TABLE_CURE,null,curaToValues(cura));
        return cura;
    }

    @Override
    public void deleteCura(Cura cura) {

         int id = cura.getId();

        database.delete(
                query.TABLE_CURE,
                query.COLUMN_ID + " =? ",
                new String[]{ ""+id}
        );

    }

    @Override
    public void updateCura(Cura cura) {
        ContentValues values = new ContentValues();

        values.put(query.COLUMN_NOME_CURE, cura.getNome());
        values.put(query.COLUMN_QTA_ASS, cura.getQuantità_assunzione());
        values.put(query.COLUMN_SCORTA, cura.getScorta());
        values.put(query.COLUMN_RIMANENZE, cura.getRimanenze());
        values.put(query.COLUMN_INIZIO_CURA, cura.getInizio_cura());
        values.put(query.COLUMN_FINE_CURA, cura.getFine_cura());
        values.put(query.COLUMN_TIPO_CURA, cura.getTipo_cura());
        values.put(query.COLUMN_ORARIO_ASSUNZIONE, cura.getOrario_assunzione());
        values.put(query.COLUMN_STATO_CURA, cura.getStato_cura());
        values.put(query.COLUMN_FOTO, cura.getFoto());
        values.put(query.COLUMN_UDM, cura.getUnità_misura());

        database.update(query.TABLE_CURE,values,"id="+cura.getId(),null);
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

    }

    public List<Cura> getCureByDate(String date) {

        List<Cura> people = new ArrayList<Cura>();
        Cursor cursor = database.query(
                query.TABLE_CURE,
                allColumns,
                "'"+date+ "'" + " between INIZIO_CURA and FINE_CURA",
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
