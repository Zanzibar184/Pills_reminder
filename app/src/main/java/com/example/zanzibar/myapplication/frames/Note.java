package com.example.zanzibar.myapplication.frames;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Note extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_note, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AggiungiNota aggiungiNota = new AggiungiNota(fab_note);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, aggiungiNota).addToBackStack(null).commit();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutnote);



    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Note");
    }

    public void addLayoutNote() {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_note, linearLayout, false);

        //TODO: dichiarare le textview qui
        TextView titolo = (TextView) frame.findViewById(R.id.txt_note_title);
        TextView testo = (TextView) frame.findViewById(R.id.txt_contenuto);
        TextView data_nota = (TextView) frame.findViewById(R.id.data_nota);
        TextView ora = (TextView) frame.findViewById(R.id.ora);
        TextView categoria = (TextView) frame.findViewById(R.id.categoria_nota);
        int tipo_nota=1;


        linearLayout.addView(frame);
    }


}
