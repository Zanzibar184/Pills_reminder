package com.example.zanzibar.myapplication.frames;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MieiFarmaci extends Fragment {

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_miei_farmaci = null;

    public MieiFarmaci() {
        // Required empty public constructor
    }

    public MieiFarmaci(FloatingActionButton fab_miei_farmaci) {
        this.fab_miei_farmaci = fab_miei_farmaci;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_miei_farmaci, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab_miei_farmaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ci troviamo in MIEI FARMACI() :-)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutfarmaci);

        for(int i = 0; i < 2; i++) {
            addLayoutMieifarmaci();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("I miei farmaci");
    }

    public void addLayoutMieifarmaci() {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_farmaci, linearLayout, false);

        //TODO: dichiarare le textview qui
        TextView txt_title = (TextView) frame.findViewById(R.id.txt_titolo_farmaco);
        txt_title.setText("Pillola XYZ");

        linearLayout.addView(frame);
    }

}
