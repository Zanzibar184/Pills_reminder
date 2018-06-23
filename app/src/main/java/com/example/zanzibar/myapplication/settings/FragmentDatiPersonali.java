package com.example.zanzibar.myapplication.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

public class FragmentDatiPersonali extends Fragment {

    private LinearLayout linearLayout = null;

    Button btn_modifica_dati = null;

    public FragmentDatiPersonali() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_dati_personali, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutdatipersonali);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_dati_personali, linearLayout, false);

        btn_modifica_dati = (Button) frame.findViewById(R.id.btn_modifica_dati);
        btn_modifica_dati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentModificaDatiPersonali modificaDatiPersonali = new FragmentModificaDatiPersonali();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, modificaDatiPersonali).addToBackStack(null).commit();

            }
        });

        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_dati_personali));
    }

}

