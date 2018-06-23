package com.example.zanzibar.myapplication.frames;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.DateHelper;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.MyBounceInterpolator;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zanzibar.myapplication.frames.Cure.getDrawIcons;


/**
 * A simple {@link Fragment} subclass.
 */
public class MieiFarmaci extends Fragment {
    private CureDAO dao;
    private List<Cura> list_cure;
    public static String MODIFICA = "Modifica informazioni";
    public static String ELIMINA = "Elimina";
    public static String INFO = "Info farmaco";

    private LinearLayout linearLayout = null;
    private LinearLayout linearLayout_cure = null;

    FloatingActionButton fab_miei_farmaci = null;

    public MieiFarmaci() {
        // Required empty public constructor
    }

    public MieiFarmaci(FloatingActionButton fab_miei_farmaci) {
        this.fab_miei_farmaci = fab_miei_farmaci;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_miei_farmaci, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);
        fab_miei_farmaci.show();
        fab_miei_farmaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.7, 40);
                myAnim.setInterpolator(interpolator);
                fab_miei_farmaci.startAnimation(myAnim);
                ScegliPillola scegliPillola = new ScegliPillola(fab_miei_farmaci);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, scegliPillola).addToBackStack(null).commit();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutfarmaci);
        linearLayout_cure = (LinearLayout) view.findViewById(R.id.ll_cure);

        dao = new CureDao_DB();
        dao.open();
        list_cure = dao.getAllCure();

        for(int i = 0; i<list_cure.size();i++)
        {
            Cura tmp = list_cure.get(i);
            addFarmaci(tmp.getNome(), tmp.getRimanenze(), tmp.getScorta(), tmp.getInizio_cura(), tmp.getFine_cura(),tmp.getTipo_cura(), tmp.getId());
        }
        dao.close();

        final Spinner periodi = view.findViewById(R.id.spinnerperiodo_cure);
        periodi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {
                dao.open();
                linearLayout_cure.removeAllViews();
                list_cure = dao.getCureByRange(Note.parseRange(parent.getItemAtPosition(pos).toString()));

                for(int i = 0; i<list_cure.size();i++)
                {
                    Cura tmp = list_cure.get(i);
                    addFarmaci(tmp.getNome(), tmp.getRimanenze(), tmp.getScorta(), tmp.getInizio_cura(), tmp.getFine_cura(),tmp.getTipo_cura(), tmp.getId());
                }
                dao.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_mieifarmaci));
    }
    //aggiunge le cure prese dal db alla schermata
    public void addFarmaci(String nome, int qta_rimasta, int qta_totale, String start_cura, String end_cura, int tipo_cura, int id) {
        final View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_farmaci, linearLayout_cure, false);

        Date inizio = DateHelper.StringtoDate(start_cura);
        Date fine = DateHelper.StringtoDate(end_cura);

        ((TextView) frame.findViewById(R.id.txt_titolo_farmaco)).setText(nome);
        ((TextView) frame.findViewById(R.id.txt_start_cura)).setText(getString(R.string.cura_dal) + DateHelper.DateToString(inizio,getString(R.string.user_date_format)) );
        ((TextView) frame.findViewById(R.id.txt_end_cura)).setText(getString(R.string.cura_al) + DateHelper.DateToString(fine,getString(R.string.user_date_format)));


        ((TextView) frame.findViewById(R.id.txt_terminata)).setText(compareStringDate(inizio,fine));


        ((TextView) frame.findViewById(R.id.txt_id_hidden)).setText(id + "");
        ((ImageView) frame.findViewById(R.id.imgCura)).setImageDrawable(getResources().getDrawable(getDrawIcons(tipo_cura)));

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cura info_cura = getCurabyId(Integer.parseInt(((TextView) v.findViewById(R.id.txt_id_hidden)).getText().toString()));

                InfoFarmaco infoFarmaco = new InfoFarmaco(fab_miei_farmaci,info_cura);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, infoFarmaco).addToBackStack(null).commit();

            }
        });

        linearLayout_cure.addView(frame);
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

    private String compareStringDate( Date inizio, Date fine) {

        if (System.currentTimeMillis() > fine.getTime())
            return getString(R.string.cura_terminata);
        else if ((fine.getTime() > System.currentTimeMillis() && (inizio.getTime() < System.currentTimeMillis())))
            return getString(R.string.cura_in_corso);
        else if (inizio.getTime() > System.currentTimeMillis())
            return getString(R.string.cura_da_cominciare);

        return "";

    }

}
