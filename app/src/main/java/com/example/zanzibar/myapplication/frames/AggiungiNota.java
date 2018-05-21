package com.example.zanzibar.myapplication.frames;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by user on 11/05/18.
 */

public class AggiungiNota extends Fragment {

    private LinearLayout linearLayout = null;

    private RelativeLayout rdatetimeselect = null;

    CheckBox c;
    private int nClicks = 1;

    private EditText text_date = null;
    private EditText text_time = null;
    private Calendar myCalendardate = null;
    private DatePickerDialog.OnDateSetListener datenote = null;

    FloatingActionButton fab_nota = null;

    public AggiungiNota() {
        // Required empty public constructor
    }

    public AggiungiNota(FloatingActionButton fab_nota) {
        this.fab_nota = fab_nota;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_aggiunginota, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddnota);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_nota, linearLayout, false);
        linearLayout.addView(frame);

        fab_nota.hide();

        rdatetimeselect = (RelativeLayout) view.findViewById(R.id.nota_date_time_view);
        ImageView img_date = (ImageView) view.findViewById(R.id.imageviewdate);
        ImageView img_time = (ImageView) view.findViewById(R.id.imageviewtime);
        text_date = (EditText) view.findViewById(R.id.textdate);
        text_time = (EditText) view.findViewById(R.id.textora);

        c = (CheckBox) view.findViewById(R.id.checkBox);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateAndTimeVisibility();
            }
        });

        img_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateNote();
                new DatePickerDialog(getContext(), datenote, myCalendardate
                        .get(Calendar.YEAR), myCalendardate.get(Calendar.MONTH),
                        myCalendardate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        img_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerNota();
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Aggiungi nota");
    }

    private void setDateNote() {
        myCalendardate = Calendar.getInstance();
        datenote = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendardate.set(Calendar.YEAR, year);
                myCalendardate.set(Calendar.MONTH, monthOfYear);
                myCalendardate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate();
            }
        };
    }

    private void updateLabelDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        text_date.setText(sdf.format(myCalendardate.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
    }

    private void setDateAndTimeVisibility() {
        if(nClicks==1) {
            nClicks++;
            rdatetimeselect.setVisibility(View.VISIBLE);
        } else if (nClicks==2) {
            nClicks--;
            rdatetimeselect.setVisibility(View.GONE);
        }
    }

    private void setTimePickerNota() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //Comunque dobbiamo salvare selectedHour e selectedMinute
                String string_with_minute_hour_zero = "0" + selectedHour + ":0" + selectedMinute;
                String string_with_hour_zero = "0" + selectedHour + ":" + selectedMinute;
                String string_with_minute_zero = selectedHour + ":0" + selectedMinute;
                String string_with_minute_hour = selectedHour + ":" + selectedMinute;
                if(selectedHour>=0 && selectedHour<=9) {
                    if(selectedMinute>=0 && selectedMinute<=9)
                        text_time.setText(string_with_minute_hour_zero);
                    else
                        text_time.setText(string_with_hour_zero);
                } else {
                    if(selectedMinute>=0 && selectedMinute<=9)
                        text_time.setText(string_with_minute_zero);
                    else
                        text_time.setText(string_with_minute_hour);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
}
