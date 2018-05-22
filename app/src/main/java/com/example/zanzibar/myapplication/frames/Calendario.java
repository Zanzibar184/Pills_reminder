package com.example.zanzibar.myapplication.frames;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private String dateSelected = null;

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
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Calendario");
    }

}
