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
import com.example.zanzibar.myapplication.frames.Cure;

import org.w3c.dom.Text;

import java.util.List;

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
                if(v.getId()==R.id.btn_conferma) {
                    cura_final.setStato_cura(Cura.ASSUNTA);
                    dao.open();
                    dao.updateCura(cura_final);
                    dao.close();
                } else if (v.getId()==R.id.btn_non_assunto) {
                    cura_final.setStato_cura(Cura.NON_ASSUNTA);
                    dao.open();
                    dao.updateCura(cura_final);
                    dao.close();
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