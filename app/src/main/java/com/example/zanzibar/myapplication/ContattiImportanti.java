package com.example.zanzibar.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContattiImportanti extends Fragment {

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_contatti = null;

    public ContattiImportanti() {
        // Required empty public constructor
    }

    public ContattiImportanti(FloatingActionButton fab_contatti) {
        this.fab_contatti = fab_contatti;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contatti_importanti, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab_contatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ci troviamo in CONTATTI IMPORTANTI() :-)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutcontatti);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.myframeviewcontatti, linearLayout, false);
        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Contatti importanti");
    }

}
