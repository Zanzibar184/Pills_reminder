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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.example.zanzibar.myapplication.frames.AggiungiPillola.printDifference;

public class NotificaNota extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_gestione_note);

        Intent i = getIntent();
        final String titolo = i.getStringExtra("titolo");
        final String contenuto = i.getStringExtra("contenuto");
        final String data = i.getStringExtra("data");
        final String ora = i.getStringExtra("ora");
        final int tipo = i.getIntExtra("tipo", 0);

        RelativeLayout rldataora = findViewById(R.id.layout_data_ora_nota);

        TextView txt_titolo = (TextView) findViewById(R.id.txt_note_title);
        TextView txt_contenuto = (TextView) findViewById(R.id.txt_contenuto);
        TextView txt_data = (TextView) findViewById(R.id.data_nota);
        TextView txt_ora = (TextView) findViewById(R.id.ora);
        TextView txt_tipo = (TextView) findViewById(R.id.categoria_nota);

        String tipo_nota = null;

        txt_titolo.setText(titolo);
        txt_contenuto.setText(contenuto);
        switch (tipo) {
            case 1: tipo_nota = "Generale"; break;
            case 2: tipo_nota = "Sintomi"; break;
            case 3: tipo_nota = "Appuntamento"; break;
            case 4: tipo_nota = "Promemoria"; break;
        }
        txt_tipo.setText(tipo_nota);

        if((txt_data.equals(null)) && (txt_ora.equals(null))) {
            rldataora.setVisibility(View.GONE);
        } else {
            txt_ora.setText(ora);
            txt_data.setText(data);
        }


    }

}