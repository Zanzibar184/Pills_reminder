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

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;


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
        return inflater.inflate(R.layout.sfondo_contatti_importanti, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab_contatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AggiungiContatto aggiungiContatto = new AggiungiContatto(fab_contatti);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, aggiungiContatto).addToBackStack(null).commit();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutcontatti);

        for(int i = 0; i < 3; i++) {
            addLayoutContatti();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Contatti importanti");
    }

    public void addLayoutContatti() {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_contatti, linearLayout, false);

        TextView nome_contatto = (TextView) frame.findViewById(R.id.txt_nome_contatto);
        TextView ruolo = (TextView) frame.findViewById(R.id.txt_ruolo);
        TextView telephone_num = (TextView) frame.findViewById(R.id.tel_number);

        final String tel_num = telephone_num.getText().toString();

        ImageView img_dial = (ImageView) frame.findViewById(R.id.imagePhoneDial);
        img_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tel_num));
                startActivity(intent);
            }
        });

        linearLayout.addView(frame);
    }

}
