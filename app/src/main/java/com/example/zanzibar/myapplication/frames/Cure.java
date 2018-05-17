package com.example.zanzibar.myapplication.frames;


import android.content.Context;
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

    final int numberOfFrames = 4;

    String conferma_farmaco = "Conferma assunzione farmaco";
    String non_conferma_farmaco = "Farmaco non assunto";
    String ripristina_stato_farmaco = "Posticipa";

    FloatingActionButton fab_cure = null;

    ImageView img_farmaco = null;
    ImageView img_iniezione = null;
    ImageView img_pomata = null;
    ImageView img_gocce = null;

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

        img_farmaco = (ImageView) view.findViewById(R.id.img_farmaco);
        img_iniezione = (ImageView) view.findViewById(R.id.img_iniezione);
        img_pomata = (ImageView) view.findViewById(R.id.img_pomata);
        img_gocce = (ImageView) view.findViewById(R.id.img_gocce);

        ImageView list_img[] = {img_farmaco, img_iniezione, img_pomata, img_gocce};

        for(ImageView i : list_img) {
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPopupMenuImages(getContext());
                }
            });
        }

    }

    public void addLayoutCure(int n) {
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_cure, linearLayout, false);
        TextView txt_title = (TextView) frame.findViewById(R.id.text_title);
            if (n == 1) {
                txt_title.setText("Mattina");
            } else if (n == 2) {
                txt_title.setText("Pomeriggio");
                //frame.setVisibility(View.GONE);
            } else if (n == 3) {
                txt_title.setText("Sera");
            } else if(n == 4){
                txt_title.setText("Notte");
            }

        linearLayout.addView(frame);
    }

    public void setPopupMenuImages(Context c) {
        PopupMenu popup = new PopupMenu(c, img_farmaco);
        popup.getMenuInflater().inflate(R.menu.menu_conferma_assunzione, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals(conferma_farmaco)) {
                    Log.i("farmaco si", "ok");
                } else if (item.getTitle().equals(non_conferma_farmaco)) {
                    Log.i("farmaco no", "ok");
                } else if (item.getTitle().equals(ripristina_stato_farmaco)) {
                    Log.i("posticipa", "ok");
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
