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
import android.widget.TextView;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cure extends Fragment {

    private LinearLayout linearLayout = null;
    private LinearLayout layout_pills = null;

    final int numberOfFrames = 4;

    String conferma_farmaco = "Conferma assunzione farmaco";
    String non_conferma_farmaco = "Farmaco non assunto";
    String ripristina_stato_farmaco = "Posticipa";

    FloatingActionButton fab_cure = null;
    /*
    ImageView img_farmaco = null;
    ImageView img_iniezione = null;
    ImageView img_pomata = null;
    ImageView img_gocce = null;
    ImageView img_greenV = null;
    ImageView img_redX = null;
    */

    public Cure() {
        // Required empty public constructor
    }

    public Cure(FloatingActionButton fab_cure) {
        this.fab_cure = fab_cure;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        /*
        img_farmaco = (ImageView) view.findViewById(R.id.img_farmaco);
        img_iniezione = (ImageView) view.findViewById(R.id.img_iniezione);
        img_pomata = (ImageView) view.findViewById(R.id.img_pomata);
        img_gocce = (ImageView) view.findViewById(R.id.img_gocce);

        img_greenV = (ImageView) view.findViewById(R.id.img_greenV);
        img_redX = (ImageView) view.findViewById(R.id.img_redX);

        ImageView list_img[] = {img_farmaco, img_iniezione, img_pomata, img_gocce};

        for(ImageView i : list_img) {
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPopupMenuImages(getContext());
                }
            });
        }
        */

    }

    public void addLayoutCure(int n) {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_cure, linearLayout, false);
        layout_pills = (LinearLayout) frame.findViewById(R.id.llayout_view_farmaco);
        TextView txt_title = (TextView) frame.findViewById(R.id.text_title);
            if (n == 1) {
                txt_title.setText("Mattina");
                addLayoutFarmaco(2);
            } else if (n == 2) {
                txt_title.setText("Pomeriggio");
                addLayoutFarmaco(4);
                //frame.setVisibility(View.GONE);
            } else if (n == 3) {
                txt_title.setText("Sera");
                addLayoutFarmaco(1);
            } else if(n == 4){
                txt_title.setText("Notte");
                addLayoutFarmaco(3);
            }

        linearLayout.addView(frame);
    }

    public void addLayoutFarmaco (int n) { // n è il numero di farmaco per mattina/pomeriggio/sera/notte
        for(int i = 0; i < n; i++){
            View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_pillola, layout_pills, false);
            final ImageView img_farmaco = (ImageView) frame.findViewById(R.id.img_farmaco);
            final ImageView img_greenV = (ImageView) frame.findViewById(R.id.img_greenV);
            final ImageView img_redX = (ImageView) frame.findViewById(R.id.img_redX);

            //Qui va il codice per le altre TextView e altro da gestire

            if(i==1) {
                Drawable drawable_farmaco = getResources().getDrawable(R.drawable.sciroppo);
                img_farmaco.setImageDrawable(drawable_farmaco);
            } else if(i==3) {
                Drawable drawable_farmaco = getResources().getDrawable(R.drawable.gocce);
                img_farmaco.setImageDrawable(drawable_farmaco);
            }
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("frame click", "hello clicker");
                    setPopupMenuImages(getContext(),img_farmaco, img_greenV, img_redX);
                }
            });
            layout_pills.addView(frame);
        }
    }


    public void setPopupMenuImages(Context c, final ImageView imgfarmaco, final ImageView imgok, final ImageView imgno) {
        PopupMenu popup = new PopupMenu(c,imgfarmaco);
        popup.getMenuInflater().inflate(R.menu.menu_conferma_assunzione, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals(conferma_farmaco)) {
                    Log.i("farmaco si", "ok");
                    imgok.setVisibility(View.VISIBLE);
                    imgno.setVisibility(View.GONE);
                } else if (item.getTitle().equals(non_conferma_farmaco)) {
                    Log.i("farmaco no", "ok");
                    imgok.setVisibility(View.GONE);
                    imgno.setVisibility(View.VISIBLE);
                } else if (item.getTitle().equals(ripristina_stato_farmaco)) {
                    Log.i("posticipa", "ok");
                    imgok.setVisibility(View.GONE);
                    imgno.setVisibility(View.GONE);
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

}
