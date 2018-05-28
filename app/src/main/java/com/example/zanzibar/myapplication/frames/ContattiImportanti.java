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

import com.example.zanzibar.myapplication.Database.contatti.Contatti;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDAO;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDao_DB;
import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContattiImportanti extends Fragment {

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_contatti = null;

    ContattiDAO dao;
    List<Contatti> list_contatti;

    public ContattiImportanti() {
        // Required empty public constructor
    }

    public ContattiImportanti(FloatingActionButton fab_contatti) {
        this.fab_contatti = fab_contatti;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dao = new ContattiDao_DB();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_contatti_importanti, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab_contatti.show();

        fab_contatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AggiungiContatto aggiungiContatto = new AggiungiContatto(fab_contatti);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, aggiungiContatto).addToBackStack(null).commit();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutcontatti);

        showContatti();

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Contatti importanti");
    }

    private void showContatti(){
        dao.open();

        list_contatti = dao.getAllContatti();

        for(int i=0; i<list_contatti.size();i++){
            Contatti tmp = list_contatti.get(i);
            addLayoutContatti(tmp.getNome(),tmp.getNumero(),tmp.getRuolo());
        }

        dao.close();

    }

    private void addLayoutContatti(String nome, final String numero, String ruolo) {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_contatti, linearLayout, false);

        ((TextView) frame.findViewById(R.id.txt_nome_contatto)).setText(nome);
        ((TextView) frame.findViewById(R.id.txt_ruolo)).setText(ruolo);
        ((TextView) frame.findViewById(R.id.tel_number)).setText(numero);



        ImageView img_dial = (ImageView) frame.findViewById(R.id.imagePhoneDial);
        img_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numero));
                startActivity(intent);
            }
        });

        linearLayout.addView(frame);
    }

}
