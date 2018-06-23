package com.example.zanzibar.myapplication.frames;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
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
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.Database.cure.Dosi;
import com.example.zanzibar.myapplication.DateHelper;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiver;
import com.example.zanzibar.myapplication.notifiche.AlarmReceiverScorte;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
    Codice per la gestione della schermata per l' inserimento di una cura
 */

public class AggiungiPillola extends Fragment {

    private CureDAO dao;

    private List<Dosi> list_dose_notifica;

    static final int REQUEST_PICTURE_CAPTURE = 1;
    static final int REQUEST_PICTURE_GALLERY = 2;

    private static final int GET_SPEECH_PILL_NAME = 300;
    private static final int GET_SPEECH_SCORTA = 400;
    private static final int GET_SPEECH_RIMANENZA = 500;

    //Stringa che ci da il percorso della foto scattata
    protected static String pictureFilePath;
    //Stringa che ci da il percorso della foto presa da galleria
    protected static String pictureGalleryFilePath;
    //intero che ci dice se la foto è presa da fotocamera o da galleria

    private int id_tipo_foto = 0;

    private int ripetizione = 1;

    private LinearLayout linearLayout = null;

    private LinearLayout view_ripetizione_giorni = null;
    private LinearLayout view_ripetizione_settimana = null;

    private FloatingActionButton fab_pills = null;

    private EditText text_date_init = null;
    private EditText text_date_end = null;
    private EditText nome_cura = null;
    private EditText scorte = null;
    private EditText rimanenze = null;
    private EditText giorni_ripetizione = null;

    private CheckBox check_importante = null;

    private Calendar myCalendarinit = null;
    private Calendar myCalendarend = null;
    private DatePickerDialog.OnDateSetListener dateinit = null;
    private DatePickerDialog.OnDateSetListener dateend = null;

    private Button btn_conferma = null;

    private Button b_lunedi;
    private Button b_martedi;
    private Button b_mercoledi;
    private Button b_giovedi;
    private Button b_venerdi;
    private Button b_sabato;
    private Button b_domenica;

    private ArrayList<String> days_of_week = null;

    private boolean lun = false;
    private boolean mar = false;
    private boolean mer = false;
    private boolean gio = false;
    private boolean ven = false;
    private boolean sab = false;
    private boolean dom = false;

    private int nClicks = 1;
    private RelativeLayout r1;
    private RelativeLayout r2;
    private RelativeLayout r3;

    private ImageView img_call_camera;

    private ImageView imgpill;

    private EditText orario_di_assunzione1 = null;
    private EditText orario_di_assunzione2 = null;
    private EditText orario_di_assunzione3 = null;

    private EditText text_dose1 = null;
    private EditText text_dose2 = null;
    private EditText text_dose3 = null;

    private Spinner spin1 = null;
    private Spinner spin2 = null;
    private Spinner spin3 = null;

    private Drawable draw;

    private int resourceId;

    private int importante = 0;


    public AggiungiPillola() {
        // Required empty public constructor
    }

