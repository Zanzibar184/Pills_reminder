package com.example.zanzibar.myapplication.frames;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.Database.cure.CureDAO;
import com.example.zanzibar.myapplication.Database.cure.CureDao_DB;
import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by user on 07/05/18.
 */

public class AggiungiPillola extends Fragment {
    private CureDAO dao;

    private LinearLayout linearLayout = null;

    FloatingActionButton fab_pills = null;

    private EditText text_date_init = null;
    private EditText text_date_end = null;
    private EditText nome_cura = null;
    private EditText scorte = null;
    private EditText rimanenze = null;

    private Calendar myCalendarinit = null;
    private Calendar myCalendarend = null;
    private DatePickerDialog.OnDateSetListener dateinit = null;
    private DatePickerDialog.OnDateSetListener dateend = null;

    private Button btn_conferma = null;

    private String choose_from_camera = "Scatta foto";
    private String choose_from_gallery = "Scegli da galleria";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    private static final int CAPTURE_IMAGE_FROM_GALLERY_ACTIVITY_REQUEST_CODE = 2888;

    int nClicks = 0;
    RelativeLayout r1;
    RelativeLayout r2;
    RelativeLayout r3;

    ImageView img_call_camera;

    ImageView imgpill;

    private EditText orario_di_assunzione1 = null;
    private EditText orario_di_assunzione2 = null;
    private EditText orario_di_assunzione3 = null;

    private EditText text_dose1 = null;
    private EditText text_dose2 = null;
    private EditText text_dose3 = null;

    Drawable draw;

    int resourceId;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sfondo_aggiungipillola, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayout = (LinearLayout) view.findViewById(R.id.llayoutaddpill);
        View frame = LayoutInflater.from(getActivity()).inflate(R.layout.add_pills_view, linearLayout, false);
        linearLayout.addView(frame);
        r1 = view.findViewById(R.id.myview3_1);
        r2 = view.findViewById(R.id.myview3_2);
        r3 = view.findViewById(R.id.myview3_3);

        fab_pills.hide();

        imgpill = (ImageView) view.findViewById(R.id.imgpillchosen);
        imgpill.setImageDrawable(draw);
        Log.i("resourceId", resourceId+"");
        ImageView img_date_init = (ImageView) view.findViewById(R.id.imgdateinit);
        ImageView img_date_end = (ImageView) view.findViewById(R.id.imgdateend);
        ImageView img_add_pill = (ImageView) view.findViewById(R.id.img_add_dosi);
        ImageView img_time_dose_1 = (ImageView) view.findViewById(R.id.img_time_1);
        ImageView img_time_dose_2 = (ImageView) view.findViewById(R.id.img_time_2);
        ImageView img_time_dose_3 = (ImageView) view.findViewById(R.id.img_time_3);

        img_call_camera = (ImageView) view.findViewById(R.id.onclick_camera);

        text_date_init = (EditText) view.findViewById(R.id.dateinit);
        text_date_end = (EditText) view.findViewById(R.id.dateend);

        orario_di_assunzione1 = (EditText) view.findViewById(R.id.txt_orario_dose1);
        orario_di_assunzione2 = (EditText) view.findViewById(R.id.txt_orario_dose2);
        orario_di_assunzione3 = (EditText) view.findViewById(R.id.txt_orario_dose3);

        text_dose1 = (EditText) view.findViewById(R.id.txt_dose1);
        text_dose2 = (EditText) view.findViewById(R.id.txt_dose2);
        text_dose3 = (EditText) view.findViewById(R.id.txt_dose3);


        nome_cura = view.findViewById(R.id.nome_farmaco);
        scorte = view.findViewById(R.id.scorte);
        rimanenze = view.findViewById(R.id.rimanenze);
        orario_di_assunzione1 = view.findViewById(R.id.txt_orario_dose1);
        orario_di_assunzione2 = view.findViewById(R.id.txt_orario_dose2);
        orario_di_assunzione3 = view.findViewById(R.id.txt_orario_dose3);


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


        btn_conferma = view.findViewById(R.id.btn_conferma_inserimento);
        btn_conferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dao = new CureDao_DB();
                dao.open();

                String nome = nome_cura.getText().toString();

                int scorta = Integer.parseInt(scorte.getText().toString());
                int qta_rimasta = Integer.parseInt(rimanenze.getText().toString());
                String inizio_cura = text_date_init.getText().toString();
                String fine_cura = text_date_end.getText().toString();
                int tipo_cura = resourceId;
                String orario_assunzione = null;
                int qta_ass = 0;



                if(nClicks >= 1)
                {
                    orario_assunzione = orario_di_assunzione1.getText().toString();
                    qta_ass = Integer.parseInt(text_dose1.getText().toString());
                    Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione));
                }
                if(nClicks >= 2)
                {
                    orario_assunzione = orario_di_assunzione2.getText().toString();
                    qta_ass = Integer.parseInt(text_dose2.getText().toString());
                    Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione));
                }
                if(nClicks >= 3)
                {
                    orario_assunzione = orario_di_assunzione3.getText().toString();
                    qta_ass = Integer.parseInt(text_dose3.getText().toString());
                    Cura cura = dao.insertCura(new Cura(nome,qta_ass,scorta,qta_rimasta, inizio_cura, fine_cura,tipo_cura, orario_assunzione));
                }

                dao.close();

                fab_pills.show();
                Cure cure = new Cure(fab_pills);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Aggiungi farmaco");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                setPillImageCapturedFromCamera(bmp);
            }
        } else if (requestCode == CAPTURE_IMAGE_FROM_GALLERY_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                Uri pickedImage = data.getData();
                setPillImageCapturedFromGallery(pickedImage);
            }
        }
    }

    private void setDateInit() {
        myCalendarinit = Calendar.getInstance();
        dateinit = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
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
                // TODO Auto-generated method stub
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

    private void setPillImageCapturedFromCamera(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        // convert byte array to Bitmap

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                byteArray.length);

        imgpill.setImageBitmap(bitmap);
    }

    private void setPillImageCapturedFromGallery(Uri pickedImage) {
        String[] filePath = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
        cursor.moveToFirst();
        String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        imgpill.setImageBitmap(bitmap);

        cursor.close();
    }

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

}
