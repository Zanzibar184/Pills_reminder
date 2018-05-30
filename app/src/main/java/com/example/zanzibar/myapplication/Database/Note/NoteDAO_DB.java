package com.example.zanzibar.myapplication.Database.Note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.zanzibar.myapplication.Database.Pills_reminder;
import com.example.zanzibar.myapplication.Database.QueryHelper;
import com.example.zanzibar.myapplication.Database.contatti.Contatti;

import java.util.ArrayList;
import java.util.List;

public class NoteDAO_DB implements  NoteDao{

    private SQLiteDatabase database;
    private QueryHelper query;
    private String[] allColumns =
            {
                    query.COLUMN_TITOLO,
                    query.COLUMN_TESTO,
                    query.COLUMN_DATA_NOTA,
                    query.COLUMN_ORA_NOTA,
                    query.COLUMN_TIPO_MEMO,
                    query.COLUMN_ID_MEMO,

            };

    @Override
    public void open() throws SQLException {

        if (query ==  null)
            query = new QueryHelper(Pills_reminder.getAppContext());
        database = query.getWritableDatabase();
    }

    @Override
    public void close() { query.close(); }

    private ContentValues NoteToValues(Nota nota){

        ContentValues values = new ContentValues();

        values.put(query.COLUMN_TITOLO, nota.getTitolo());
        values.put(query.COLUMN_TESTO, nota.getTesto());
        values.put(query.COLUMN_DATA_NOTA, nota.getData());
        values.put(query.COLUMN_ORA_NOTA, nota.getOra());
        values.put(query.COLUMN_TIPO_MEMO, nota.getTipo_memo());

        return values;

    }

    private Nota CursotToNota(Cursor cursor){
        String titolo = cursor.getString(0);
        String testo = cursor.getString(1);
        String data = cursor.getString(2);
        String ora = cursor.getString(3);
        int tipo_memo = cursor.getInt(4);
        int id_memo = cursor.getInt(5);

        return new Nota(titolo,testo,data,ora,tipo_memo,id_memo);
    }

    @Override
    public Nota insertNota(Nota nota) {
        database.insert(query.TABLE_NOTE,null,NoteToValues(nota));
        return nota;
    }

    @Override
    public void deleteNota(Nota nota) {

        int id = nota.getId_memo();

        database.delete(
                query.TABLE_NOTE,
                query.COLUMN_ID_MEMO + " =? ",
                new String[]{ ""+id}
        );

    }

    @Override
    public void updateNota(Nota nota) {

        database.update(query.TABLE_NOTE,NoteToValues(nota),"id_memo="+ nota.getId_memo(),null);

    }

    @Override
    public List<Nota> getAllNote() {
        List<Nota> note = new ArrayList<Nota>();
        Cursor cursor = database.query(
                query.TABLE_NOTE,
                allColumns,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Nota nota = CursotToNota(cursor);
            note.add(nota);
            cursor.moveToNext();
        }

        cursor.close();

        return note;

    }

    @Override
    public List<Nota> getNoteByDate(String date) {
        List<Nota> people = new ArrayList<Nota>();
        Cursor cursor = database.query(
                query.TABLE_NOTE,
                allColumns,
                "'"+date+ "'" + " = DATA",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Nota person = CursotToNota(cursor);
            people.add(person);
            cursor.moveToNext();
        }

        cursor.close();

        return people;
    }
}
