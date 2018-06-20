package com.example.zanzibar.myapplication.frames;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.example.zanzibar.myapplication.Database.Note.Nota;
import com.example.zanzibar.myapplication.Database.Note.NoteDAO_DB;
import com.example.zanzibar.myapplication.Database.Note.NoteDao;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiverNote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 11/05/18.
 */

public class ModificaNota extends Fragment {

    NoteDao dao;
    List<Nota> list_note;

    private LinearLayout linearLayout = null;

    private RelativeLayout rdatetimeselect = null;

    CheckBox c;

    String dateSelected = null;

    Boolean date_time_visible = false;

    private EditText text_date = null;
    private EditText text_time = null;
    private EditText text_titolo_nota = null;
    private EditText text_contenuto_nota = null;
    private Button conferma = null;
    private Calendar myCalendardate = null;
    private DatePickerDialog.OnDateSetListener datenote = null;

    private String categoria_nota = null;

    FloatingActionButton fab_nota = null;

    private Nota nota;

    public ModificaNota() {
        // Required empty public constructor
    }

    public ModificaNota(FloatingActionButton fab_nota) {
        this.fab_nota = fab_nota;
    }

    public ModificaNota(FloatingActionButton fab_nota, Nota nota) {
        this.fab_nota = fab_nota;
        this.nota = nota;
    }

