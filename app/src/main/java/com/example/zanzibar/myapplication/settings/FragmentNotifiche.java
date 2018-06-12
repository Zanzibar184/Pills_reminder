package com.example.zanzibar.myapplication.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.zanzibar.myapplication.R;

import static android.content.Context.MODE_PRIVATE;

public class FragmentNotifiche extends PreferenceFragmentCompat {

    boolean bool = true;
    SharedPreferences.Editor editor = null;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.pref_notifiche, rootKey);
        editor = getContext().getSharedPreferences("ImpostazioniNotifiche",MODE_PRIVATE).edit();

        //-----------------------------

        final SwitchPreferenceCompat notifiche_app = (SwitchPreferenceCompat) findPreference("notifiche_abilitate");
        notifiche_app.setChecked(true);
        notifiche_app.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_app.isChecked()) {
                    editor.putBoolean("imposta_notifiche_app", true);
                    editor.apply();
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean b = prefs.getBoolean("imposta_notifiche_app",false);
                    Log.i("notifiche abilitate1_1", b+"");
                } else if (!(notifiche_app.isChecked())){
                    editor.putBoolean("imposta_notifiche_app", false);
                    editor.apply();
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean b = prefs.getBoolean("imposta_notifiche_app",false);
                    Log.i("notifiche abilitate1_2", b+"");
                }
                return true;
            }
        });

        //-----------------------------

        final SwitchPreferenceCompat notifiche_scorta = (SwitchPreferenceCompat) findPreference("notifiche_scorta_abilitate");
        notifiche_scorta.setChecked(true);
        notifiche_scorta.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_scorta.isChecked()) {
                    editor.putBoolean("imposta_notifiche_scorta_app", true);
                    editor.apply();
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean b = prefs.getBoolean("imposta_notifiche_scorta_app",false);
                    Log.i("notifiche abilitate2_1", b+"");
                } else if (!(notifiche_scorta.isChecked())){
                    editor.putBoolean("imposta_notifiche_scorta_app", false);
                    editor.apply();
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean b = prefs.getBoolean("imposta_notifiche_scorta_app",false);
                    Log.i("notifiche abilitate2_2", b+"");
                }
                return true;
            }
        });

        //-----------------------------

        final SwitchPreferenceCompat notifiche_SMSAVVISO = (SwitchPreferenceCompat) findPreference("notifiche_SMSAVVISO_abilitate");
        notifiche_SMSAVVISO.setChecked(true);
        notifiche_SMSAVVISO.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_SMSAVVISO.isChecked()) {
                    editor.putBoolean("imposta_notifiche_sms", true);
                    editor.apply();
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean b = prefs.getBoolean("imposta_notifiche_sms",false);
                    Log.i("notifiche abilitate3_1", b+"");
                } else if (!(notifiche_SMSAVVISO.isChecked())){
                    editor.putBoolean("imposta_notifiche_sms", false);
                    editor.apply();
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean b = prefs.getBoolean("imposta_notifiche_sms",false);
                    Log.i("notifiche abilitate3_2", b+"");
                }
                return true;
            }
        });

        final ListPreference tempo_smsavviso = (ListPreference) findPreference("minuti_smsmavviso");
        tempo_smsavviso.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                /*
                CharSequence currText = tempo_smsavviso.getEntry();
                String currValue = tempo_smsavviso.getValue();
                Log.i("1", currText+"");
                Log.i("2", currValue);
                tempo_smsavviso.setSummary(currText);
                /*
                editor.putBoolean("minuti_smsavviso", true);
                editor.apply();
                SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                boolean b = prefs.getBoolean("imposta_notifiche_farmaci",false);
                Log.i("notifiche abilitate4_1", b+"");
                */
                return true;
            }
        });

        //-----------------------------

        final SwitchPreferenceCompat notifiche_assunzione = (SwitchPreferenceCompat) findPreference("notifiche_assunzione_farmaci");
        notifiche_assunzione.setChecked(true);
        notifiche_assunzione.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick (Preference preference) {
                if(notifiche_assunzione.isChecked()) {
                    editor.putBoolean("imposta_notifiche_farmaci", true);
                    editor.apply();
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean b = prefs.getBoolean("imposta_notifiche_farmaci",false);
                    Log.i("notifiche abilitate4_1", b+"");
                } else if (!(notifiche_assunzione.isChecked())){
                    editor.putBoolean("imposta_notifiche_farmaci", false);
                    editor.apply();
                    SharedPreferences prefs = getContext().getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
                    boolean b = prefs.getBoolean("imposta_notifiche_farmaci",false);
                    Log.i("notifiche abilitate4_2", b+"");
                }
                return true;
            }
        });


    }

}
