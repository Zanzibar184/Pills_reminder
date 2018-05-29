package com.example.zanzibar.myapplication.frames;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.zanzibar.myapplication.frames.Cure.getDrawIcons;


/**
 * A simple {@link Fragment} subclass.
 */
public class MieiFarmaci extends Fragment {
    private CureDAO dao;
    private List<Cura> list_cure;
    public static String MODIFICA = "Modifica informazioni";
    public static String ELIMINA = "Elimina";

    private LinearLayout linearLayout = null;

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
        fab_miei_farmaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ci troviamo in MIEI FARMACI() :-)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutfarmaci);

        dao = new CureDao_DB();
        dao.open();
        list_cure = dao.getAllCure();

        for(int i = 0; i<list_cure.size();i++)
        {
            Cura tmp = list_cure.get(i);
            addFarmaci(tmp.getNome(), tmp.getRimanenze(), tmp.getScorta(), tmp.getInizio_cura(), tmp.getFine_cura(),tmp.getTipo_cura(), tmp.getId());
        }


    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("I miei farmaci");
    }

    public void addFarmaci(String nome, int qta_rimasta, int qta_totale, String start_cura, String end_cura, int tipo_cura, int id) {
        final View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_farmaci, linearLayout, false);

        Date inizio = StringToDate(start_cura);
        Date fine = StringToDate(end_cura);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        ((TextView) frame.findViewById(R.id.txt_titolo_farmaco)).setText(nome);
        ((TextView) frame.findViewById(R.id.txt_qta_totale)).setText("Quantità della confezione: " +qta_totale);
        ((TextView) frame.findViewById(R.id.txt_qta_rimasta)).setText("Quantità rimanente: " +qta_rimasta);
        ((TextView) frame.findViewById(R.id.txt_start_cura)).setText("Dal: " + dateFormat.format(inizio) );
        ((TextView) frame.findViewById(R.id.txt_end_cura)).setText("Fino al: " + dateFormat.format(fine));
        ((TextView) frame.findViewById(R.id.txt_id_hidden)).setText(id + "");
        ((ImageView) frame.findViewById(R.id.imgCura)).setImageDrawable(getResources().getDrawable(getDrawIcons(tipo_cura)));

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopupMenuImages(getContext(), frame);
            }
        });

        linearLayout.addView(frame);
    }

    private Date StringToDate(String s) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        try {

            date = formatter.parse(s);
            Log.i("data: ",formatter.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }


    public void setPopupMenuImages(Context c, final View v) {
        PopupMenu popup = new PopupMenu(c,v);
        popup.getMenuInflater().inflate(R.menu.menu_modifica_cura, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

            if (item.getTitle().equals(MieiFarmaci.MODIFICA)) {

                int id = Integer.parseInt(((TextView) v.findViewById(R.id.txt_id_hidden)).getText().toString());
                Cura modify = getCurabyId(id);
                ImageView cura = v.findViewById(R.id.imgCura);

                ModificaPillola modificaPillola = new ModificaPillola(fab_miei_farmaci, modify);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, modificaPillola).addToBackStack(null).commit();



            } else if (item.getTitle().equals(MieiFarmaci.ELIMINA)) {

                //Toast.makeText(getContext(), "elimina" + ((TextView) v.findViewById(R.id.txt_id_hidden)).getText(), Toast.LENGTH_LONG).show();

                Cura remove_cura = getCurabyId(Integer.parseInt(((TextView) v.findViewById(R.id.txt_id_hidden)).getText().toString()));
                dao.open();
                dao.deleteCura(remove_cura);
                dao.close();

                MieiFarmaci mieiFarmaci = new MieiFarmaci(fab_miei_farmaci);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, mieiFarmaci).addToBackStack(null).commit();


            }
            return true;

            }
        });

        popup.show();
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
