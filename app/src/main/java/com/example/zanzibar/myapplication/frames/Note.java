package com.example.zanzibar.myapplication.frames;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.Database.Note.Nota;
import com.example.zanzibar.myapplication.Database.Note.NoteDAO_DB;
import com.example.zanzibar.myapplication.Database.Note.NoteDao;
import com.example.zanzibar.myapplication.Database.contatti.Contatti;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.util.List;


public class Note extends Fragment {

    NoteDao dao;
    List<Nota> list_note;

    private LinearLayout linearLayout = null;

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
                AggiungiNota aggiungiNota = new AggiungiNota(fab_note);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, aggiungiNota).addToBackStack(null).commit();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutnote);

        showNote();


    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Note");
    }

    private void showNote(){
        dao.open();

        list_note = dao.getAllNote();

        for(int i=0; i<list_note.size();i++){
            Nota tmp = list_note.get(i);
            addLayoutNote(tmp.getTitolo(),tmp.getTesto(),tmp.getData(),tmp.getOra(),tmp.getTipo_memo(), tmp.getId_memo());
        }

        dao.close();

    }

    private void addLayoutNote(String titolo, String testo, String data, String ora, int tipo_memo, int id_memo) {
        final View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_note, linearLayout, false);

        ((TextView) frame.findViewById(R.id.txt_note_title)).setText(titolo);
        ((TextView) frame.findViewById(R.id.txt_contenuto)).setText(testo);
        ((TextView) frame.findViewById(R.id.data_nota)).setText(data);
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



        linearLayout.addView(frame);
    }

    public void setPopupMenuImages(Context c, final View v) {
        PopupMenu popup = new PopupMenu(c,v);
        popup.getMenuInflater().inflate(R.menu.menu_modifica_nota, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                Nota modify = getNotabyId(Integer.parseInt(((TextView) v.findViewById(R.id.id_nota_hidden)).getText().toString()));

                if (item.getTitle().equals(MieiFarmaci.MODIFICA)) {

                    ModificaNota modificaNota = new ModificaNota(fab_note,modify);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentmanager, modificaNota).addToBackStack(null).commit();



                } else if (item.getTitle().equals(MieiFarmaci.ELIMINA)) {

                    dao.open();
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


}
