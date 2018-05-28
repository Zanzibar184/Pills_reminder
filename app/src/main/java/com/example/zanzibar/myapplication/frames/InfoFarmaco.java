package com.example.zanzibar.myapplication.frames;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.io.File;
import java.util.List;

public class InfoFarmaco extends Fragment {

    private Cura cura;

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_info = null;

    public InfoFarmaco() {
        // Required empty public constructor
    }

    public InfoFarmaco(FloatingActionButton fab_info, Cura cura) {
        this.fab_info = fab_info;
        this.cura = cura;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_infofarmaco, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fab_info.hide();

        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutinfofarmaco);

        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_info_farmaco, linearLayout, false);

        ((TextView) frame.findViewById(R.id.nome_farmaco_info)).setText(cura.getNome());
        ((TextView) frame.findViewById(R.id.txt_pillole_scatola)).setText("Confezione da: "+ cura.getScorta() + " " + cura.getUnità_misura());
        ((TextView) frame.findViewById(R.id.txt_pillole_rimanenti)).setText("Quantità rimasta: " + cura.getRimanenze() + " " + cura.getUnità_misura());
        ((TextView) frame.findViewById(R.id.txt_dose)).setText("Quantità di assunzione: " + cura.getQuantità_assunzione() + " " + cura.getUnità_misura() + " alle " + cura.getOrario_assunzione());
        ((TextView) frame.findViewById(R.id.txt_inizio_cura)).setText("Cominciata il: " + cura.getInizio_cura());
        ((TextView) frame.findViewById(R.id.txt_termine_cura)).setText("Termina il: " + cura.getFine_cura());

        //TODO: non far crashare nel caso non ci sia la foto nella cura
        if(cura.getFoto() != null){
            File imgFile = new  File(cura.getFoto());

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = (ImageView) frame.findViewById(R.id.imgpillchosen);

                myImage.setImageBitmap(myBitmap);

            }
        }


        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Info farmaco");
    }



}