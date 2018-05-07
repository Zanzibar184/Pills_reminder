package com.example.zanzibar.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MieiFarmaci extends Fragment {

    private LinearLayout linearLayout = null;

    public MieiFarmaci() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_miei_farmaci, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutfarmaci);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.myframeviewfarmaci, linearLayout, false);
        TextView txt_title = (TextView) frame.findViewById(R.id.text_title);
        txt_title.setText("Pillola XYZ");
        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("I miei farmaci");
    }

}
