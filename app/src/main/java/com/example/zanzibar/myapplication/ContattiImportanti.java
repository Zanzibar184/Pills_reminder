package com.example.zanzibar.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


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
                AggiungiContatto aggiungiContatto = new AggiungiContatto(fab_contatti);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, aggiungiContatto).commit();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutcontatti);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.myframeviewcontatti, linearLayout, false);
        linearLayout.addView(frame);

        //prendo il numero di telefono che è inserito e

        TextView txt_telephone_num = (TextView) view.findViewById(R.id.tel_number);
        final String tel_num = txt_telephone_num.getText().toString();

        ImageView img_dial = (ImageView) view.findViewById(R.id.imagePhoneDial);
        img_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+tel_num));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Contatti importanti");
    }

}
