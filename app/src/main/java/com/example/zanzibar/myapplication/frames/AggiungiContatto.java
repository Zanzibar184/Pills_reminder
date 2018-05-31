package com.example.zanzibar.myapplication.frames;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.example.zanzibar.myapplication.Database.contatti.Contatti;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDAO;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDao_DB;
import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.example.zanzibar.myapplication.frames.AggiungiPillola.REQUEST_PICTURE_CAPTURE;
import static com.example.zanzibar.myapplication.frames.AggiungiPillola.REQUEST_PICTURE_GALLERY;

/**
 * Created by user on 11/05/18.
 */

public class AggiungiContatto extends Fragment {
    private ContattiDAO dao;

    private LinearLayout linearLayout = null;

    private FloatingActionButton fab_contatto = null;

    private String choose_from_camera = "Scatta foto";
    private String choose_from_gallery = "Scegli da galleria";
    private static final int IMPORT_CONTACT_NAME_NUMBER = 5000;

    private static final int GET_SPEECH_CONTACT_NAME = 100;
    private static final int GET_SPEECH_RELATIONSHIP = 200;

    private EditText nomeContatto = null;
    private EditText numeroContatto = null;
    private EditText relazioneContatto = null;

    private Button aggiungiContatto = null;

    private ImageView imgcontact;
    private ImageView img_call_camera;

    private CheckBox check_import;
    private CheckBox check_SMSAVVISO;

    private int id_tipo_foto = 0;

    int sms_avviso = 0;


    //Stringa che ci da il percorso della foto scattata
    protected static String pictureFilePath;
    //Stringa che ci da il percorso della foto presa da galleria
    protected static String pictureGalleryFilePath;

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


        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddcontatto);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_contatto, linearLayout, false);
        linearLayout.addView(frame);

        fab_contatto.hide();

        imgcontact = (ImageView) view.findViewById(R.id.imgcontactphotochosen);

        img_call_camera = (ImageView) view.findViewById(R.id.onclick_camera);

        ImageView img_mic_nome = (ImageView) view.findViewById(R.id.img_mic_nomecontatto);
        ImageView img_mic_rel = (ImageView) view.findViewById(R.id.img_mic_relationship);

        nomeContatto = view.findViewById(R.id.nome_contatto);
        numeroContatto = view.findViewById(R.id.numero_contatto);
        relazioneContatto = view.findViewById(R.id.relazione_contatto);


        /*
        ----coloro di rosso
        GradientDrawable backgroundGradient = new GradientDrawable();
        backgroundGradient.setStroke(5, Color.RED);
        nomeContatto.setBackground(backgroundGradient);
        -----
        */

        aggiungiContatto = (Button) view.findViewById(R.id.btn_conferma_contatto);
        aggiungiContatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dao = new ContattiDao_DB();
                dao.open();

                String nome = nomeContatto.getText().toString();
                String ruolo = relazioneContatto.getText().toString();
                String numero = numeroContatto.getText().toString();





                if(id_tipo_foto == 1) {
                    Contatti contatti = dao.insertContatto(new Contatti(nome,ruolo,numero,pictureFilePath, sms_avviso));
                } else if (id_tipo_foto == 2) {
                    Contatti contatti = dao.insertContatto(new Contatti(nome,ruolo,numero,pictureGalleryFilePath, sms_avviso));
                }else if(id_tipo_foto == 0){
                    Contatti contatti = dao.insertContatto(new Contatti(nome,ruolo,numero, sms_avviso));
                }
                dao.close();

                ContattiImportanti cure = new ContattiImportanti(fab_contatto);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).addToBackStack(null).commit();

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
                    /*
                    nomeContatto.setText("");
                    numeroContatto.setText("");
                    */
                }
            }
        });

        check_SMSAVVISO = (CheckBox) view.findViewById(R.id.checkBox_setContact_SMSAVVISO);
        check_SMSAVVISO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_SMSAVVISO.isChecked())
                    sms_avviso = 1;
                else
                    sms_avviso = 0;
            }
        });

        img_call_camera.setOnClickListener(popupPhotoListener);

        imgcontact.setOnClickListener(popupPhotoListener);

        img_mic_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput(GET_SPEECH_CONTACT_NAME);
            }
        });

        img_mic_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput(GET_SPEECH_RELATIONSHIP);
            }
        });

    }


    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Aggiungi contatto");
    }



    private void setPillImageCapturedFromGallery(Uri pickedImage) {
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

        pictureGalleryFilePath = imagePath;
        id_tipo_foto = 2;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);



        cursor.close();
    }

    private View.OnClickListener popupPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PopupMenu popup = new PopupMenu(getContext(), img_call_camera);
            popup.getMenuInflater().inflate(R.menu.menu_choose_photo, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getTitle().equals(choose_from_camera)) {
                        sendTakePictureIntent();
                    } else if(item.getTitle().equals(choose_from_gallery)) {
                        sendTakeGalleryIntent();
                    }
                    return true;
                }
            });

            popup.show();
        }
    };

    private void sendTakePictureIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        if (cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            //startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);

            File pictureFile = null;
            try {
                pictureFile = getPictureFile();
            } catch (IOException ex) {
                Toast.makeText(getContext(),
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (pictureFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.zanzibar.myapplication.fileprovider",
                        pictureFile);
                id_tipo_foto = 1;
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE);
            }
        }
    }

    private void sendTakeGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_PICTURE_GALLERY);
    }

    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "PILL_" + timeStamp;
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == Activity.RESULT_OK) {
            File imgFile = new  File(pictureFilePath);
            if(imgFile.exists()){
                //Qui settiamo l'immagine del farmaco in aggiungipillola, al momento commentato
                //imgpill.setImageURI(Uri.fromFile(imgFile));
                Log.i("picturefilepath", pictureFilePath+"");
            }
        } else if (requestCode == REQUEST_PICTURE_GALLERY && resultCode == Activity.RESULT_OK) {
            //Qui settiamo l'immagine del farmaco in aggiungipillola, al momento commentato
            Uri pickedImage = data.getData();
            setPillImageCapturedFromGallery(pickedImage);

        } else if (requestCode == IMPORT_CONTACT_NAME_NUMBER && resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            getNameNumberFromContact(getContext(), contactData);
        } else if(requestCode == GET_SPEECH_CONTACT_NAME && resultCode == Activity.RESULT_OK) {
            if(data!=null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                nomeContatto.setText(result.get(0));
            }
        } else if(requestCode == GET_SPEECH_RELATIONSHIP && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                relazioneContatto.setText(result.get(0));
            }
        }

        if(id_tipo_foto == 1) {
            setImage(imgcontact,pictureFilePath);
        } else if (id_tipo_foto == 2) {
            setImage(imgcontact,pictureGalleryFilePath);
        }
    }

    private void setImage(final ImageView img, final String path){

        File imgFile = new  File(path);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            img.setImageBitmap(myBitmap);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFotoFarmaco(path,img);
                }
            });

        }

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

    public void getSpeechInput(int req_code){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (i.resolveActivity(getContext().getPackageManager())!=null){
            startActivityForResult(i,req_code);
        } else {
            Toast.makeText(getContext(), "Ci dispiace, il tuo dispositivo non supporta il riconoscimento vocale", Toast.LENGTH_LONG).show();
        }
    }

}
