package com.example.zanzibar.myapplication.frames;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Calendario extends Fragment {

    FloatingActionButton fab_cal = null;

    private LinearLayout layout3= null;

    ScrollView mainScroll = null;

    private String dateSelected = null;

    private int height = 0;
    private int width = 0;
    private int actionBarHeight = 0;
    private int statusbarHeight = 0;

    public Calendario() {
        // Required empty public constructor
    }

    public Calendario(FloatingActionButton fab_cal) {
        this.fab_cal = fab_cal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainScroll = (ScrollView) container.findViewById(R.id.fragmentmanager);
        return inflater.inflate(R.layout.sfondo_calendario, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab_cal.show();

        fab_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AggiungiNota aggiungiNota = new AggiungiNota(fab_cal, dateSelected);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, aggiungiNota).addToBackStack(null).commit();
            }
        });


        //Initialize CustomCalendarView from layout
        CustomCalendarView calendarView = (CustomCalendarView) view.findViewById(R.id.calendario);

        //Initialize calendar with date
        Calendar currentCalendar = Calendar.getInstance(Locale.getDefault());

        //Show Monday as first date of week
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);

        //call refreshCalendar to update calendar the view
        calendarView.refreshCalendar(currentCalendar);

        //Handling custom calendar events
        calendarView.setCalendarListener(new CalendarListener() {
            @Override
            public void onDateSelected(final Date date) {
                final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                dateSelected = df.format(date);
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Toast.makeText(getContext(), df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        //Disabilito lo scorrimento della ScrollView principale
        mainScroll.setOnTouchListener( new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        LinearLayout layout1 = (LinearLayout) view.findViewById(R.id.layout1);
        LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.layout2);
        layout3 = (LinearLayout) view.findViewById(R.id.layout3);

        calcolaDimensioniFinestra();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                (int)(height/2)-(actionBarHeight/2)-(statusbarHeight/2));
        layout1.setLayoutParams(lp);
        layout2.setLayoutParams(lp);

        for(int i =0; i < 3; i++) {
            addLayoutCalendar();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Calendario");
    }

    private void addLayoutCalendar() {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_note_calendario, layout3, false);
        layout3.addView(frame);
    }

    private void calcolaDimensioniFinestra() {

        //Mi faccio restituire l'altezza della ActionBar
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        //Mi faccio restituire l'altezza della schermata
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        //Mi faccio restituire l'altezza della StatusBar
        Resources resources = getContext().getResources();
        statusbarHeight = resources.getIdentifier("status_bar_height", "dimen", "android");
        if(statusbarHeight>0) {
            statusbarHeight = resources.getDimensionPixelSize(statusbarHeight);
        }
    }

}
