package com.example.zanzibar.myapplication.settings;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FragmentModificaDatiPersonali extends Fragment {

    private LinearLayout linearLayout = null;

    private Calendar myCalendar = null;
    private DatePickerDialog.OnDateSetListener datenascita = null;

    Spinner spinner_genere = null;

    EditText txt_datanascita = null;
    EditText txt_nome = null;
    EditText txt_cognome = null;
    EditText txt_eta = null;
    EditText txt_genere = null;
    EditText txt_cf = null;
    EditText txt_citta = null;
    EditText txt_email = null;
    EditText txt_numero = null;

    Button btn_conferma = null;

    public FragmentModificaDatiPersonali() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_modifica_dati_personali, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutmodificadatipersonali);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_modifica_dati_personali, linearLayout, false);

        spinner_genere = (Spinner) frame.findViewById(R.id.spinner_genere);

        txt_nome = (EditText) frame.findViewById(R.id.nome_utente);
        txt_cognome = (EditText) frame.findViewById(R.id.cognome_utente);
        txt_eta = (EditText) frame.findViewById(R.id.eta_utente);
        txt_genere = (EditText) frame.findViewById(R.id.txt_genere_utente);
        txt_cf = (EditText) frame.findViewById(R.id.cf_utente);
        txt_citta = (EditText) frame.findViewById(R.id.citta_utente);
        txt_email = (EditText) frame.findViewById(R.id.email_utente);
        txt_numero = (EditText) frame.findViewById(R.id.numerotel_utente);

        ImageView calendar_datanascita = (ImageView) frame.findViewById(R.id.imgdatanascita);
        calendar_datanascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateNascita();
                new DatePickerDialog(getContext(), datenascita, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txt_datanascita = (EditText) frame.findViewById(R.id.data_nascita);
        txt_datanascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateNascita();
                new DatePickerDialog(getContext(), datenascita, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_conferma = (Button) frame.findViewById(R.id.btn_modifica_dati);
        btn_conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txt_nome.getText().toString();
                String cognome = txt_cognome.getText().toString();
                String genere = spinner_genere.getSelectedItem().toString();
            }
        });

        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Modifica Dati Personali");
    }

    private void setDateNascita() {
        myCalendar = Calendar.getInstance();
        datenascita = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        txt_datanascita.setText(sdf.format(myCalendar.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
    }

}

