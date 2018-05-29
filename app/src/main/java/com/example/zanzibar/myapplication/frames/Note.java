package com.example.zanzibar.myapplication.frames;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.Database.Note.Nota;
import com.example.zanzibar.myapplication.Database.Note.NoteDAO_DB;
import com.example.zanzibar.myapplication.Database.Note.NoteDao;
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
            addLayoutNote(tmp.getTitolo(),tmp.getTesto(),tmp.getData(),tmp.getOra(),tmp.getTipo_memo());
        }

        dao.close();

    }

    private void addLayoutNote(String titolo, String testo, String data, String ora, int tipo_memo) {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_note, linearLayout, false);

        ((TextView) frame.findViewById(R.id.txt_note_title)).setText(titolo);
        ((TextView) frame.findViewById(R.id.txt_contenuto)).setText(testo);
        ((TextView) frame.findViewById(R.id.data_nota)).setText(data);
        ((TextView) frame.findViewById(R.id.ora)).setText(ora);
        TextView categoria = (TextView) frame.findViewById(R.id.categoria_nota);

        if ((data.equals("")) && (ora.equals("")))
        {
            ((RelativeLayout)frame.findViewById(R.id.layout_data_ora_nota)).setVisibility(View.GONE);
        }



        linearLayout.addView(frame);
    }


}
