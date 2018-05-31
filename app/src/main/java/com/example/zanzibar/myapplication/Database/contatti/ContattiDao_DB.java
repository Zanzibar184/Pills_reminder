package com.example.zanzibar.myapplication.Database.contatti;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.zanzibar.myapplication.Database.Pills_reminder;
import com.example.zanzibar.myapplication.Database.QueryHelper;

import java.util.ArrayList;
import java.util.List;

public class ContattiDao_DB implements ContattiDAO {

    private SQLiteDatabase database;
    private QueryHelper query;
    private String[] allColumns =
            {
                    query.COLUMN_NOME_CONTATTI,
                    query.COLUMN_RUOLO,
                    query.COLUMN_NUMERO,
                    query.COLUMN_FOTO,
                    query.COLUMN_IMPORTANTE,
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

    private ContentValues contattiToValues(Contatti contatti){

        ContentValues values = new ContentValues();

        values.put(query.COLUMN_NOME_CONTATTI, contatti.getNome());
        values.put(query.COLUMN_RUOLO, contatti.getRuolo());
        values.put(query.COLUMN_NUMERO, contatti.getNumero());
        values.put(query.COLUMN_FOTO_CONTATTO, contatti.getFoto());
        values.put(query.COLUMN_IMPORTANTE, contatti.getImportante());
        return values;
    }



    private Contatti cursorToContatti(Cursor cursor){

        String nome = cursor.getString(0);
        String cognome = cursor.getString(1);
        String numero = cursor.getString(2);
        String foto = cursor.getString(3);
        int importante = cursor.getInt(4);



        return  new Contatti(nome, cognome, numero, foto, importante);
    }

    @Override
    public Contatti insertContatto(Contatti contatti) {

        database.insert(query.TABLE_CONTATTI,null,contattiToValues(contatti));
        return contatti;
    }

    @Override
    public void deleteContatto(Contatti contatti) {

        String id = contatti.getNumero();

        database.delete(
                query.TABLE_CONTATTI,
                query.COLUMN_NUMERO + " =? ",
                new String[]{ ""+id}
        );

    }

    @Override
    public void updateContatto(Contatti contatti,  String numero) {
        database.update(query.TABLE_CONTATTI,contattiToValues(contatti),"numero="+numero,null);
    }

    @Override
    public List<Contatti> getAllContatti() {

        List<Contatti> contatti_list = new ArrayList<Contatti>();
        Cursor cursor = database.query(
                query.TABLE_CONTATTI,
                allColumns,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Contatti contatti1 = cursorToContatti(cursor);
            contatti_list.add(contatti1);
            cursor.moveToNext();
        }

        cursor.close();

        return contatti_list;

    }

    @Override
    public List<Contatti> getContattiImportanti() {

        List<Contatti> contatti_list = new ArrayList<Contatti>();
        Cursor cursor = database.query(
                query.TABLE_CONTATTI,
                allColumns,
                query.COLUMN_IMPORTANTE + "= 1",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Contatti contatti1 = cursorToContatti(cursor);
            contatti_list.add(contatti1);
            cursor.moveToNext();
        }

        cursor.close();

        return contatti_list;
    }


}
