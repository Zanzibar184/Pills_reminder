package com.example.zanzibar.myapplication.frames;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiver;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiverScorte;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static com.example.zanzibar.myapplication.frames.AggiungiPillola.REQUEST_PICTURE_CAPTURE;
import static com.example.zanzibar.myapplication.frames.AggiungiPillola.REQUEST_PICTURE_GALLERY;
import static com.example.zanzibar.myapplication.frames.AggiungiPillola.getIntPillole;
import static com.example.zanzibar.myapplication.frames.AggiungiPillola.pictureFilePath;
import static com.example.zanzibar.myapplication.frames.AggiungiPillola.printDifference;

public class ModificaPillola extends Fragment {
    private CureDAO dao;

    private LinearLayout linearLayout = null;

    Cura modify_cura;

    int id_tipo_foto = 0;

    //Stringa che ci da il percorso della foto scattata
    protected static String pictureFilePath;
    //Stringa che ci da il percorso della foto presa da galleria
    protected static String pictureGalleryFilePath;

    FloatingActionButton fab_pills = null;

    private EditText text_date_init = null;
    private EditText text_date_end = null;
    private EditText nome_cura = null;
    private EditText scorte = null;
    private EditText rimanenze = null;

    private CheckBox check_importante = null;

    Spinner spin1 = null;

    private Calendar myCalendarinit = null;
    private Calendar myCalendarend = null;
    private DatePickerDialog.OnDateSetListener dateinit = null;
    private DatePickerDialog.OnDateSetListener dateend = null;

    private Button btn_conferma = null;

    private String choose_from_camera = "Scatta foto";
    private String choose_from_gallery = "Scegli da galleria";

    RelativeLayout r1;

    ImageView img_call_camera;

    ImageView imgpill;

    private EditText orario_di_assunzione1 = null;

    private EditText text_dose1 = null;

    Drawable draw;

    int resourceId;

    private int importante = 0;


    public ModificaPillola() {
        // Required empty public constructor
    }

