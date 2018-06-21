package com.example.zanzibar.myapplication.Database.cure;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.zanzibar.myapplication.Database.Pills_reminder;
import com.example.zanzibar.myapplication.Database.QueryHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.zanzibar.myapplication.frames.AggiungiPillola.printDifference;

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
                    query.COLUMN_ID,
                    query.COLUMN_FOTO,
                    query.COLUMN_UDM,
                    query.COLUMN_CURA_IMPORTANTE,
                    query.COLUMN_RIPETIZIONE,
            };
    private String[] dosicolumns =
            {
                    query.COLUMN_ID_DOSI,
                    query.COLUMN_GIORNO,
                    query.COLUMN_STATO,

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
        values.put(query.COLUMN_FOTO, cura.getFoto());
        values.put(query.COLUMN_UDM, cura.getUnità_misura());
        values.put(query.COLUMN_CURA_IMPORTANTE, cura.getImportante());
        values.put(query.COLUMN_RIPETIZIONE, cura.getRipetizione());
        return values;
    }
    private ContentValues dosiToValues(Dosi dosi){

        ContentValues values = new ContentValues();

        values.put(query.COLUMN_ID_DOSI, dosi.getId());
        values.put(query.COLUMN_GIORNO, dosi.getGiorno());
        values.put(query.COLUMN_STATO, dosi.getStato_cura());

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
        int id = cursor.getInt(8);
        String foto = cursor.getString(9);
        String udm = cursor.getString(10);
        int importante = cursor.getInt(11);
        String ripetizione = cursor.getString(12);

        return  new Cura(nome, quantità_assunzione, scorta, rimanenze,inizio_cura,fine_cura, tipo_cura, orario_assunzione, id, foto, udm, importante, ripetizione);
    }
    private Dosi cursorToDose(Cursor cursor){
        int id = cursor.getInt(0);
        String giorno = cursor.getString(1);
        String stato_cura = cursor.getString(2);


        return  new Dosi(id,giorno,stato_cura);
    }

    @Override
    public Cura insertCura(Cura cura) {

        database.insert(query.TABLE_CURE,null,curaToValues(cura));
        insertDosi(cura);


        return cura;
    }

    @Override
    public Cura insertCura(Cura cura,  List<String> DaysAllowed) {

        database.insert(query.TABLE_CURE,null,curaToValues(cura));
        insertDosi(cura, DaysAllowed);


        return cura;
    }
    @Override
    public Cura insertCura(Cura cura, int repetition) {

        database.insert(query.TABLE_CURE,null,curaToValues(cura));
        insertDosi(cura, repetition);


        return cura;
    }

    private void insertDosi(Cura cura_without_id){

        Cura cura_get_id = findCura(cura_without_id.getNome(),cura_without_id.getInizio_cura(),cura_without_id.getFine_cura(),cura_without_id.getOrario_assunzione());

        int diff_giorni = (int) printDifference(StringtoDate(cura_get_id.getInizio_cura()),StringtoDate(cura_get_id.getFine_cura()));

        for(int i = 0; i<=diff_giorni;i++){

            Date inizio_cura = StringtoDate(cura_get_id.getInizio_cura());
            Calendar c = Calendar.getInstance();
            c.setTime(inizio_cura);
            c.add(Calendar.DATE, i);
            inizio_cura = c.getTime();

            String giorno = DateToString(inizio_cura);

            database.insert(query.TABLE_DOSI,null,dosiToValues(new Dosi(cura_get_id.getId(),giorno,Dosi.DA_ASSUMERE)));
        }
    }
    private void insertDosi(Cura cura_without_id, List<String> DaysAllowed){

        Cura cura_get_id = findCura(cura_without_id.getNome(),cura_without_id.getInizio_cura(),cura_without_id.getFine_cura(),cura_without_id.getOrario_assunzione());

        int diff_giorni = (int) printDifference(StringtoDate(cura_get_id.getInizio_cura()),StringtoDate(cura_get_id.getFine_cura()));

        for(int i = 0; i<=diff_giorni;i++){

            Date scroll_date = StringtoDate(cura_get_id.getInizio_cura());
            Calendar c = Calendar.getInstance();
            c.setTime(scroll_date);
            c.add(Calendar.DATE, i);
            scroll_date = c.getTime();

            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            String allowed = outFormat.format(scroll_date);
            //Log.i("lista", DaysAllowed + "");
            //Log.i("giorno controllato", allowed + "");
            String giorno = DateToString(scroll_date);

            if( DaysAllowed.contains(allowed)) {
                //Log.i("giorno ", allowed);
                database.insert(query.TABLE_DOSI, null, dosiToValues(new Dosi(cura_get_id.getId(), giorno, Dosi.DA_ASSUMERE)));
            }
        }
    }


    private void insertDosi(Cura cura_without_id, int repetition){

        Cura cura_get_id = findCura(cura_without_id.getNome(),cura_without_id.getInizio_cura(),cura_without_id.getFine_cura(),cura_without_id.getOrario_assunzione());

        int diff_giorni = (int) printDifference(StringtoDate(cura_get_id.getInizio_cura()),StringtoDate(cura_get_id.getFine_cura()));

        int i = 0;
        while(i<=diff_giorni){

            Date inizio_cura = StringtoDate(cura_get_id.getInizio_cura());
            Calendar c = Calendar.getInstance();
            c.setTime(inizio_cura);
            c.add(Calendar.DATE, i);
            inizio_cura = c.getTime();

            String giorno = DateToString(inizio_cura);

            database.insert(query.TABLE_DOSI,null,dosiToValues(new Dosi(cura_get_id.getId(),giorno,Dosi.DA_ASSUMERE)));
            i += repetition;
        }
    }

    @Override
    public void deleteCura(Cura cura) {

         int id = cura.getId();

        database.delete(
                query.TABLE_CURE,
                query.COLUMN_ID + " =? ",
                new String[]{ ""+id}
        );
        database.delete(
                query.TABLE_DOSI,
                query.COLUMN_ID_DOSI + " =? ",
                new String[]{ ""+id}
        );




    }
    @Override
    public void reinitDose(Cura cura) {

        int id = cura.getId();

        database.delete(
                query.TABLE_DOSI,
                query.COLUMN_ID_DOSI + " =? ",
                new String[]{ ""+id}
        );

        int diff_giorni = (int) printDifference(StringtoDate(cura.getInizio_cura()),StringtoDate(cura.getFine_cura()));

        int i = 0;
        while(i<=diff_giorni){

            if(cura.getRipetizione().length() < 3){
                Date inizio_cura = StringtoDate(cura.getInizio_cura());
                Calendar c = Calendar.getInstance();
                c.setTime(inizio_cura);
                c.add(Calendar.DATE, i);
                inizio_cura = c.getTime();

                String giorno = DateToString(inizio_cura);

                database.insert(query.TABLE_DOSI,null,dosiToValues(new Dosi(cura.getId(),giorno,Dosi.DA_ASSUMERE)));
                i = i + Integer.parseInt(cura.getRipetizione());
            }
            else {
                List<String> DaysAllowed = Cura.reverseRipetizione(cura.getRipetizione());

                Date scroll_date = StringtoDate(cura.getInizio_cura());
                Calendar c = Calendar.getInstance();
                c.setTime(scroll_date);
                c.add(Calendar.DATE, i);
                scroll_date = c.getTime();

                SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
                String allowed = outFormat.format(scroll_date);
                //Log.i("lista", DaysAllowed + "");
                //Log.i("giorno controllato", allowed + "");
                String giorno = DateToString(scroll_date);

                if( DaysAllowed.contains(allowed)) {
                    //Log.i("giorno ", allowed);
                    database.insert(query.TABLE_DOSI, null, dosiToValues(new Dosi(cura.getId(), giorno, Dosi.DA_ASSUMERE)));
                }

                i++;
            }



        }




    }

    @Override
    public void updateCura(Cura cura) {
                database.update(query.TABLE_CURE,curaToValues(cura),"id="+cura.getId(),null);
    }

    @Override
    public List<Cura> getAllCure() {

        List<Cura> cure = new ArrayList<Cura>();
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
            cure.add(person);
            cursor.moveToNext();
        }

        cursor.close();

        return cure;

    }


    @Override
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
    @Override
    public List<Cura> getCureByRange(String date) {

        List<Cura> people = new ArrayList<Cura>();
        Cursor cursor = database.query(
                query.TABLE_CURE,
                allColumns,
                "'"+date+ "'" + " <= INIZIO_CURA",
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

    @Override
    public Cura findCura(String nome, String inizio_cura, String fine_cura, String orario) {
        List<Cura> people = new ArrayList<Cura>();
        Cursor cursor = database.query(
                query.TABLE_CURE,
                allColumns,
                "NOME = '"+nome +"' and INIZIO_CURA = '" + inizio_cura + "' and FINE_CURA = '" + fine_cura + "' and ORARIO_ASSUNZIONE = '"+ orario + "'",
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

        return  people.get(0);


    }

    @Override
    public List<Dosi> getAllDosi() {

        List<Dosi> lista_dosi = new ArrayList<Dosi>();
        Cursor cursor = database.query(
                query.TABLE_DOSI,
                dosicolumns,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Dosi dose = cursorToDose(cursor);
            lista_dosi.add(dose);
            cursor.moveToNext();
        }

        cursor.close();

        return lista_dosi;

    }

    @Override
    public List<Dosi> getDosiByDate(String date) {

        List<Dosi> list_dosi = new ArrayList<Dosi>();
        Cursor cursor = database.query(
                query.TABLE_DOSI,
                dosicolumns,
                "'"+date+ "'" + " = GIORNO",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Dosi dose = cursorToDose(cursor);
            list_dosi.add(dose);
            cursor.moveToNext();
        }

        cursor.close();

        return list_dosi;

    }
    @Override
    public List<Dosi> getDosiById(int id) {

        List<Dosi> list_dosi = new ArrayList<Dosi>();
        Cursor cursor = database.query(
                query.TABLE_DOSI,
                dosicolumns,
                ""+id+ "" + " = id",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Dosi dose = cursorToDose(cursor);
            list_dosi.add(dose);
            cursor.moveToNext();
        }

        cursor.close();

        return list_dosi;

    }

    @Override
    public void updateDose(Dosi dose) {
        database.update(query.TABLE_DOSI,dosiToValues(dose),"id="+dose.getId()+" and giorno='"+dose.getGiorno()+"'",null);
    }

    private Date StringtoDate(String data){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date converted = new Date();
        try {
            converted = dateFormat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return  converted;

    }

    private String DateToString(Date data){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(data);
        return strDate;

    }


}
