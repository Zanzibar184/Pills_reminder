package com.example.zanzibar.myapplication;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cure extends Fragment {

    private LinearLayout linearLayout = null;

    final int numberOfFrames = 4;

    FloatingActionButton fab_cure = null;

    public Cure() {
        // Required empty public constructor
    }

    public Cure(FloatingActionButton fab_cure) {
        this.fab_cure = fab_cure;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cure, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab_cure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScegliPillola scegliPillola = new ScegliPillola(fab_cure);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, scegliPillola).commit();
            }
        });

        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutcure);

        for (int i = 1; i <= numberOfFrames; i++) {
            addLayoutCure(i);
        }

    }

    public void addLayoutCure(int n) {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.myframeviewcure, linearLayout, false);
        TextView txt_title = (TextView) frame.findViewById(R.id.text_title);
            if (n == 1) {
                txt_title.setText("Mattina");
            } else if (n == 2) {
                txt_title.setText("Pomeriggio");
                //frame.setVisibility(View.GONE);
            } else if (n == 3) {
                txt_title.setText("Sera");
            } else if(n == 4){
                txt_title.setText("Notte");
            }

        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Cure");
    }

}