    public ModificaPillola(FloatingActionButton fab_pills, Cura modfy_cura) {
        this.fab_pills = fab_pills;
        this.draw = draw;
        this.resourceId = modfy_cura.getTipo_cura();
        this.modify_cura = modfy_cura;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_aggiungipillola, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddpill);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.modify_pills_view, linearLayout, false);
        linearLayout.addView(frame);
        r1 = view.findViewById(R.id.myview3_1);

        fab_pills.hide();


        imgpill = (ImageView) view.findViewById(R.id.imgpillchosen);


        imgpill.setImageDrawable(getResources().getDrawable(Cure.getDrawIcons(modify_cura.getTipo_cura())));


        ImageView img_date_init = (ImageView) view.findViewById(R.id.imgdateinit);
        ImageView img_date_end = (ImageView) view.findViewById(R.id.imgdateend);
        ImageView img_time_dose_1 = (ImageView) view.findViewById(R.id.img_time_1);

        spin1 = (Spinner) view.findViewById(R.id.spin1);

        ArrayAdapter myAdap = (ArrayAdapter) spin1.getAdapter(); //cast to an ArrayAdapter

        int spinnerPosition = myAdap.getPosition(modify_cura.getUnità_misura());

        //set the default according to value
        spin1.setSelection(spinnerPosition);

        img_call_camera = (ImageView) view.findViewById(R.id.img_add_photo);

        text_date_init = (EditText) view.findViewById(R.id.dateinit);
        text_date_init.setText(modify_cura.getInizio_cura());

        text_date_end = (EditText) view.findViewById(R.id.dateend);
        text_date_end.setText(modify_cura.getFine_cura());


        text_dose1 = (EditText) view.findViewById(R.id.txt_dose1);
        text_dose1.setText(modify_cura.getQuantità_assunzione()+"");


        nome_cura = view.findViewById(R.id.nome_farmaco);
        nome_cura.setText(modify_cura.getNome());

        scorte = view.findViewById(R.id.scorte);
        scorte.setText(modify_cura.getScorta() +"");

        rimanenze = view.findViewById(R.id.rimanenze);
        rimanenze.setText(modify_cura.getRimanenze()+"");

        orario_di_assunzione1 = view.findViewById(R.id.txt_orario_dose1);
        orario_di_assunzione1.setText(modify_cura.getOrario_assunzione());

        if(modify_cura.getFoto() != null){
            setImage(imgpill, modify_cura.getFoto());

        }

        deleteNotification(modify_cura.getNome(), modify_cura.getQuantità_assunzione(), modify_cura.getUnità_misura(), modify_cura.getOrario_assunzione(), modify_cura.getInizio_cura(), modify_cura.getFine_cura());


        img_date_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateInit();
                new DatePickerDialog(getContext(), dateinit, myCalendarinit
                        .get(Calendar.YEAR), myCalendarinit.get(Calendar.MONTH),
                        myCalendarinit.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        img_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateEnd();
                new DatePickerDialog(getContext(), dateend, myCalendarend
                        .get(Calendar.YEAR), myCalendarend.get(Calendar.MONTH),
                        myCalendarend.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        text_date_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateInit();
                new DatePickerDialog(getContext(), dateinit, myCalendarinit
                        .get(Calendar.YEAR), myCalendarinit.get(Calendar.MONTH),
                        myCalendarinit.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        text_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateEnd();
                new DatePickerDialog(getContext(), dateend, myCalendarend
                        .get(Calendar.YEAR), myCalendarend.get(Calendar.MONTH),
                        myCalendarend.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        img_call_camera.setOnClickListener(popupPhotoListener);
        imgpill.setOnClickListener(popupPhotoListener);

        img_time_dose_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerDosi(orario_di_assunzione1);
            }
        });




        orario_di_assunzione1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerDosi(orario_di_assunzione1);
            }
        });

        check_importante = view.findViewById(R.id.checkBox);

        if ( modify_cura.getImportante() == 1) {
            check_importante.setChecked(true);
            importante = 1;
        }

        check_importante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_importante.isChecked())
                    importante = 1;
                else
                    importante = 0;
            }
        });




        btn_conferma = view.findViewById(R.id.btn_conferma_inserimento);
        btn_conferma.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view) {

                if((!nome_cura.getText().toString().equals("")) && (!text_dose1.getText().toString().equals("")) && (!text_date_init.getText().toString().equals(""))
                        && (!text_date_end.getText().toString().equals("")) && (!orario_di_assunzione1.getText().toString().equals("")))
                {
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean assumi_farmaco_notifica = prefs.getBoolean("imposta_notifiche_farmaci",false);
                    boolean notifica_scorte = prefs.getBoolean("imposta_notifiche_scorta_app", false);
                    String scorte_pillole = prefs.getString("pillole_scorta","");

                    dao = new CureDao_DB();
                    dao.open();

                    String nome = nome_cura.getText().toString();

                    int scorta;
                    if (!scorte.getText().toString().equals(""))
                        scorta = Integer.parseInt(scorte.getText().toString());
                    else
                        scorta = 0;
                    int qta_rimasta;
                    if (!rimanenze.getText().toString().equals(""))
                        qta_rimasta = Integer.parseInt(rimanenze.getText().toString());
                    else
                        qta_rimasta =0;
                    String inizio_cura = text_date_init.getText().toString();
                    String fine_cura = text_date_end.getText().toString();
                    int tipo_cura = resourceId;
                    String orario_assunzione = null;
                    int qta_ass = Integer.parseInt(text_dose1.getText().toString());
                    orario_assunzione = orario_di_assunzione1.getText().toString();

                    String URI_foto_farmaco = null;
                    if(id_tipo_foto == 1) {
                        URI_foto_farmaco = pictureFilePath;
                    } else if (id_tipo_foto == 2) {
                        URI_foto_farmaco = pictureGalleryFilePath;
                    }

                    String unità_misura = spin1.getSelectedItem().toString();

                    Cura cura_updated = new Cura(
                            nome,
                            qta_ass,
                            scorta,
                            qta_rimasta,
                            inizio_cura,
                            fine_cura,
                            tipo_cura,
                            orario_assunzione,
                            modify_cura.getId(),
                            URI_foto_farmaco,
                            unità_misura,
                            importante
                    );
                    dao.updateCura(cura_updated);
                    dao.reinitDose(cura_updated);


                    if(assumi_farmaco_notifica) {
                        setNotify(nome, qta_ass, unità_misura, orario_assunzione, inizio_cura, fine_cura);
                    }

                    int numero_pillole_rimaste = getIntPillole(scorte_pillole);

                    if(qta_rimasta <= numero_pillole_rimaste && scorta!=0 && notifica_scorte) {
                        setNotifyScorta(nome, scorta, qta_rimasta);
                    }

                    dao.close();

                    fab_pills.show();
                    Cure cure = new Cure(fab_pills);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).addToBackStack(null).commit();
                }
                else
                    colorInputUnfilled();

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_modificapillola));
    }

    private void colorInputUnfilled(){


        GradientDrawable alert = new GradientDrawable();
        alert.setStroke(3, Color.RED);


        if (nome_cura.getText().toString().equals(""))
            nome_cura.setBackground(alert);
        else
            nome_cura.setBackground(null);

        if (text_dose1.getText().toString().equals(""))
            text_dose1.setBackground(alert);
        else
            text_dose1.setBackground(null);

        if (text_date_init.getText().toString().equals(""))
            text_date_init.setBackground(alert);
        else
            text_date_init.setBackground(null);

        if (text_date_end.getText().toString().equals(""))
            text_date_end.setBackground(alert);
        else
            text_date_end.setBackground(null);

        if (orario_di_assunzione1.getText().toString().equals(""))
            orario_di_assunzione1.setBackground(alert);
        else
            orario_di_assunzione1.setBackground(null);
    }

    private void setDateInit() {
        myCalendarinit = Calendar.getInstance();
        dateinit = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarinit.set(Calendar.YEAR, year);
                myCalendarinit.set(Calendar.MONTH, monthOfYear);
                myCalendarinit.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelInit();
            }
        };
    }

    private void setDateEnd() {
        myCalendarend = Calendar.getInstance();
        dateend = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendarend.set(Calendar.YEAR, year);
                myCalendarend.set(Calendar.MONTH, monthOfYear);
                myCalendarend.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
    }

    private void updateLabelInit() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        text_date_init.setText(sdf.format(myCalendarinit.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        text_date_end.setText(sdf.format(myCalendarend.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
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


    private void setTimePickerDosi(EditText editText) {
        final EditText e = editText;
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                //Comunque dobbiamo salvare selectedHour e selectedMinute
                String string_with_minute_hour_zero = "0" + selectedHour + ":0" + selectedMinute;
                String string_with_hour_zero = "0" + selectedHour + ":" + selectedMinute;
                String string_with_minute_zero = selectedHour + ":0" + selectedMinute;
                String string_with_minute_hour = selectedHour + ":" + selectedMinute;
                if(selectedHour>=0 && selectedHour<=9) {
                    if(selectedMinute>=0 && selectedMinute<=9)
                        e.setText(string_with_minute_hour_zero);
                    else
                        e.setText(string_with_hour_zero);
                } else {
                    if(selectedMinute>=0 && selectedMinute<=9)
                        e.setText(string_with_minute_zero);
                    else
                        e.setText(string_with_minute_hour);
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
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

        }

        if(id_tipo_foto == 1) {
            setImage(imgpill,pictureFilePath);
        } else if (id_tipo_foto == 2) {
            setImage(imgpill,pictureGalleryFilePath);
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


    private void setNotify(String nome, int quantità, String unità, String orario, String data_inizio, String data_fine) {

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        dao.open();
        Cura cura_notifica = dao.findCura(nome,data_inizio,data_fine,orario);
        dao.close();

        Random r = new Random();
        int random_value = r.nextInt();
        while(random_value<0) {
            random_value = r.nextInt();
        }
        String key = nome + "_" + quantità + "_" + orario;
        int req_code_int = random_value;

        //int req_code_int = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE + next);

        SharedPreferences.Editor editor = getContext().getSharedPreferences("MyNotifPref",MODE_PRIVATE).edit();
        editor.putInt(key, req_code_int);
        editor.apply();

        SharedPreferences prefs = getContext().getSharedPreferences("MyNotifPref", MODE_PRIVATE);
        int request_code = prefs.getInt(key, 0);

        SharedPreferences.Editor editor2 = getContext().getSharedPreferences("ContatoreGiorniPreferenze",MODE_PRIVATE).edit();
        editor2.putInt(key, 0);
        editor2.apply();

        Date date = null;
        SimpleDateFormat formatdate = new SimpleDateFormat("H:mm");
        Date cal1 = null;
        SimpleDateFormat formatcal1 = new SimpleDateFormat("yyyy-MM-dd");
        Date cal2 = null;
        SimpleDateFormat formatcal2 = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal_app = Calendar.getInstance();
        cal_app.setTimeInMillis(System.currentTimeMillis());
        Date date_app = null;
        SimpleDateFormat formatcalapp = new SimpleDateFormat("yyyy-MM-dd");

        try {
            date = formatdate.parse(orario);
            cal1 = formatcal1.parse(data_inizio);
            cal2 = formatcal2.parse(data_fine);
            date_app = cal_app.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Bundle c = new Bundle();
        c.putString("contenuto", "Ricordati di prendere " + quantità + " " + unità + " di " + nome);
        c.putInt("req_code", request_code);
        c.putInt("n_giorni", (int) ((printDifference(cal1, cal2)) - printDifference(cal1,date_app)) + 1);
        c.putInt("contatore_giorni", 0);
        c.putString("key", key);
        c.putString("cura", cura_notifica.toString());
        intent.putExtras(c);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        int seconds = 0;
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), request_code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000*60*60*24, pendingIntent);

        //alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        Log.i("dati in setNotify()", "key:" + key + " reqcode "  + request_code);

    }

    private void setNotifyScorta(String nome, int scorta, int qta_rimasta){
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiverScorte.class);

        Random r = new Random();
        int random_value = r.nextInt();
        while(random_value<0) {
            random_value = r.nextInt();
        }
        String key = nome + "_" + scorta + "_" + qta_rimasta;
        int req_code_int = random_value;
        //int req_code_int = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        Bundle c = new Bundle();
        c.putString("contenuto", "Rimangono " + qta_rimasta + " su " + scorta + " di " + nome);
        c.putInt("req_code", req_code_int);
        c.putString("key", key);

        intent.putExtras(c);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), req_code_int, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 1000*60, pendingIntent);

    }

    private void deleteNotification(String nome, int quantita, String unita, String orario, String data_inizio, String data_fine) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        String key = nome + "_" + quantita + "_" + orario;
        SharedPreferences prefs = getContext().getSharedPreferences("MyNotifPref", MODE_PRIVATE);
        int request_code = prefs.getInt(key, 0);

        /*
        Bundle c = new Bundle();
        c.putString("titolo", "Hai un farmaco da prendere");
        c.putString("contenuto", "Ricordati di prendere " + quantità + " " + unita + " di " + nome);
        c.putInt("req_code", request_code);
        intent.putExtras(c);
        */

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), request_code, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

}
