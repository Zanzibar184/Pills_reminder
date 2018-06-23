package com.example.zanzibar.myapplication.frames;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;

import com.example.zanzibar.myapplication.MainActivity;
import com.example.zanzibar.myapplication.PrivacyActivity;
import com.example.zanzibar.myapplication.R;
import com.example.zanzibar.myapplication.WelcomeActivity;

import static android.content.Context.MODE_PRIVATE;

public class FragmentImpostazioni extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    boolean bool = true;
    SharedPreferences.Editor editor = null;
    SharedPreferences prefs = null;
    private String abilitate = "Abilitate";
    private String disabilitate = "Disabilitate";

    SwitchPreferenceCompat notifiche_scorta = null;
    SwitchPreferenceCompat notifiche_SMSAVVISO = null;
    ListPreference tempo_smsavviso = null;
    SwitchPreferenceCompat notifiche_assunzione = null;
    SwitchPreferenceCompat notifiche_note = null;
    ListPreference pillole_scorta = null;
    Preference pref_feed = null;
    Preference pref_infoapp = null;
    Preference pref_privacy = null;



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.pref_settings, rootKey);
        editor = getContext().getSharedPreferences("ImpostazioniNotifiche",MODE_PRIVATE).edit();
        prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);

        //-----------------------------

        notifiche_scorta = (SwitchPreferenceCompat) findPreference("notifiche_scorta_abilitate");
        boolean enable_scorta = prefs.getBoolean("imposta_notifiche_scorta_app",false);
        Log.i("2", enable_scorta+"");
        if(enable_scorta) {
            notifiche_scorta.setChecked(true);
        } else {
            notifiche_scorta.setChecked(false);
        }

        pillole_scorta = (ListPreference) findPreference("pillole_di_scorta");
        String scorta = prefs.getString("pillole_scorta","");
        pillole_scorta.setSummary(scorta);

        notifiche_SMSAVVISO = (SwitchPreferenceCompat) findPreference("notifiche_SMSAVVISO_abilitate");
        boolean enable_sms = prefs.getBoolean("imposta_notifiche_sms",false);
        Log.i("3", enable_sms+"");
        if(enable_sms) {
            notifiche_SMSAVVISO.setChecked(true);
        } else {
            notifiche_SMSAVVISO.setChecked(false);
        }

        tempo_smsavviso = (ListPreference) findPreference("minuti_smsmavviso");
        String minutiSMS = prefs.getString("minuti_smsavviso","");
        tempo_smsavviso.setSummary(minutiSMS);

        notifiche_assunzione = (SwitchPreferenceCompat) findPreference("notifiche_assunzione_farmaci");
        boolean enable_farmaci = prefs.getBoolean("imposta_notifiche_farmaci",false);
        Log.i("4", enable_farmaci+"");
        if(enable_farmaci) {
            notifiche_assunzione.setChecked(true);
        } else {
            notifiche_assunzione.setChecked(false);
        }

        notifiche_note = (SwitchPreferenceCompat) findPreference("notifiche_note");
        boolean enable_note = prefs.getBoolean("imposta_notifiche_note",false);
        Log.i("5", enable_note+"");
        if(enable_note) {
            notifiche_note.setChecked(true);
        } else {
            notifiche_note.setChecked(false);
        }

        pref_feed = (Preference) findPreference("pref_key_feedback");
        pref_infoapp = (Preference) findPreference("pref_key_infoapp");
        pref_privacy = (Preference) findPreference("pref_key_privacy");

        //-----------------------------

        /*
        notifiche_app.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                /*
                Intent intent = new Intent();
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

                //for Android 5-7<<<ssssszs
                intent.putExtra("app_package", getContext().getPackageName());
                intent.putExtra("app_uid", getContext().getApplicationInfo().uid);

                // for Android O
                intent.putExtra("android.provider.extra.APP_PACKAGE", getContext().getPackageName());

                startActivity(intent);

                if(notifiche_app.isChecked()) {
                    editor.putBoolean("imposta_notifiche_app", true);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_app",false);
                    Log.i("notifiche abilitate1_1", b+"");
                } else if (!(notifiche_app.isChecked())){
                    editor.putBoolean("imposta_notifiche_app", false);
                    editor.putBoolean("imposta_notifiche_scorta_app", false);
                    editor.putBoolean("imposta_notifiche_sms", false);
                    editor.putBoolean("imposta_notifiche_farmaci", false);
                    editor.apply();
                    notifiche_scorta.setChecked(false);
                    notifiche_SMSAVVISO.setChecked(false);
                    notifiche_assunzione.setChecked(false);
                    boolean b = prefs.getBoolean("imposta_notifiche_app",false);
                    Log.i("notifiche abilitate1_2", b+"");
                }
                return true;
            }
        });
        */

        notifiche_scorta.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_scorta.isChecked()) {
                    editor.putBoolean("imposta_notifiche_scorta_app", true);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_scorta_app",false);
                    Log.i("notifiche abilitate2_1", b+"");
                } else if (!(notifiche_scorta.isChecked())){
                    editor.putBoolean("imposta_notifiche_scorta_app", false);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_scorta_app",false);
                    Log.i("notifiche abilitate2_2", b+"");
                }
                return true;
            }
        });

        notifiche_SMSAVVISO.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_SMSAVVISO.isChecked()) {
                    editor.putBoolean("imposta_notifiche_sms", true);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_sms",false);
                    Log.i("notifiche abilitate3_1", b+"");
                } else if (!(notifiche_SMSAVVISO.isChecked())){
                    editor.putBoolean("imposta_notifiche_sms", false);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_sms",false);
                    Log.i("notifiche abilitate3_2", b+"");
                }
                return true;
            }
        });

        notifiche_assunzione.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_assunzione.isChecked()) {
                    editor.putBoolean("imposta_notifiche_farmaci", true);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_farmaci",false);
                    Log.i("notifiche abilitate4_1", b+"");
                } else if (!(notifiche_assunzione.isChecked())){
                    editor.putBoolean("imposta_notifiche_farmaci", false);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_farmaci",false);
                    Log.i("notifiche abilitate4_2", b+"");
                }
                return true;
            }
        });

        notifiche_note.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_note.isChecked()) {
                    editor.putBoolean("imposta_notifiche_note", true);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_note",false);
                    Log.i("notifiche abilitate10_1", b+"");
                } else if (!(notifiche_note.isChecked())){
                    editor.putBoolean("imposta_notifiche_note", false);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_note",false);
                    Log.i("notifiche abilitate10_2", b+"");
                }
                return true;
            }
        });

        pref_feed.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                sendFeedbackMail();
                return true;
            }
        });

        pref_infoapp.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences pref_intro = getContext().getSharedPreferences("snow-intro-slider", 0);
                SharedPreferences.Editor editor_intro = pref_intro.edit();
                editor_intro.putBoolean("imposta_info_app", true);
                editor_intro.apply();
                Log.i("pref in infoapp", pref_intro.getBoolean("imposta_info_app", false) + "");
                Intent i = new Intent(getContext(), WelcomeActivity.class);
                startActivity(i);
                return true;
            }
        });

        pref_privacy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent_privacy = new Intent(getContext(), PrivacyActivity.class);
                startActivity(intent_privacy);
                return true;
            }
        });


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /* get preference */
        Preference preference = findPreference(key);

        /* update summary */
        if (key.equals("minuti_smsmavviso")) {
            preference.setSummary(((ListPreference) preference).getEntry());
            CharSequence currText = tempo_smsavviso.getEntry();
            String currValue = tempo_smsavviso.getValue();
            Log.i("1", currText+"");
            Log.i("2", currValue);
            //bug di ListPreference: non aggiorna summary automaticamente!!!!
            //tempo_smsavviso.setSummary(currText);
            editor.putString("minuti_smsavviso", currValue);
            editor.apply();
            SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
            String b = prefs.getString("minuti_smsavviso","");
            Log.i("stringa minuti", b);
        }

        if (key.equals("pillole_di_scorta")) {
            preference.setSummary(((ListPreference) preference).getEntry());
            CharSequence currText = pillole_scorta.getEntry();
            String currValue = pillole_scorta.getValue();
            editor.putString("pillole_scorta", currValue);
            editor.apply();
            SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
            String b = prefs.getString("pillole_scorta","");
            Log.i("stringa pillole scorta", b);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.titolo_impostazioni));
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    private void sendFeedbackMail() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("application/octet-stream");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"pillsreminderapp@app.com"});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback Pills Reminder");
        startActivity(Intent.createChooser(sendIntent, "Lascia un feedback"));
    }


}
