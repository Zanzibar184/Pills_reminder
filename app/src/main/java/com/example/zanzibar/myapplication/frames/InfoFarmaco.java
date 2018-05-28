package com.example.zanzibar.myapplication.frames;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

public class InfoFarmaco extends Fragment {

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_info = null;

    public InfoFarmaco() {
        // Required empty public constructor
    }

    public InfoFarmaco(FloatingActionButton fab_info) {
        this.fab_info = fab_info;
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

        linearLayout.addView(frame);
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Info farmaco");
    }


}
