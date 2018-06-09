package com.example.zanzibar.myapplication.notifiche;

import android.content.Intent;
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

import org.w3c.dom.Text;

import java.util.List;

public class NotificaAssunzione extends AppCompatActivity {

    private CureDAO dao;
    private List<Cura> list_cure;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_gestione_farmaci);

        Button conferma = (Button) findViewById(R.id.btn_conferma);
        Button non_assunto = (Button) findViewById(R.id.btn_non_assunto);
        Button rimanda = (Button) findViewById(R.id.btn_rimanda);

        Intent i = getIntent();
        final String m = i.getStringExtra("prova_passaggio_parametri");

        View.OnClickListener gestisci_conferma = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_conferma) {
                    Log.i("click1", "hai premuto si");
                    Log.i("bloh", m);
                } else if (v.getId()==R.id.btn_non_assunto) {
                    Log.i("click2", "hai premuto no");
                } else if(v.getId()==R.id.btn_rimanda){
                    Log.i("click3", "hai premuto rimanda");
                }
            }
        };

        conferma.setOnClickListener(gestisci_conferma);
        non_assunto.setOnClickListener(gestisci_conferma);
        rimanda.setOnClickListener(gestisci_conferma);



    }
}