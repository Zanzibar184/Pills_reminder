package com.example.zanzibar.myapplication.frames;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

/**
 * Created by user on 11/05/18.
 */

public class AggiungiContatto extends Fragment {

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_contatto = null;

    Button aggiungiContatto = null;

    public AggiungiContatto() {
        // Required empty public constructor
    }

    public AggiungiContatto(FloatingActionButton fab_contatto) {
        this.fab_contatto = fab_contatto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_aggiungicontatto, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddcontatto);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_contatto, linearLayout, false);
        linearLayout.addView(frame);

        fab_contatto.hide();

        aggiungiContatto = (Button) view.findViewById(R.id.btn_conferma_contatto);
        aggiungiContatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO implement button add contacts
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Aggiungi contatto");
    }

}