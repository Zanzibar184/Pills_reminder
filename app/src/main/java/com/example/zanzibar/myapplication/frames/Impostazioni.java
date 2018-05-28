package com.example.zanzibar.myapplication.frames;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.settings.FragmentDatiPersonali;
import com.example.zanzibar.myapplication.settings.FragmentNotifiche;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class Impostazioni extends Fragment {

    private LinearLayout linearLayout = null;

    ImageView img_profile = null;

    private String choose_from_camera = "Scatta foto";
    private String choose_from_gallery = "Scegli da galleria";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int CAPTURE_IMAGE_FROM_GALLERY_ACTIVITY_REQUEST_CODE = 2888;

    RelativeLayout notifiche = null;
    RelativeLayout dati_personali = null;
    RelativeLayout infoapp = null;
    RelativeLayout feedback = null;
    RelativeLayout sincronizzazione = null;

    public Impostazioni() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_impostazioni, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutimpostazioni);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.frame_impostazioni, linearLayout, false);
        linearLayout.addView(frame);

        notifiche = (RelativeLayout) view.findViewById(R.id.myview1);
        dati_personali = (RelativeLayout) view.findViewById(R.id.myview2);
        infoapp = (RelativeLayout) view.findViewById(R.id.myview3);
        feedback = (RelativeLayout) view.findViewById(R.id.myview4);
        sincronizzazione = (RelativeLayout) view.findViewById(R.id.myview5);

        notifiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentNotifiche fragmentNotifiche = new FragmentNotifiche();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentmanager, fragmentNotifiche);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        dati_personali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDatiPersonali datiPersonali = new FragmentDatiPersonali();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, datiPersonali).addToBackStack(null).commit();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedbackMail();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                setProfileImageCapturedFromCamera(bmp);
            }
        } else if (requestCode == CAPTURE_IMAGE_FROM_GALLERY_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Uri pickedImage = data.getData();
                setProfileImageCapturedFromGallery(pickedImage);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Impostazioni");
    }

    private void setProfileImageCapturedFromCamera(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // convert byte array to Bitmap

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);

        img_profile.setImageBitmap(bitmap);
    }

    private void setProfileImageCapturedFromGallery(Uri pickedImage) {
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        img_profile.setImageBitmap(bitmap);

        cursor.close();
    }

    private void sendFeedbackMail() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("application/octet-stream");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pillsreminderapp@app.com"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Pills Reminder");
        startActivity(Intent.createChooser(sendIntent, "Lascia un feedback"));
    }

}
