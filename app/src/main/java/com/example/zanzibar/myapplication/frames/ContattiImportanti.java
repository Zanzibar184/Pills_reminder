package com.example.zanzibar.myapplication.frames;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.zanzibar.myapplication.Database.contatti.Contatti;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDAO;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.MyBounceInterpolator;
import com.example.zanzibar.myapplication.R;

import java.io.File;
import java.util.List;


/**
 * Schermata per la visualizzazione dei contatti salvati
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

        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);

        fab_contatti.show();

        fab_contatti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.7, 40);
                myAnim.setInterpolator(interpolator);
                fab_contatti.startAnimation(myAnim);
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
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_contattiimportanti));
    }

    private void showContatti(){
        dao.open();

        list_contatti = dao.getAllContatti();

        for(int i=0; i<list_contatti.size();i++){
            Contatti tmp = list_contatti.get(i);
            addLayoutContatti(tmp.getNome(),tmp.getNumero(),tmp.getRuolo(), tmp.getFoto());
        }

        dao.close();

    }

    private void addLayoutContatti(String nome, final String numero, String ruolo, String foto) {
        final View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_contatti, linearLayout, false);

        ((TextView) frame.findViewById(R.id.txt_nome_contatto)).setText(nome);
        ((TextView) frame.findViewById(R.id.txt_ruolo)).setText(ruolo);
        ((TextView) frame.findViewById(R.id.tel_number)).setText(numero);

        ImageView foto_contatto = (ImageView) frame.findViewById(R.id.imgcontatto);

        if(foto != null){
            File imgFile = new  File(foto);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                foto_contatto.setImageBitmap(myBitmap);

            }
        }



        ImageView img_dial = (ImageView) frame.findViewById(R.id.imagePhoneDial);
        img_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numero));
                startActivity(intent);
            }
        });

        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPopupMenuImages(getContext(), frame);
            }
        });

        linearLayout.addView(frame);
    }

    public void setPopupMenuImages(Context c, final View v) {
        Context wrapper = new ContextThemeWrapper(getContext(), R.style.MenuPillsStyle);
        PopupMenu popup = new PopupMenu(wrapper,v);
        popup.getMenuInflater().inflate(R.menu.menu_modifica_contatto, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                Contatti modify = getContattobyId(((TextView) v.findViewById(R.id.tel_number)).getText().toString());

                if (item.getTitle().equals(MieiFarmaci.MODIFICA)) {







                    ModifciaContatto modifciaContatto = new ModifciaContatto(fab_contatti, modify);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentmanager, modifciaContatto).addToBackStack(null).commit();



                } else if (item.getTitle().equals(MieiFarmaci.ELIMINA)) {

                    dao.open();
                    dao.deleteContatto(modify);
                    dao.close();

                    ContattiImportanti contattiImportanti = new ContattiImportanti(fab_contatti);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentmanager, contattiImportanti).addToBackStack(null).commit();

                }
                return true;

            }
        });

        popup.show();
    }

    private Contatti getContattobyId(String numero){

        for(int i=0;i<list_contatti.size(); i++)
        {
            if ((list_contatti.get(i).getNumero()) == numero)
            {
                return list_contatti.get(i);
            }
        }
        Contatti contatti = null;
        return contatti;
    }


}
