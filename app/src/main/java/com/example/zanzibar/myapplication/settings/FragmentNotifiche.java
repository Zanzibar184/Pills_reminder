package com.example.zanzibar.myapplication.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;

import com.example.zanzibar.myapplication.R;

import static android.content.Context.MODE_PRIVATE;

public class FragmentNotifiche extends PreferenceFragmentCompat {

    boolean bool = true;
    SharedPreferences.Editor editor = null;
    SharedPreferences prefs = null;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.pref_notifiche, rootKey);
        editor = getContext().getSharedPreferences("ImpostazioniNotifiche",MODE_PRIVATE).edit();
        prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);

        //-----------------------------

        final SwitchPreferenceCompat notifiche_app = (SwitchPreferenceCompat) findPreference("notifiche_abilitate");
        boolean enable_notifications = prefs.getBoolean("imposta_notifiche_app",false);
        Log.i("1", enable_notifications+"");
        if(enable_notifications) {
            notifiche_app.setChecked(true);
        } else {
            notifiche_app.setChecked(false);
        }

        final SwitchPreferenceCompat notifiche_scorta = (SwitchPreferenceCompat) findPreference("notifiche_scorta_abilitate");
        boolean enable_scorta = prefs.getBoolean("imposta_notifiche_scorta_app",false);
        Log.i("2", enable_scorta+"");
        if(enable_scorta) {
            notifiche_scorta.setChecked(true);
        } else {
            notifiche_scorta.setChecked(false);
        }

        final SwitchPreferenceCompat notifiche_SMSAVVISO = (SwitchPreferenceCompat) findPreference("notifiche_SMSAVVISO_abilitate");
        boolean enable_sms = prefs.getBoolean("imposta_notifiche_sms",false);
        Log.i("3", enable_sms+"");
        if(enable_sms) {
            notifiche_SMSAVVISO.setChecked(true);
        } else {
            notifiche_SMSAVVISO.setChecked(false);
        }

        final ListPreference tempo_smsavviso = (ListPreference) findPreference("minuti_smsmavviso");
        String minutiSMS = prefs.getString("minuti_smsavviso","");
        tempo_smsavviso.setSummary(minutiSMS);

        final SwitchPreferenceCompat notifiche_assunzione = (SwitchPreferenceCompat) findPreference("notifiche_assunzione_farmaci");
        boolean enable_farmaci = prefs.getBoolean("imposta_notifiche_farmaci",false);
        Log.i("4", enable_farmaci+"");
        if(enable_farmaci) {
            notifiche_assunzione.setChecked(true);
        } else {
            notifiche_assunzione.setChecked(false);
        }

        //-----------------------------

        notifiche_app.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_app.isChecked()) {
                    editor.putBoolean("imposta_notifiche_app", true);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_app",false);
                    Log.i("notifiche abilitate1_1", b+"");
                } else if (!(notifiche_app.isChecked())){
                    editor.putBoolean("imposta_notifiche_app", false);
                    editor.apply();
                    boolean b = prefs.getBoolean("imposta_notifiche_app",false);
                    Log.i("notifiche abilitate1_2", b+"");
                }
                return true;
            }
        });

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

        tempo_smsavviso.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                CharSequence currText = tempo_smsavviso.getEntry();
                String currValue = tempo_smsavviso.getValue();
                Log.i("1", currText+"");
                Log.i("2", currValue);
                //bug di ListPreference: non aggiorna summary automaticamente!!!!
                tempo_smsavviso.setSummary(currText);
                editor.putString("minuti_smsavviso", currValue);
                editor.apply();
                SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                String b = prefs.getString("minuti_smsavviso","");
                Log.i("stringa minuti", b);
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


    }

}