    public AggiungiPillola(FloatingActionButton fab_pills, Drawable draw, int resourceId) {
        this.fab_pills = fab_pills;
        this.draw = draw;
        this.resourceId = resourceId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.sfondo_aggiungipillola, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Cure.v.setScrollY(0);
        Cure.v.setScrollX(0);


        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddpill);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_pills_view, linearLayout, false);
        linearLayout.addView(frame);
        RelativeLayout view_scorte = (RelativeLayout) view.findViewById(R.id.myview2);
        view_ripetizione_giorni = (LinearLayout) view.findViewById(R.id.llripgiorno);
        view_ripetizione_settimana = (LinearLayout) view.findViewById(R.id.llayout_ripweek);

        if (resourceId == 1 || resourceId == 7 || resourceId == 8 || resourceId == 9) {
            view_scorte.setVisibility(View.VISIBLE);
        } else {
            view_scorte.setVisibility(View.GONE);
        }

        r1 = view.findViewById(R.id.myview3_1);
        r2 = view.findViewById(R.id.myview3_2);
        r3 = view.findViewById(R.id.myview3_3);

        fab_pills.hide();

        imgpill = (ImageView) view.findViewById(R.id.imgpillchosen);
        imgpill.setImageDrawable(draw);
        ImageView img_date_init = (ImageView) view.findViewById(R.id.imgdateinit);
        ImageView img_date_end = (ImageView) view.findViewById(R.id.imgdateend);
        ImageView img_add_pill = (ImageView) view.findViewById(R.id.img_add_dosi);
        ImageView img_remove_dose2 = (ImageView) view.findViewById(R.id.btn_rimuovi_dose2);
        ImageView img_remove_dose3 = (ImageView) view.findViewById(R.id.btn_rimuovi_dose3);
        ImageView img_time_dose_1 = (ImageView) view.findViewById(R.id.img_time_1);
        ImageView img_time_dose_2 = (ImageView) view.findViewById(R.id.img_time_2);
        ImageView img_time_dose_3 = (ImageView) view.findViewById(R.id.img_time_3);

        ImageView img_mic_pillname = (ImageView) view.findViewById(R.id.img_mic_pillname);
        ImageView img_mic_rimanenze = (ImageView) view.findViewById(R.id.img_mic_rimanenza);
        ImageView img_mic_scorta = (ImageView) view.findViewById(R.id.img_mic_scorte);

        img_call_camera = (ImageView) view.findViewById(R.id.img_add_photo);

        text_date_init = (EditText) view.findViewById(R.id.dateinit);
        text_date_end = (EditText) view.findViewById(R.id.dateend);

        orario_di_assunzione1 = (EditText) view.findViewById(R.id.txt_orario_dose1);
        orario_di_assunzione2 = (EditText) view.findViewById(R.id.txt_orario_dose2);
        orario_di_assunzione3 = (EditText) view.findViewById(R.id.txt_orario_dose3);

        giorni_ripetizione = (EditText) view.findViewById(R.id.giorni_ripetizione);

        text_dose1 = (EditText) view.findViewById(R.id.txt_dose1);
        text_dose2 = (EditText) view.findViewById(R.id.txt_dose2);
        text_dose3 = (EditText) view.findViewById(R.id.txt_dose3);

        spin1 = (Spinner) view.findViewById(R.id.spin1);
        spin2 = (Spinner) view.findViewById(R.id.spin2);
        spin3 = (Spinner) view.findViewById(R.id.spin3);


        nome_cura = view.findViewById(R.id.nome_farmaco);
        scorte = view.findViewById(R.id.scorte);
        rimanenze = view.findViewById(R.id.rimanenze);
        orario_di_assunzione1 = view.findViewById(R.id.txt_orario_dose1);
        orario_di_assunzione2 = view.findViewById(R.id.txt_orario_dose2);
        orario_di_assunzione3 = view.findViewById(R.id.txt_orario_dose3);

        b_lunedi = (Button) view.findViewById(R.id.btn_lun);
        b_martedi = (Button) view.findViewById(R.id.btn_mar);
        b_mercoledi = (Button) view.findViewById(R.id.btn_mer);
        b_giovedi = (Button) view.findViewById(R.id.btn_gio);
        b_venerdi = (Button) view.findViewById(R.id.btn_ven);
        b_sabato = (Button) view.findViewById(R.id.btn_sab);
        b_domenica = (Button) view.findViewById(R.id.btn_dom);

        // fine inizializzazione variabili -------<<<<<<

        b_lunedi.setOnClickListener(button_week_manage);
        b_martedi.setOnClickListener(button_week_manage);
        b_mercoledi.setOnClickListener(button_week_manage);
        b_giovedi.setOnClickListener(button_week_manage);
        b_venerdi.setOnClickListener(button_week_manage);
        b_sabato.setOnClickListener(button_week_manage);
        b_domenica.setOnClickListener(button_week_manage);

        img_remove_dose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_dose2.setText("");
                orario_di_assunzione2.setText("");
                r2.setVisibility(View.GONE);
                nClicks--;
            }
        });

        img_remove_dose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_dose3.setText("");
                orario_di_assunzione3.setText("");
                r3.setVisibility(View.GONE);
                nClicks--;
            }
        });

        img_mic_pillname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput(GET_SPEECH_PILL_NAME);
            }
        });

        img_mic_scorta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput(GET_SPEECH_SCORTA);
            }
        });

        img_mic_rimanenze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput(GET_SPEECH_RIMANENZA);
            }
        });

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

        img_add_pill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibilityDosi();
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

        img_time_dose_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerDosi(orario_di_assunzione2);
            }
        });

        img_time_dose_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerDosi(orario_di_assunzione3);
            }
        });


        orario_di_assunzione1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerDosi(orario_di_assunzione1);
            }
        });

        orario_di_assunzione2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerDosi(orario_di_assunzione2);
            }
        });

        orario_di_assunzione3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimePickerDosi(orario_di_assunzione3);
            }
        });

        check_importante = view.findViewById(R.id.checkBox);
        check_importante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_importante.isChecked())
                    importante = 1;
                else
                    importante = 0;
            }
        });

        RadioGroup rgroup = view.findViewById(R.id.radioGroup_ripetizione);
        rgroup.check(R.id.rbtn_giorno);
        view_ripetizione_settimana.setVisibility(View.GONE);
        rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    String s = checkedRadioButton.getText().toString();
                    if (s.equals(getString(R.string.radio_ripeti))) {
                        ripetizione = 1;
                        view_ripetizione_settimana.setVisibility(View.GONE);
                        view_ripetizione_giorni.setVisibility(View.VISIBLE);
                    } else if (s.equals(getString(R.string.radio_settimana))) {
                        ripetizione = 2;
                        view_ripetizione_settimana.setVisibility(View.VISIBLE);
                        view_ripetizione_giorni.setVisibility(View.GONE);
                    }
                }
            }
        });


        btn_conferma = view.findViewById(R.id.btn_conferma_inserimento);
        btn_conferma.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

                SharedPreferences prefs = getContext().getSharedPreferences(getString(R.string.imp_notifiche), MODE_PRIVATE);
                boolean assumi_farmaco_notifica = prefs.getBoolean(getString(R.string.imp_not_farmaci),false);
                boolean notifica_scorte = prefs.getBoolean(getString(R.string.imp_not_scorta), false);
                String scorte_pillole = prefs.getString(getString(R.string.pillole_scorta),"");


                //controllo che gli input richiesti siano riempiti, altrimenti impedisco inserimento di record nel db
                if((!nome_cura.getText().toString().equals("")) && (!text_dose1.getText().toString().equals("")) && (!text_date_init.getText().toString().equals(""))
                        && (!text_date_end.getText().toString().equals("")) && (!orario_di_assunzione1.getText().toString().equals(""))
                        && ((!giorni_ripetizione.getText().toString().equals("") || (( lun || mar || mer || gio || ven || sab || dom)))))

                {
                    dao = new CureDao_DB();

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


                    String URI_foto_farmaco = null;
                    if(id_tipo_foto == 1) {
                        URI_foto_farmaco = pictureFilePath;
                    } else if (id_tipo_foto == 2) {
                        URI_foto_farmaco = pictureGalleryFilePath;
                    }
                    //fine
                    String unita_misura_dose = null;

                    days_of_week = new ArrayList<String>();

                    int n_giorni_ripetizione = 0;

                    if (ripetizione == 1 && !giorni_ripetizione.getText().toString().equals("")) {
                        n_giorni_ripetizione = Integer.parseInt(giorni_ripetizione.getText().toString());
                    } else if (ripetizione == 2) {





                        if (lun) {
                            days_of_week.add(DateHelper.getDaySystem(0));
                        }if (mar) {
                            days_of_week.add(DateHelper.getDaySystem(1));
                        }if (mer) {
                            days_of_week.add(DateHelper.getDaySystem(2));
                        }if (gio) {
                            days_of_week.add(DateHelper.getDaySystem(3));
                        }if (ven) {
                            days_of_week.add(DateHelper.getDaySystem(4));
                        }if (sab) {
                            days_of_week.add(DateHelper.getDaySystem(5));
                        }if (dom) {
                            days_of_week.add(DateHelper.getDaySystem(6));
                        }
                    }

                    int qta_ass;
                    if(nClicks >= 1)
                    {
                        orario_assunzione = orario_di_assunzione1.getText().toString();
                        qta_ass = Integer.parseInt(text_dose1.getText().toString());
                        unita_misura_dose = spin1.getSelectedItem().toString();
                        dao.open();
                        if(ripetizione == 1){
                            Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione, URI_foto_farmaco, unita_misura_dose, importante, n_giorni_ripetizione +""), n_giorni_ripetizione);

                        }
                        else if (ripetizione ==2){
                            Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione, URI_foto_farmaco, unita_misura_dose, importante, Cura.parseRipetizione(days_of_week)), days_of_week);
                        }
                        dao.close();
                        if(assumi_farmaco_notifica) {
                            setNotify2(nome, qta_ass, unita_misura_dose, orario_assunzione, inizio_cura, fine_cura);
                        }

                    }
                    if(nClicks >= 2)
                    {
                        orario_assunzione = orario_di_assunzione2.getText().toString();
                        qta_ass = Integer.parseInt(text_dose2.getText().toString());
                        unita_misura_dose = spin2.getSelectedItem().toString();
                        dao.open();
                        if(ripetizione == 1){
                            Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione, URI_foto_farmaco, unita_misura_dose, importante, n_giorni_ripetizione +""), n_giorni_ripetizione);

                        }
                        else if (ripetizione ==2){
                            Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione, URI_foto_farmaco, unita_misura_dose, importante, Cura.parseRipetizione(days_of_week)), days_of_week);
                        }
                        dao.close();
                        if(assumi_farmaco_notifica) {
                            setNotify2(nome, qta_ass, unita_misura_dose, orario_assunzione, inizio_cura, fine_cura);
                        }
                    }
                    if(nClicks >= 3)
                    {
                        orario_assunzione = orario_di_assunzione3.getText().toString();
                        qta_ass = Integer.parseInt(text_dose3.getText().toString());
                        unita_misura_dose = spin3.getSelectedItem().toString();
                        dao.open();
                        if(ripetizione == 1){
                            Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione, URI_foto_farmaco, unita_misura_dose, importante, n_giorni_ripetizione +""), n_giorni_ripetizione);

                        }
                        else if (ripetizione ==2){
                            Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione, URI_foto_farmaco, unita_misura_dose, importante, Cura.parseRipetizione(days_of_week)), days_of_week);
                        }
                        dao.close();
                        if(assumi_farmaco_notifica) {
                            setNotify2(nome, qta_ass, unita_misura_dose, orario_assunzione, inizio_cura, fine_cura);
                        }

                    }

                    int numero_pillole_rimaste = getIntPillole(scorte_pillole);

                    if(qta_rimasta <= numero_pillole_rimaste && scorta!=0 && notifica_scorte) {
                        setNotifyScorta(nome, scorta, qta_rimasta, inizio_cura, fine_cura, orario_assunzione);
                    }

                    //dao.close();

                    fab_pills.show();
                    Cure cure = new Cure(fab_pills);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).addToBackStack(null).commit();
                }
                else
                    colorInputUnfilled();


            View hide_keyboard = getActivity().getCurrentFocus();
            if (hide_keyboard != null) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(hide_keyboard.getWindowToken(), 0);
            }


            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_aggiungifarmaco));
    }

    public static int getIntPillole(String s) {
        int res = 0;
        switch (s) {
            case "2 pillole": res=2; break;
            case "5 pillole": res=5; break;
            case "10 pillole": res=10; break;
            case "15 pillole": res=15; break;
            case "20 pillole": res=20; break;
            case "30 pillole": res=30; break;
        }
        return res;
    }

    //colora di rosso gli input importanti rimasti vuoti
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

        if(giorni_ripetizione.getText().toString().equals(""))
            giorni_ripetizione.setBackground(alert);
        else
            giorni_ripetizione.setBackground(null);

        if( !lun && !mar && !mer && !gio && !ven && !sab && !dom)
            view_ripetizione_settimana.setBackground(alert);
        else
            view_ripetizione_settimana.setBackground(null);
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
        String myFormat = getString(R.string.date_format_base); //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        text_date_init.setText(sdf.format(myCalendarinit.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelEnd() {
        String myFormat = getString(R.string.date_format_base); //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ITALIAN);
        text_date_end.setText(sdf.format(myCalendarend.getTime()));
        //text_date_end.setText(sdf.format(myCalendar.getTime()));
    }

    //setto come immagine quella che ho preso dalla galleria
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

        imgpill.setImageBitmap(bitmap);

        cursor.close();
    }

    //gestisco la visualizzazione dei layout delle varie dosi a seconda di quante volte l'utente clicca sul bottone di aggiunta
    private void setVisibilityDosi() {
        nClicks++;
        if(nClicks==1){
            r1.setVisibility(View.VISIBLE);
        } else if(nClicks==2) {
            r2.setVisibility(View.VISIBLE);
        } else if(nClicks==3) {
            r3.setVisibility(View.VISIBLE);
        }

        if(nClicks > 3)
            nClicks = 3;
    }

    //avvia il timepicker per ottenere l'orario a cui settare l'assunzione dei farmaci
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

    //listener per scegliere di scattare una foto del farmaco con la fotocamera o prenderla da galleria
    private View.OnClickListener popupPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PopupMenu popup = new PopupMenu(getContext(), img_call_camera);
            popup.getMenuInflater().inflate(R.menu.menu_choose_photo, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if(item.getTitle().equals(getString(R.string.foto))) {
                        sendTakePictureIntent();
                    } else if(item.getTitle().equals(getString(R.string.galleria))) {
                        sendTakeGalleryIntent();
                    }
                    return true;
                }
            });

            popup.show();
        }
    };

    //metodo che fa partire un intent per avviare la fotocamera per scattare una foto del farmaco
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

    //metodo che fa partire un intent per avviare la galleria per scegliere un'immagine del farmaco
    private void sendTakeGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_PICTURE_GALLERY);
    }

    //creo il path per l'immagine scattata dalla fotocamera
    private File getPictureFile() throws IOException {
        String timeStamp = new SimpleDateFormat(getString(R.string.picture_date_format)).format(new Date());
        String pictureFile = "PILL_" + timeStamp;
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile,  ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        return image;
    }

    //gestisco i risultati dei vari intent, a seconda che siano relativi alla fotocamera, alla galleria o all'input vocale
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == Activity.RESULT_OK) {
            File imgFile = new  File(pictureFilePath);
            if(imgFile.exists()){
                //Qui settiamo l'immagine del farmaco in aggiungipillola, al momento commentato
                imgpill.setImageURI(Uri.fromFile(imgFile));
            }
        } else if (requestCode == REQUEST_PICTURE_GALLERY && resultCode == Activity.RESULT_OK) {
            //Qui settiamo l'immagine del farmaco in aggiungipillola, al momento commentato
            Uri pickedImage = data.getData();
            setPillImageCapturedFromGallery(pickedImage);
        } else if(requestCode == GET_SPEECH_PILL_NAME && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                nome_cura.setText(result.get(0));
            }
        } else if(requestCode == GET_SPEECH_RIMANENZA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                rimanenze.setText(result.get(0));
            }
        } else if(requestCode == GET_SPEECH_SCORTA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                scorte.setText(result.get(0));
            }
        }

    }

    public void getSpeechInput(int req_code){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (i.resolveActivity(getContext().getPackageManager())!=null){
            startActivityForResult(i,req_code);
        } else {
            Toast.makeText(getContext(), getString(R.string.non_support_speechapi), Toast.LENGTH_LONG).show();
        }
    }

    private void setNotify2(String nome, int quantità, String unità, String orario, String data_inizio, String data_fine) {


        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        dao.open();
        Cura cura_notifica = dao.findCura(nome,data_inizio,data_fine,orario);
        list_dose_notifica = dao.getDosiById(cura_notifica.getId());
        dao.close();




        for(int i = 0; i < list_dose_notifica.size(); i++){
            Dosi tmp = list_dose_notifica.get(i);

            String myDate = tmp.getGiorno() + " " + orario;
            Date date_notifica = DateHelper.StringtoDate(myDate,getString(R.string.notify_date_format));

            Calendar cal_notifica = Calendar.getInstance();
            cal_notifica.setTime(date_notifica);

            Random r = new Random();
            int random_value = r.nextInt();
            while(random_value<0) {
                random_value = r.nextInt();
            }
            String key = nome + "_" + quantità + "_" + orario;
            int req_code_int = random_value;

            SharedPreferences.Editor editor = getContext().getSharedPreferences(getString(R.string.pref_name),MODE_PRIVATE).edit();
            editor.putInt(key, req_code_int);
            editor.apply();

            SharedPreferences prefs = getContext().getSharedPreferences(getString(R.string.pref_name), MODE_PRIVATE);
            int request_code = prefs.getInt(key, 0);

            Bundle c = new Bundle();
            c.putString(getString(R.string.pref_content), "Ricordati di prendere " + quantità + " " + unità + " di " + nome);
            c.putInt(getString(R.string.pref_req_code), request_code);
            c.putString(getString(R.string.pref_key), key);
            c.putString(getString(R.string.pref_cura_record), cura_notifica.toString());
            intent.putExtras(c);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), request_code, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal_notifica.getTimeInMillis() , pendingIntent);


        }


    }

    //gestisco e avvio le notifiche per i farmaci
    private void setNotifyScorta(String nome, int scorta, int qta_rimasta, String data_inizio, String data_fine, String orario) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiverScorte.class);

        dao.open();
        Cura cura_scorta = dao.findCura(nome, data_inizio, data_fine, orario);
        dao.close();

        Random r = new Random();
        int random_value = r.nextInt();
        while(random_value<0) {
            random_value = r.nextInt();
        }
        String key = nome + "_" + scorta + "_" + qta_rimasta;
        int req_code_int = random_value;
        //int req_code_int = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        Bundle c = new Bundle();
        c.putString(getString(R.string.pref_content), "Rimangono " + qta_rimasta + " su " + scorta + " di " + nome);
        c.putInt(getString(R.string.pref_req_code), req_code_int);
        c.putString(getString(R.string.pref_key), key);
        c.putString(getString(R.string.pref_cura_record), cura_scorta.toString());

        intent.putExtras(c);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), req_code_int, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() + 1000*60, pendingIntent);

    }

    //restituisce il numero di giorni che stanno in un intervallo di date
    public static long printDifference(Date startDate, Date endDate){

        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        return elapsedDays;
    }

    //listener che gestisce i giorni della settimana che sono stati scelti impostando la cura
    private View.OnClickListener button_week_manage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectDay(view);
        }
    };

    //cambia il colore e lo stato dei bottoni che si occupano dei giorni della settimana quando si clicca su di essi
    public void selectDay(View view) {
        switch (view.getId()) {
            case R.id.btn_lun:
                if (lun) {
                    lun = false;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
                } else if (!lun) {
                    lun = true;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_selected));
                }
                break;
            case R.id.btn_mar:
                if (mar) {
                    mar = false;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
                } else if (!mar) {
                    mar = true;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_selected));
                }
                break;
            case R.id.btn_mer:
                if (mer) {
                    mer = false;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
                } else if (!mer) {
                    mer = true;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_selected));
                }
                break;
            case R.id.btn_gio:
                if (gio) {
                    gio = false;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
                } else if (!gio) {
                    gio = true;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_selected));
                }
                break;
            case R.id.btn_ven:
                if (ven) {
                    ven = false;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
                } else if (!ven) {
                    ven = true;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_selected));
                }
                break;
            case R.id.btn_sab:
                if (sab) {
                    sab = false;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
                } else if (!sab) {
                    sab = true;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_selected));
                }
                break;
            case R.id.btn_dom:
                if (dom) {
                    dom = false;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button));
                } else if (!dom) {
                    dom = true;
                    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.round_button_selected));
                }
                break;
        }
    }


}
