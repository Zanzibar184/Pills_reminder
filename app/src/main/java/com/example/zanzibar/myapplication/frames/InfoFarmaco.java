package com.example.zanzibar.myapplication.frames;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zanzibar.myapplication.frames.MieiFarmaci.StringToDate;

/*
Schermata con tutti i dettagli di una cura

 */
public class InfoFarmaco extends Fragment {

    private Cura cura;
    private CureDAO dao;
    private LinearLayout linearLayout = null;

    private FloatingActionButton fab_info = null;

    private Button modifica = null;
    private Button elimina = null;

    private ImageView image;

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

        dao = new CureDao_DB();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_infofarmaco, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);
        fab_info.hide();

        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutinfofarmaco);


        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_info_farmaco, linearLayout, false);

        image = (ImageView) frame.findViewById(R.id.imgpillchosen);
        image.setImageDrawable(getResources().getDrawable(Cure.getDrawIcons(cura.getTipo_cura())));

        RelativeLayout view_scorte = (RelativeLayout) frame.findViewById(R.id.myview1);

        int resourceId = cura.getTipo_cura();

        if (resourceId == 1 || resourceId == 7 || resourceId == 8 || resourceId == 9) {
            view_scorte.setVisibility(View.VISIBLE);
            ((TextView) frame.findViewById(R.id.txt_pillole_scatola)).setText("Confezione da: " + cura.getScorta() + " " + cura.getUnità_misura());
            ((TextView) frame.findViewById(R.id.txt_pillole_rimanenti)).setText("Quantità rimasta: " + cura.getRimanenze() + " " + cura.getUnità_misura());
        } else {
            view_scorte.setVisibility(View.GONE);
        }

        ((TextView) frame.findViewById(R.id.nome_farmaco_info)).setText(cura.getNome());
        ((TextView) frame.findViewById(R.id.txt_dose)).setText("Quantità di assunzione: " + cura.getQuantità_assunzione() + " " + cura.getUnità_misura() + " alle " + cura.getOrario_assunzione());

        Date inizio = StringToDate(cura.getInizio_cura());
        Date fine = StringToDate(cura.getFine_cura());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ((TextView) frame.findViewById(R.id.txt_inizio_cura)).setText("Cominciata il: " +dateFormat.format(inizio));
        ((TextView) frame.findViewById(R.id.txt_termine_cura)).setText("Termina il: " + dateFormat.format(fine));

        if(cura.getImportante() == 1){
            ((TextView) frame.findViewById(R.id.txt_sms)).setText(R.string.info_positivo_sms);
        }
        else
        {
            ((TextView) frame.findViewById(R.id.txt_sms)).setText(R.string.info_negativo_sms);
        }


        if(cura.getFoto() != null){
            File imgFile = new  File(cura.getFoto());

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                final ImageView myImage = (ImageView) frame.findViewById(R.id.imgpillchosen);

                myImage.setImageBitmap(myBitmap);

                myImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showFotoFarmaco(cura.getFoto(),myImage);
                    }
                });

            }
        }


        linearLayout.addView(frame);

        modifica = frame.findViewById(R.id.btn_modifica_faramco);
        modifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ModificaPillola modificaPillola = new ModificaPillola(fab_info, cura);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, modificaPillola).addToBackStack(null).commit();

            }
        });

        elimina = frame.findViewById(R.id.btn_delete_farmaco);
        elimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.open();
                deleteNotification(cura.getNome(), cura.getQuantità_assunzione(),cura.getOrario_assunzione());
                dao.deleteCura(cura);
                dao.close();

                Cure cure = new Cure(fab_info);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).addToBackStack(null).commit();

            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_infofarmaco));
    }

    private void showFotoFarmaco(String file, ImageView ivPreview) {
        final Dialog nagDialog = new Dialog(getContext(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.preview_image);
        Button btnClose = (Button)nagDialog.findViewById(R.id.btnIvClose);
        ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
        File f = new File(file);
        ivPreview.setImageURI(Uri.fromFile(f));
        ivPreview.setOnTouchListener(new ImageMatrixTouchHandler(getContext()));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                nagDialog.dismiss();
            }
        });

        nagDialog.show();
    }

    private void deleteNotification(String nome, int quantita,String orario) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        String key = nome + "_" + quantita + "_" + orario;
        SharedPreferences prefs = getContext().getSharedPreferences("MyNotifPref", MODE_PRIVATE);
        int request_code = prefs.getInt(key, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), request_code, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

}
