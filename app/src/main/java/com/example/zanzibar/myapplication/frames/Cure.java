package com.example.zanzibar.myapplication.frames;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cure extends Fragment {

    private LinearLayout linearLayout = null;
    private LinearLayout layout_pills = null;

    final int numberOfFrames = 4;

    String conferma_farmaco = "Conferma assunzione farmaco";
    String non_conferma_farmaco = "Farmaco non assunto";
    String ripristina_stato_farmaco = "Annulla";

    FloatingActionButton fab_cure = null;
    private CureDAO dao;
    private List<Cura> list_cure;

    public Cure() {
        // Required empty public constructor
    }

    public Cure(FloatingActionButton fab_cure) {
        this.fab_cure = fab_cure;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dao = new CureDao_DB();
        dao.open();
        list_cure = dao.getAllCure();
        dao.close();

        /*
        View inflatedScroll = getLayoutInflater().inflate(R.layout.content_main, null);
        scrollView = (ScrollView) inflatedScroll.findViewById(R.id.fragmentmanager);
        scrollView.fullScroll(ScrollView.FOCUS_UP);
        Button btn =
                */

        return inflater.inflate(R.layout.sfondo_cure, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        //-----------IMPORTANTISSIMOOOOOOO!!!!!
        fab_cure.show();
        //-----------
        fab_cure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScegliPillola scegliPillola = new ScegliPillola(fab_cure);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, scegliPillola).addToBackStack(null).commit();
            }
        });

        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutcure);

        for (int i = 1; i <= numberOfFrames; i++) {
            addLayoutCure(i);
        }

    }

    public void addLayoutCure(int n) {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_cure, linearLayout, false);
        layout_pills = (LinearLayout) frame.findViewById(R.id.llayout_view_farmaco);
        TextView txt_title = (TextView) frame.findViewById(R.id.text_title);
            if (n == 1) {
                txt_title.setText("Mattina");

                for(int i=0;i<list_cure.size(); i++)
                {
                    Cura tmp = list_cure.get(i);
                    addLayoutFarmaco(tmp.getNome(),tmp.getQuantità_assunzione(), tmp.getTipo_cura());
                }

            } else if (n == 2) {
                txt_title.setText("Pomeriggio");

            } else if (n == 3) {
                txt_title.setText("Sera");

            } else if(n == 4){
                txt_title.setText("Notte");

            }

        linearLayout.addView(frame);
    }

    public void addLayoutFarmaco (String nome,int qta_ass, int tipo_cura) {

            View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_pillola, layout_pills, false);
            final ImageView img_farmaco = (ImageView) frame.findViewById(R.id.img_farmaco);
            final ImageView img_greenV = (ImageView) frame.findViewById(R.id.img_greenV);
            final ImageView img_redX = (ImageView) frame.findViewById(R.id.img_redX);
            final TextView txt_nome = (TextView) frame.findViewById(R.id.txt_nome_pillola);
            final TextView txt_orario_assunzione = (TextView) frame.findViewById(R.id.txt_ora);
            final TextView txt_qta_ass = (TextView) frame.findViewById(R.id.txt_numero_dosi);

            //Qui va il codice per le altre TextView e altro da gestire


                Drawable drawable_farmaco = getResources().getDrawable(getDrawIcons(tipo_cura));
                img_farmaco.setImageDrawable(drawable_farmaco);
                txt_nome.setText(nome);
                txt_orario_assunzione.setText("18:00"); //da aggiornare
                txt_qta_ass.setText("x" + qta_ass);



            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPopupMenuImages(getContext(),img_farmaco, img_greenV, img_redX);
                }
            });
            layout_pills.addView(frame);

    }


    public void setPopupMenuImages(Context c, final ImageView imgfarmaco, final ImageView imgok, final ImageView imgno) {
        PopupMenu popup = new PopupMenu(c,imgfarmaco);
        popup.getMenuInflater().inflate(R.menu.menu_conferma_assunzione, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals(conferma_farmaco)) {
                    imgok.setVisibility(View.VISIBLE);
                    imgno.setVisibility(View.GONE);
                    //TODO: inserire codice per gestire farmaco assunto
                } else if (item.getTitle().equals(non_conferma_farmaco)) {
                    imgok.setVisibility(View.GONE);
                    imgno.setVisibility(View.VISIBLE);
                    //TODO: inserire codice per gestire farmaco NON assunto
                } else if (item.getTitle().equals(ripristina_stato_farmaco)) {
                    imgok.setVisibility(View.GONE);
                    imgno.setVisibility(View.GONE);
                    //TODO: inserire codice per gestire annullamento assunzione farmaco
                }
                return true;
            }
        });

        popup.show();
    }


    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Cure");
    }


    private int getDrawIcons(int id){
        switch(id){
            case 1: return R.drawable.pill_colored;
            case 2: return R.drawable.syringe;
            case 3: return R.drawable.sciroppo;
            case 4: return R.drawable.gocce;
            case 5: return R.drawable.pomata;
            case 6: return R.drawable.spray;
        }

        return 0;
    }
}

