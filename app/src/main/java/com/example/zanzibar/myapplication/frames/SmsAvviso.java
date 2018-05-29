package com.example.zanzibar.myapplication.frames;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SmsAvviso extends Fragment {

    private LinearLayout linearLayout = null;

    private LinearLayout layout_contattiSMS = null;

    public SmsAvviso() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_sms_avviso, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutsms);
        layout_contattiSMS = (LinearLayout) view.findViewById(R.id.myview2);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_sms, linearLayout, false);



        linearLayout.addView(frame);
        for(int i = 0; i < 2; i++){
            addLayoutContattiSMS();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("SMS AVVISO");
    }

    private void addLayoutContattiSMS() {
        View frame_contatti = LayoutInflater.from(getActivity()).inflate(R.layout.frame_contatti, layout_contattiSMS, false);
        linearLayout.addView(frame_contatti);
    }

}
