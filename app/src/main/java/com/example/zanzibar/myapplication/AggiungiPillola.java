package com.example.zanzibar.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by user on 07/05/18.
 */

public class AggiungiPillola extends Fragment {

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_pills = null;
    private EditText text_date_init = null;
    private EditText text_date_end = null;
    private Calendar myCalendarinit = null;
    private Calendar myCalendarend = null;
    private DatePickerDialog.OnDateSetListener dateinit = null;
    private DatePickerDialog.OnDateSetListener dateend = null;

    public AggiungiPillola() {
        // Required empty public constructor
    }

    public AggiungiPillola(FloatingActionButton fab_pills) {
        this.fab_pills = fab_pills;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aggiungipillola, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddpill);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_pills_view, linearLayout, false);
        linearLayout.addView(frame);

        fab_pills.hide();

        ImageView img_date_init = (ImageView) view.findViewById(R.id.imgdateinit);
        ImageView img_date_end = (ImageView) view.findViewById(R.id.imgdateend);
        ImageView img_add_pill = (ImageView) view.findViewById(R.id.img_add_dosi);
        text_date_init = (EditText) view.findViewById(R.id.dateinit);
        text_date_end = (EditText) view.findViewById(R.id.dateend);

        img_date_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setDateInit();
                new DatePickerDialog(getContext(), dateinit, myCalendarinit
                        .get(Calendar.YEAR), myCalendarinit.get(Calendar.MONTH),
                        myCalendarinit.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        img_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                setDateEnd();
                new DatePickerDialog(getContext(), dateend, myCalendarend
                        .get(Calendar.YEAR), myCalendarend.get(Calendar.MONTH),
                        myCalendarend.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        img_add_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Dose aggiunta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Aggiungi farmaco");
    }


    private void setDateInit() {
        myCalendarinit = Calendar.getInstance();
        dateinit = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarinit.set(Calendar.YEAR, year);
                myCalendarinit.set(Calendar.MONTH, monthOfYear);
                myCalendarinit.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelInit();
            }
        };
    }

    private void setDateEnd() {
        myCalendarend = Calendar.getInstance();
        dateend = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarend.set(Calendar.YEAR, year);
                myCalendarend.set(Calendar.MONTH, monthOfYear);
                myCalendarend.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    private void updateLabelInit() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        text_date_init.setText(sdf.format(myCalendarinit.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        text_date_end.setText(sdf.format(myCalendarend.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
    }

}
