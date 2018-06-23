package com.example.zanzibar.myapplication.frames;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zanzibar.myapplication.Database.Note.Nota;
import com.example.zanzibar.myapplication.Database.Note.NoteDAO_DB;
import com.example.zanzibar.myapplication.Database.Note.NoteDao;
import com.example.zanzibar.myapplication.DateHelper;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.MyBounceInterpolator;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiver;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiverNote;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class Note extends Fragment {

    NoteDao dao;
    List<Nota> list_note;

    private LinearLayout linearLayoutSpinner = null;
    private LinearLayout linearLayoutNote = null;

    FloatingActionButton fab_note = null;

    public Note() {
        // Required empty public constructor
    }

    public Note(FloatingActionButton fab_note) {
        this.fab_note = fab_note;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dao = new NoteDAO_DB();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_note, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);

        fab_note.show();

        fab_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.7, 40);
                myAnim.setInterpolator(interpolator);
                fab_note.startAnimation(myAnim);
                AggiungiNota aggiungiNota = new AggiungiNota(fab_note);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, aggiungiNota).addToBackStack(null).commit();
            }
        });
        linearLayoutSpinner = (LinearLayout) view.findViewById(R.id.llnotespinner);
        linearLayoutNote = (LinearLayout) view.findViewById(R.id.llnotes);

        final Spinner periodi = view.findViewById(R.id.spinnerperiodo);
        periodi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                         long id) {
                  showNote(parent.getItemAtPosition(pos).toString());
              }

              @Override
              public void onNothingSelected(AdapterView<?> arg0) {

              }
          });



    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_note));
    }

    private void showNote(String range){
        linearLayoutNote.removeAllViews();
        dao.open();

        list_note = dao.getNoteByRange(parseRange(range));

        for(int i=0; i<list_note.size();i++){
            Nota tmp = list_note.get(i);
            addLayoutNote(tmp.getTitolo(),tmp.getTesto(),tmp.getData(),tmp.getOra(),tmp.getTipo_memo(), tmp.getId_memo());
        }

        dao.close();

    }

    private void addLayoutNote(String titolo, String testo, String data, String ora, int tipo_memo, int id_memo) {
        final View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_note, linearLayoutNote, false);

        ((TextView) frame.findViewById(R.id.txt_note_title)).setText(titolo);
        ((TextView) frame.findViewById(R.id.txt_contenuto)).setText(testo);

        Date data_format = DateHelper.StringtoDate(data);
        ((TextView) frame.findViewById(R.id.data_nota)).setText(DateHelper.DateToString(data_format, getString(R.string.user_date_format)));

        ((TextView) frame.findViewById(R.id.ora)).setText(ora);
        ((TextView) frame.findViewById(R.id.id_nota_hidden)).setText(id_memo + "");
        ((TextView) frame.findViewById(R.id.categoria_nota)).setText(CheckType(tipo_memo));


        if ((data.equals("")) && (ora.equals("")))
        {
            ((RelativeLayout)frame.findViewById(R.id.layout_data_ora_nota)).setVisibility(View.GONE);
        }

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopupMenuImages(getContext(), frame);
            }
        });



        linearLayoutNote.addView(frame);
    }

    public void setPopupMenuImages(Context c, final View v) {
        Context wrapper = new ContextThemeWrapper(getContext(), R.style.MenuPillsStyle);
        PopupMenu popup = new PopupMenu(wrapper,v);
        popup.getMenuInflater().inflate(R.menu.menu_modifica_nota, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                Nota modify = getNotabyId(Integer.parseInt(((TextView) v.findViewById(R.id.id_nota_hidden)).getText().toString()));

                if (item.getTitle().equals(MieiFarmaci.MODIFICA)) {

                    ModificaNota modificaNota = new ModificaNota(fab_note,modify);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentmanager, modificaNota).addToBackStack(null).commit();



                } else if (item.getTitle().equals(MieiFarmaci.ELIMINA)) {

                    SharedPreferences prefs = getContext().getSharedPreferences("MyNotifPref", MODE_PRIVATE);

                    dao.open();
                    deleteNotification(modify.getTitolo(),modify.getData(),modify.getOra(),modify.getTipo_memo());
                    dao.deleteNota(modify);
                    dao.close();

                    Note note = new Note(fab_note);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentmanager, note).addToBackStack(null).commit();


                }
                return true;

            }
        });

        popup.show();
    }

    public static String CheckType(int id){
        switch(id){
            case 1: return "Generale";
            case 2: return "Sintomi";
            case 3: return "Appuntamento";
            case 4: return "Promemoria";
        }

        return "";
    }

    public static  int CheckId(String scelta){
        switch(scelta){
            case "Generale": return 1;
            case "Sintomi": return 2;
            case "Appuntamento": return 3;
            case "Promemoria": return 4;
        }

        return 0;
    }
    public static  int CheckRadioId(int id){
        switch(id){
            case 1: return R.id.rbtn_generale;
            case 2: return R.id.rbtn_sintomi;
            case 3: return R.id.rbtn_appuntamento;
            case 4: return R.id.rbtn_promemoria;
        }

        return 0;
    }

    private Nota getNotabyId(int id){

        for(int i=0;i<list_note.size(); i++)
        {
            if ((list_note.get(i).getId_memo()) == id)
            {
                return list_note.get(i);
            }
        }
        Nota nota = null;
        return nota;
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

    public static String parseRange(String range){

        switch(range){
            case "Tutti": return DateAdd(3650);
            case "Ultimo giorno": return DateAdd(0);
            case "Ultima settimana": return DateAdd(7);
            case "Ultimo mese": return DateAdd(30);
            case "Ultimo anno": return DateAdd(365);
        }


        return "";
    }

    public static String DateAdd(int add){

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        c.add(Calendar.DATE, -add);
        String result = sdf.format(c.getTime());

        return result;

    }

}
