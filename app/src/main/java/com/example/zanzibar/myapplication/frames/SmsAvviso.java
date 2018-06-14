package com.example.zanzibar.myapplication.frames;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.Database.contatti.Contatti;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDAO;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmsAvviso extends Fragment {

    private LinearLayout linearLayout = null;
    private LinearLayout linearLayoutNumeri = null;

    ImageView modifySMS = null;
    ImageView saveSMS = null;

    EditText editSMS = null;

    ContattiDAO dao;
    List<Contatti> list_contatti;

    public SmsAvviso() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dao = new ContattiDao_DB();
        dao.open();
        list_contatti = dao.getContattiImportanti();
        dao.close();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_sms_avviso, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutsms);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_sms, linearLayout, false);

        modifySMS = (ImageView) frame.findViewById(R.id.modify_sms_text);
        saveSMS = (ImageView) frame.findViewById(R.id.save_sms_text);

        editSMS = (EditText) frame.findViewById(R.id.contenuto_SMS);

        modifySMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSMS.setVisibility(View.VISIBLE);
                editSMS.setFocusable(true);
                editSMS.setFocusableInTouchMode(true);
                editSMS.setClickable(true);
            }
        });

        saveSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String testo = editSMS.getText().toString();
                Log.i("Testo sms", testo);
                editSMS.setFocusable(false);
                editSMS.setFocusableInTouchMode(false);
                editSMS.setClickable(false);
                saveSMS.setVisibility(View.GONE);
            }
        });

        linearLayoutNumeri = (LinearLayout) frame.findViewById(R.id.layout_numeri_avvisare);



        for (int i = 0; i < list_contatti.size(); i++){
            Contatti tmp = list_contatti.get(i);
            addLayoutNumeri(tmp);
        }


        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_smsavviso));
    }

    private void addLayoutNumeri(Contatti contatti) {
        final View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_numeri_da_avvvisare, linearLayoutNumeri, false);
        TextView info = (TextView) frame.findViewById(R.id.txt_contattoSMS);
        info.setText(contatti.toString());
        linearLayoutNumeri.addView(frame);
    }

}
