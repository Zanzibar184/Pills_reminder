package com.example.zanzibar.myapplication.frames;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 11/05/18.
 */

public class AggiungiContatto extends Fragment {

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_contatto = null;

    private String choose_from_camera = "Scatta foto";
    private String choose_from_gallery = "Scegli da galleria";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int CAPTURE_IMAGE_FROM_GALLERY_ACTIVITY_REQUEST_CODE = 2888;
    private static final int IMPORT_CONTACT_NAME_NUMBER = 5000;

    EditText nomeContatto = null;
    EditText numeroContatto = null;

    Button aggiungiContatto = null;
    Button importaContatto = null;

    ImageView imgcontact;
    ImageView img_call_camera;

    CheckBox check_import;
    CheckBox check_SMSAVVISO;

    public AggiungiContatto() {
        // Required empty public constructor
    }

    public AggiungiContatto(FloatingActionButton fab_contatto) {
        this.fab_contatto = fab_contatto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_aggiungicontatto, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddcontatto);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_contatto, linearLayout, false);
        linearLayout.addView(frame);

        fab_contatto.hide();

        imgcontact = (ImageView) view.findViewById(R.id.imgcontactphotochosen);

        img_call_camera = (ImageView) view.findViewById(R.id.onclick_camera);

        nomeContatto = view.findViewById(R.id.nome_contatto);
        numeroContatto = view.findViewById(R.id.numero_contatto);

        aggiungiContatto = (Button) view.findViewById(R.id.btn_conferma_contatto);
        aggiungiContatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        check_import = (CheckBox) view.findViewById(R.id.checkBox_importa_contatto);
        check_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_import.isChecked()) {
                    Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                    startActivityForResult(pickContact, IMPORT_CONTACT_NAME_NUMBER);
                } else {
                    nomeContatto.setText("");
                    numeroContatto.setText("");
                }
            }
        });

        check_SMSAVVISO = (CheckBox) view.findViewById(R.id.checkBox_setContact_SMSAVVISO);
        check_SMSAVVISO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: implementare metodo per agiungere contatto al servizio SMS AVVISO
            }
        });

        img_call_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), img_call_camera);
                popup.getMenuInflater().inflate(R.menu.menu_choose_photo, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals(choose_from_camera)) {
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture,
                                    CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                        } else if(item.getTitle().equals(choose_from_gallery)) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto,
                                    CAPTURE_IMAGE_FROM_GALLERY_ACTIVITY_REQUEST_CODE);
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                setContactImageCapturedFromCamera(bmp);
            }
        } else if (requestCode == CAPTURE_IMAGE_FROM_GALLERY_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Uri pickedImage = data.getData();
                setContactImageCapturedFromGallery(pickedImage);
            }
        } else if (requestCode == IMPORT_CONTACT_NAME_NUMBER)
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                getNameNumberFromContact(getContext(),contactData);
            }
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Aggiungi contatto");
    }

    public void getNameNumberFromContact(Context c, Uri contactData) {
        Cursor cursor = c.getContentResolver().query(contactData, null, null, null, null);
        if (cursor.moveToFirst()) {
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String num = cursor.getString(phoneIndex);
            String nome = cursor.getString(nameIndex);
            nomeContatto.setText(nome);
            numeroContatto.setText(num);
        }
    }

    private void setContactImageCapturedFromCamera(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // convert byte array to Bitmap

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);

        imgcontact.setImageBitmap(bitmap);
    }

    private void setContactImageCapturedFromGallery(Uri pickedImage) {
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        imgcontact.setImageBitmap(bitmap);

        cursor.close();
    }

}
