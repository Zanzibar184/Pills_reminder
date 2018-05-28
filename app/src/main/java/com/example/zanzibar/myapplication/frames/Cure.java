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
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cure extends Fragment {

    private LinearLayout linearLayout = null;
    private LinearLayout layout_pills_mattina = null;
    private LinearLayout layout_pills_pomeriggio = null;
    private LinearLayout layout_pills_sera = null;
    private LinearLayout layout_pills_notte = null;

    final int numberOfFrames = 4;

    String conferma_farmaco = "Conferma assunzione farmaco";
    String non_conferma_farmaco = "Farmaco non assunto";
    String ripristina_stato_farmaco = "Annulla";
    String informazioni_farmaco = "Info farmaco";

    FloatingActionButton fab_cure = null;
    private CureDAO dao;
    private List<Cura> list_cure;

    ScrollView v;

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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        list_cure = dao.getCureByDate(formatter.format(new Date()));
        dao.close();

        v = container.findViewById(R.id.fragmentmanager);

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

        addLayoutCure();

        v.setScrollX(0);
        v.setScrollY(0);


    }

    public void addLayoutCure() {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_cure, linearLayout, false);
        layout_pills_mattina = (LinearLayout) frame.findViewById(R.id.layout_mattina);
        layout_pills_pomeriggio = (LinearLayout) frame.findViewById(R.id.layout_pomeriggio);
        layout_pills_sera = (LinearLayout) frame.findViewById(R.id.layout_sera);
        layout_pills_notte = (LinearLayout) frame.findViewById(R.id.layout_notte);

                for(int i=0;i<list_cure.size(); i++)
                {
                    Cura tmp = list_cure.get(i);
                    int ora = Integer.parseInt(tmp.getOrario_assunzione().substring(0,2));

                    if((ora >= 6) && (ora <12))
                    {
                        addLayoutFarmaco(tmp.getNome(),tmp.getQuantità_assunzione(), tmp.getTipo_cura(), tmp.getOrario_assunzione(),layout_pills_mattina, tmp.getId(),tmp.getStato_cura());
                    }
                    if((ora >= 12) && (ora <18))
                    {
                        addLayoutFarmaco(tmp.getNome(),tmp.getQuantità_assunzione(), tmp.getTipo_cura(), tmp.getOrario_assunzione(),layout_pills_pomeriggio, tmp.getId(),tmp.getStato_cura());
                    }
                    if((ora >= 18) && (ora <24))
                    {
                        addLayoutFarmaco(tmp.getNome(),tmp.getQuantità_assunzione(), tmp.getTipo_cura(), tmp.getOrario_assunzione(),layout_pills_sera, tmp.getId(),tmp.getStato_cura());
                    }
                    if((ora >= 0) && (ora <6))
                    {
                        addLayoutFarmaco(tmp.getNome(),tmp.getQuantità_assunzione(), tmp.getTipo_cura(), tmp.getOrario_assunzione(),layout_pills_notte, tmp.getId(),tmp.getStato_cura());
                    }

                }



        linearLayout.addView(frame);
    }

    public void addLayoutFarmaco (String nome,int qta_ass, int tipo_cura, String orario_assunzione, LinearLayout layout, int id, String stato_cura) {

            final View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_pillola, layout, false);
            final ImageView img_farmaco = (ImageView) frame.findViewById(R.id.img_farmaco);
            final ImageView img_greenV = (ImageView) frame.findViewById(R.id.img_greenV);
            final ImageView img_redX = (ImageView) frame.findViewById(R.id.img_redX);
            final TextView txt_nome = (TextView) frame.findViewById(R.id.txt_nome_pillola);
            final TextView txt_orario_assunzione = (TextView) frame.findViewById(R.id.txt_ora);
            final TextView txt_qta_ass = (TextView) frame.findViewById(R.id.txt_numero_dosi);
            final TextView cura_id = (TextView) frame.findViewById(R.id.cura_id);


            //Qui va il codice per le altre TextView e altro da gestire


                Drawable drawable_farmaco = getResources().getDrawable(getDrawIcons(tipo_cura));
                img_farmaco.setImageDrawable(drawable_farmaco);
                txt_nome.setText(nome);
                txt_orario_assunzione.setText(orario_assunzione);
                txt_qta_ass.setText("x" + qta_ass);
                cura_id.setText(Integer.toString(id));

                if (stato_cura.equals(Cura.NON_ASSUNTA))
                {
                    Log.i("stato cura_: ", stato_cura);
                    img_redX.setVisibility(View.VISIBLE);
                }
                else if (stato_cura.equals(Cura.ASSUNTA))
                {
                    Log.i("stato cura_: ", stato_cura);
                    img_greenV.setVisibility(View.VISIBLE);
                }


            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPopupMenuImages(getContext(),frame, img_greenV, img_redX);
                }
            });
            layout.addView(frame);

    }


    public void setPopupMenuImages(Context c, final View v, final ImageView imgok, final ImageView imgno) {
        PopupMenu popup = new PopupMenu(c,v);
        popup.getMenuInflater().inflate(R.menu.menu_conferma_assunzione, popup.getMenu());

        if(imgok.getVisibility() == View.VISIBLE) {
            popup.getMenu().findItem(R.id.menu_conferma).setVisible(false);
        } else if(imgno.getVisibility() == View.VISIBLE) {
            popup.getMenu().findItem(R.id.menu_non_conferma).setVisible(false);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                dao = new CureDao_DB();
                dao.open();

                TextView id = (TextView) v.findViewById(R.id.cura_id);

                Cura updated_cura = getCurabyId(Integer.parseInt(id.getText().toString()));

                if (item.getTitle().equals(conferma_farmaco)) {
                    imgok.setVisibility(View.VISIBLE);
                    imgno.setVisibility(View.GONE);

                    //aggiorno lo stato del record
                    updated_cura.setStato_cura(Cura.ASSUNTA);
                    dao.updateCura(updated_cura);

                } else if (item.getTitle().equals(non_conferma_farmaco)) {
                    imgok.setVisibility(View.GONE);
                    imgno.setVisibility(View.VISIBLE);

                    updated_cura.setStato_cura(Cura.NON_ASSUNTA);
                    dao.updateCura(updated_cura);
                } else if (item.getTitle().equals(ripristina_stato_farmaco)) {
                    imgok.setVisibility(View.GONE);
                    imgno.setVisibility(View.GONE);

                    updated_cura.setStato_cura(Cura.DA_ASSUMERE);
                    dao.updateCura(updated_cura);
                } else if (item.getTitle().equals(informazioni_farmaco)) {
                    MieiFarmaci mieiFarmaci = new MieiFarmaci(fab_cure);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentmanager, mieiFarmaci).addToBackStack(null).commit();
                }

                dao.close();
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


    public static int getDrawIcons(int id){
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

    private Cura getCurabyId(int id){

        for(int i=0;i<list_cure.size(); i++)
        {
            if ((list_cure.get(i).getId()) == id)
            {
                return list_cure.get(i);
            }
        }
        Cura cura = null;
        return cura;
    }
}

