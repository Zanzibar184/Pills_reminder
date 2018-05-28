package com.example.zanzibar.myapplication.frames;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zanzibar.myapplication.Database.Note.Nota;
import com.example.zanzibar.myapplication.Database.Note.NoteDAO_DB;
import com.example.zanzibar.myapplication.Database.Note.NoteDao;
import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;
import com.stacktips.view.CalendarListener;
import com.stacktips.view.CustomCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class Calendario extends Fragment {

    FloatingActionButton fab_cal = null;

    private LinearLayout layout3= null;

    private String dateSelected = null;

    private int height = 0;
    private int width = 0;
    private int actionBarHeight = 0;
    private int statusbarHeight = 0;
    private int height_layout1 = 0;

    private CureDAO dao;
    private List<Cura> list_cure;

    private NoteDao dao_note;
    private List<Nota> list_note;

    public Calendario() {
        // Required empty public constructor
    }

    public Calendario(FloatingActionButton fab_cal) {
        this.fab_cal = fab_cal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dao = new CureDao_DB();
        dao_note = new NoteDAO_DB();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_calendario, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
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

        LinearLayout layout1 = (LinearLayout) view.findViewById(R.id.layout1);

        //Initialize CustomCalendarView from layout
        final CustomCalendarView calendarView = (CustomCalendarView) view.findViewById(R.id.calendario);

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
                refreshData(dateSelected);
            }

            @Override
            public void onMonthChanged(Date date) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                //Toast.makeText(getContext(), df.format(date), Toast.LENGTH_SHORT).show();
            }
        });

        calendarView.post(new Runnable() {
            @Override
            public void run() {
                height_layout1 = calendarView.getHeight(); //height is ready
                LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.layout2);
                layout3 = (LinearLayout) view.findViewById(R.id.layout3);
                calcolaDimensioniFinestra();
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int)((height - actionBarHeight - statusbarHeight - height_layout1)));
                layout2.setLayoutParams(lp);

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Calendario");
    }

    private void refreshData(String date){
        layout3.removeAllViews();
        dao.open();
        list_cure = dao.getCureByDate(date);
        for(int i=0; i<list_cure.size();i++){
            Cura tmp = list_cure.get(i);
            addLayoutFarmaciCalendario(tmp.getNome(),tmp.getOrario_assunzione(),tmp.getQuantità_assunzione(),tmp.getUnità_misura(), tmp.getTipo_cura());
        }

        dao.close();

        /*
        dao_note.open();
        list_note = dao_note.getNoteByDate(date);
        for(int i=0; i<list_note.size();i++){
            Nota tmp = list_note.get(i);
            addLayoutNoteCalendario(tmp.getTitolo(),tmp.getTesto(),tmp.getData(),tmp.getOra(),tmp.getTipo_memo());
        }

        dao_note.close();
        */
    }


    private void addLayoutNoteCalendario(String titolo, String testo, String data, String ora, int tipo_memo) {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_nota_calendario, layout3, false);

        ((TextView) frame.findViewById(R.id.txt_note_title)).setText(titolo);
        ((TextView) frame.findViewById(R.id.txt_contenuto)).setText(testo);
        ((TextView) frame.findViewById(R.id.data_nota)).setText(data);
        ((TextView) frame.findViewById(R.id.ora)).setText(ora);
        layout3.addView(frame);
    }

    private void addLayoutFarmaciCalendario(String nome, String orario, int qta_ass, String udm, int tipo_cura) {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_farmaci_calendario, layout3, false);

        ((TextView) frame.findViewById(R.id.txt_titolo_farmaco)).setText(nome);
        ((TextView) frame.findViewById(R.id.txt_dose)).setText("Assumere "+qta_ass+" "+udm);
        ((TextView) frame.findViewById(R.id.txt_ora_dose)).setText(orario);
        setImage(tipo_cura, ((ImageView) frame.findViewById(R.id.img_pillola_nota)));

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

    private void setImage(int tipo_cura, ImageView imgCura){

        if(tipo_cura ==2) {
            imgCura.setImageResource(R.drawable.syringe);

        } else if(tipo_cura ==3) {
            imgCura.setImageResource(R.drawable.sciroppo);

        } else if(tipo_cura ==4) {
            imgCura.setImageResource(R.drawable.gocce);

        } else if(tipo_cura ==5) {
            imgCura.setImageResource(R.drawable.pomata);

        } else if(tipo_cura ==6) {
            imgCura.setImageResource(R.drawable.spray);

        } else if(tipo_cura ==1) {
            imgCura.setImageResource(R.drawable.pill_colored);

        }
    }

}
