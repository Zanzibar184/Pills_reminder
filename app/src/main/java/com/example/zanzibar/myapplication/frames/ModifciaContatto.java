package com.example.zanzibar.myapplication.frames;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.example.zanzibar.myapplication.Database.contatti.Contatti;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDAO;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.zanzibar.myapplication.frames.AggiungiPillola.REQUEST_PICTURE_CAPTURE;
import static com.example.zanzibar.myapplication.frames.AggiungiPillola.REQUEST_PICTURE_GALLERY;

/**
 * Created by user on 11/05/18.
 */

public class ModifciaContatto extends Fragment {
    private ContattiDAO dao;

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_contatto = null;

    private String choose_from_camera = "Scatta foto";
    private String choose_from_gallery = "Scegli da galleria";
    private static final int IMPORT_CONTACT_NAME_NUMBER = 5000;

    EditText nomeContatto = null;
    EditText numeroContatto = null;
    EditText relazioneContatto = null;

    Button aggiungiContatto = null;

    ImageView imgcontact;
    ImageView img_call_camera;

    CheckBox check_import;
    CheckBox check_SMSAVVISO;

    int id_tipo_foto = 0;


    //Stringa che ci da il percorso della foto scattata
    protected static String pictureFilePath;
    //Stringa che ci da il percorso della foto presa da galleria
    protected static String pictureGalleryFilePath;

    Contatti contatto;

    public ModifciaContatto() {
        // Required empty public constructor
    }

    public ModifciaContatto(FloatingActionButton fab_contatto, Contatti contatti) {
        this.fab_contatto = fab_contatto;
        this.contatto = contatti;
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

        RelativeLayout r_checkbox = (RelativeLayout) frame.findViewById(R.id.relativeLayoutcheckbox);
        r_checkbox.setVisibility(View.GONE);

        ((ImageView) view.findViewById(R.id.img_mic_nomecontatto)).setVisibility(View.GONE);
        ((ImageView) view.findViewById(R.id.img_mic_relationship)).setVisibility(View.GONE);

        imgcontact = (ImageView) view.findViewById(R.id.imgcontactphotochosen);

        img_call_camera = (ImageView) view.findViewById(R.id.onclick_camera);

        nomeContatto = view.findViewById(R.id.nome_contatto);
        nomeContatto.setText(contatto.getNome());
        numeroContatto = view.findViewById(R.id.numero_contatto);
        numeroContatto.setText(contatto.getNumero());
        relazioneContatto = view.findViewById(R.id.relazione_contatto);
        relazioneContatto.setText(contatto.getRuolo());

        if(contatto.getFoto() != null)
        setImage(imgcontact,contatto.getFoto());

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
                    dao.updateContatto(new Contatti(nome,ruolo,numero,pictureFilePath), contatto.getNumero());
                } else if (id_tipo_foto == 2) {
                    dao.updateContatto(new Contatti(nome,ruolo,numero,pictureGalleryFilePath), contatto.getNumero());
                }else if(id_tipo_foto == 0){
                    dao.updateContatto(new Contatti(nome,ruolo,numero,contatto.getFoto()),contatto.getNumero());
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
                    nomeContatto.setText("");
                    numeroContatto.setText("");
                }
            }
        });

        check_SMSAVVISO = (CheckBox) view.findViewById(R.id.checkBox_setContact_SMSAVVISO);
        check_SMSAVVISO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        img_call_camera.setOnClickListener(popupPhotoListener);

        imgcontact.setOnClickListener(popupPhotoListener);

    }


    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Modifica contatto");
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
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

}
