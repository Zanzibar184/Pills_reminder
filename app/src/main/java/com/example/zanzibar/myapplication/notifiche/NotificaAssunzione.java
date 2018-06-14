package com.example.zanzibar.myapplication.notifiche;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.frames.Cure;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.zanzibar.myapplication.frames.AggiungiPillola.printDifference;

public class NotificaAssunzione extends AppCompatActivity {

    private CureDAO dao;
    private List<Cura> list_cure;
    private Cura cura;
    private Cura cura_final;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_gestione_farmaci);

        dao = new CureDao_DB();


        Button conferma = (Button) findViewById(R.id.btn_conferma);
        Button non_assunto = (Button) findViewById(R.id.btn_non_assunto);
        Button rimanda = (Button) findViewById(R.id.btn_rimanda);

        Intent i = getIntent();
        final String m = i.getStringExtra("stringa_cura");

        cura = Cura.toCura(m);
        dao.open();
        cura_final = dao.findCura(cura.getNome(),cura.getInizio_cura(),cura.getFine_cura(),cura.getOrario_assunzione());
        dao.close();


        View.OnClickListener gestisci_conferma = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                boolean assumi_farmaco_notifica = prefs.getBoolean("imposta_notifiche_farmaci",false);
                String scorte_pillole = prefs.getString("pillole_scorta","");
                if(v.getId()==R.id.btn_conferma) {
                    cura_final.setStato_cura(Cura.ASSUNTA);
                    //cura_final.setRimanenze(cura_final.getRimanenze() - cura_final.getQuantità_assunzione());
                    dao.open();
                    dao.updateCura(cura_final);
                    dao.close();
                } else if (v.getId()==R.id.btn_non_assunto) {
                    cura_final.setStato_cura(Cura.NON_ASSUNTA);
                    dao.open();
                    dao.updateCura(cura_final);
                    dao.close();
                } else if(v.getId()==R.id.btn_rimanda){
                    if(assumi_farmaco_notifica) {
                        setNotify(cura_final.getNome(), cura_final.getQuantità_assunzione(), cura_final.getUnità_misura(), cura_final.getOrario_assunzione(), cura_final.getInizio_cura(), cura_final.getFine_cura());
                    }

                }

                finish();


            }
        };


        conferma.setOnClickListener(gestisci_conferma);
        non_assunto.setOnClickListener(gestisci_conferma);
        rimanda.setOnClickListener(gestisci_conferma);



    }


    private void setNotify(String nome, int quantità, String unità, String orario, String data_inizio, String data_fine) {

        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        dao.open();
        Cura cura_notifica = dao.findCura(nome,data_inizio,data_fine,orario);
        dao.close();


        String key = nome + "_" + quantità + "_" + orario;
        int req_code_int = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("MyNotifPref",MODE_PRIVATE).edit();
        editor.putInt(key, req_code_int);
        editor.apply();

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyNotifPref", MODE_PRIVATE);
        int request_code = prefs.getInt(key, 0);

        SharedPreferences.Editor editor2 = getApplicationContext().getSharedPreferences("ContatoreGiorniPreferenze",MODE_PRIVATE).edit();
        editor.putInt(key, 0);
        editor.apply();

        Date date = null;
        SimpleDateFormat formatdate = new SimpleDateFormat("H:mm");
        Date cal1 = null;
        SimpleDateFormat formatcal1 = new SimpleDateFormat("yyyy-MM-dd");
        Date cal2 = null;
        SimpleDateFormat formatcal2 = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = formatdate.parse(orario);
            cal1 = formatcal1.parse(data_inizio);
            cal2 = formatcal2.parse(data_fine);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Bundle c = new Bundle();
        c.putString("contenuto", "Ricordati di prendere " + quantità + " " + unità + " di " + nome);
        c.putInt("req_code", request_code);
        c.putInt("n_giorni", (int) printDifference(cal1, cal2) );
        c.putInt("contatore_giorni", 0);
        c.putString("key", key);
        c.putString("cura", cura_notifica.toString());
        intent.putExtras(c);

        Calendar cal = Calendar.getInstance();
        /*
        cal.setTime(date);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = 0;
        */
        cal.setTimeInMillis(System.currentTimeMillis());
        /*
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);
        */

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), request_code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 1000*60, pendingIntent);

        //alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        Log.i("dati in setNotify()", "key:" + key + "reqcode" + request_code);

    }

}