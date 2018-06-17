package com.example.zanzibar.myapplication.notifiche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.R;

public class NotificaScorta extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_gestione_scorte);

        Intent i = getIntent();
        final String contenuto = i.getStringExtra("contenuto");

        TextView txt_titolo = (TextView) findViewById(R.id.text_title_scorte);
        txt_titolo.setText(contenuto);

        Button button_ricarica = (Button) findViewById(R.id.btn_ricarica);
        button_ricarica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ricarica scorte
                Log.i("ricarica scorte", "Ricarica!!!");
                finish();
            }
        });


    }

}