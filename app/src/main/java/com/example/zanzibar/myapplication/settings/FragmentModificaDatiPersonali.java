package com.example.zanzibar.myapplication.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

public class FragmentModificaDatiPersonali extends Fragment {

    private LinearLayout linearLayout = null;

    public FragmentModificaDatiPersonali() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_modifica_dati_personali, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutmodificadatipersonali);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_modifica_dati_personali, linearLayout, false);
        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Modifica Dati Personali");
    }

}