    public ModificaNota(FloatingActionButton fab_nota, String dateSelected) {
        this.fab_nota = fab_nota;
        this.dateSelected = dateSelected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dao = new NoteDAO_DB();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_aggiunginota, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddnota);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_nota, linearLayout, false);
        linearLayout.addView(frame);

        fab_nota.hide();

        rdatetimeselect = (RelativeLayout) view.findViewById(R.id.nota_date_time_view);

        ImageView img_date = (ImageView) view.findViewById(R.id.imageviewdate);

        ImageView img_time = (ImageView) view.findViewById(R.id.imageviewtime);

        ((ImageView) view.findViewById(R.id.img_mic_titolo_nota)).setVisibility(View.GONE);
        ((ImageView) view.findViewById(R.id.img_mic_contenuto)).setVisibility(View.GONE);

        text_date = (EditText) view.findViewById(R.id.textdate);
        text_date.setText(nota.getData());

        text_time = (EditText) view.findViewById(R.id.textora);
        text_time.setText(nota.getOra());

        conferma = (Button) view.findViewById(R.id.btn_conferma_inserimento_nota);

        text_contenuto_nota = (EditText) view.findViewById(R.id.contenuto_nota);
        text_contenuto_nota.setText(nota.getTesto());

        text_titolo_nota = (EditText) view.findViewById(R.id.title_nota);
        text_titolo_nota.setText(nota.getTitolo());

        RadioGroup rgroup = view.findViewById(R.id.radioGroup_cat);
        rgroup.check(Note.CheckRadioId(nota.getTipo_memo()));
        categoria_nota=Note.CheckType(nota.getTipo_memo());

        rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked)
                {
                    String s = checkedRadioButton.getText().toString();
                    categoria_nota = s;
                    //Log.i("Checked:", categoria_nota + " " + CheckId(categoria_nota));
                }
            }
        });

        c = (CheckBox) view.findViewById(R.id.checkbox);
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                     setDateAndTimeVisibility();

                     if(date_time_visible == false){
                         text_date.setText("");
                         text_time.setText("");
                     }

             }
         }
        );

        if(!nota.getData().equals("")) {
            c.setChecked(true);
            date_time_visible = false;
            setDateAndTimeVisibility();
        }

        text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateNote();
                new DatePickerDialog(getContext(), datenote, myCalendardate
                        .get(Calendar.YEAR), myCalendardate.get(Calendar.MONTH),
                        myCalendardate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        text_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerNota();
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

        deleteNotification(nota.getTitolo(), nota.getData(), nota.getOra(), nota.getTipo_memo());

        conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tipo_memo = Note.CheckId(categoria_nota);
                if((!text_contenuto_nota.getText().toString().equals("")) && (tipo_memo != 0)) {
                    if(date_time_visible == true && (!text_time.getText().toString().equals("")) && (!text_date.getText().toString().equals(""))) {
                        dao.open();
                        if ((!text_date.getText().toString().equals("") && !text_time.getText().toString().equals(""))) {
                            setNotifyNota(text_titolo_nota.getText().toString(), text_contenuto_nota.getText().toString(), text_date.getText().toString(), text_time.getText().toString(), tipo_memo);
                        }
                        dao.updateNota(new Nota(text_titolo_nota.getText().toString(), text_contenuto_nota.getText().toString(), text_date.getText().toString(), text_time.getText().toString(), tipo_memo, nota.getId_memo()));

                        dao.close();

                        Note nota = new Note(fab_nota);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.fragmentmanager, nota).addToBackStack(null).commit();
                    }
                    else
                        colorInputUnfilled();
                }
                else
                    colorInputUnfilled();

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_note));
    }

    private void setNotifyNota(String titolo, String contenuto, String date, String time, int tipo){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiverNote.class);

        Random r = new Random();
        int random_value = r.nextInt();
        while(random_value<0) {
            random_value = r.nextInt();
        }
        String key = titolo + "_" + date + "_" + time + "_" + tipo;
        int req_code_int = random_value;
        //int req_code_int = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        String myStrDate = date + " " + time;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date_nota = null;

        SharedPreferences.Editor editor = getContext().getSharedPreferences("MyNotifPref",MODE_PRIVATE).edit();
        editor.putInt(key, req_code_int);
        editor.apply();

        /*
        SharedPreferences prefs = getContext().getSharedPreferences("MyNotifPref", MODE_PRIVATE);
        int request_code = prefs.getInt(key, 0);
        */

        try {
            date_nota = format.parse(myStrDate);
            System.out.println(date_nota);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Bundle c = new Bundle();
        c.putString("contenuto", titolo);
        c.putInt("req_code", req_code_int);
        c.putString("key", key);
        c.putString("titolo_nota", titolo);
        c.putString("contenuto_nota", contenuto);
        c.putString("data_nota", date);
        c.putString("orario_nota", time);
        c.putInt("tipo_nota", tipo);

        /*
        Log.i("contenut aggiunginota", titolo);
        Log.i("reqcode in aggiunginota", req_code_int+"");
        Log.i("key in aggiunginota", key);
        */

        intent.putExtras(c);


        Calendar cal = Calendar.getInstance();
        cal.setTime(date_nota);
        cal.add(Calendar.DATE, -1);
        Log.i("date cal", cal.getTime() + "");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), req_code_int, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

    }

    private void deleteNotification(String titolo, String data, String ora, int tipo) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiverNote.class);

        String key = titolo + "_" + data + "_" + ora + "_" + tipo;
        //int req_code_int = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        SharedPreferences prefs = getContext().getSharedPreferences("MyNotifPref",MODE_PRIVATE);
        int req = prefs.getInt(key, 0);

        Log.i("contenut aggiunginota", titolo);
        Log.i("reqcode in aggiunginota", req+"");
        Log.i("key in aggiunginota", key);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), req, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    private void colorInputUnfilled(){


        GradientDrawable alert = new GradientDrawable();
        alert.setStroke(3, Color.RED);


        if (text_contenuto_nota.getText().toString().equals(""))
            text_contenuto_nota.setBackground(alert);
        else
            text_contenuto_nota.setBackground(null);

        if(date_time_visible == true){
            if (text_date.getText().toString().equals(""))
                text_date.setBackground(alert);
            else
                text_date.setBackground(null);
            if (text_time.getText().toString().equals(""))
                text_time.setBackground(alert);
            else
                text_time.setBackground(null);
        }

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
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        text_date.setText(sdf.format(myCalendardate.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
    }

    private void setDateAndTimeVisibility() {
        if(date_time_visible==true) {
            rdatetimeselect.setVisibility(View.GONE);
            date_time_visible = false;
        } else if (date_time_visible==false) {
            rdatetimeselect.setVisibility(View.VISIBLE);
            date_time_visible = true;
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
