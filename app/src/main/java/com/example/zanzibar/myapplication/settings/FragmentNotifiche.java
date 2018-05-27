package com.example.zanzibar.myapplication.settings;

import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.util.Log;

import com.example.zanzibar.myapplication.R;

public class FragmentNotifiche extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from an XML resource
        setPreferencesFromResource(R.xml.pref_notifiche, rootKey);

        //-----------------------------

        SwitchPreference notifiche_app = (SwitchPreference) findPreference("notifiche_abilitate");
        notifiche_app.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        CheckBoxPreference set_vibrazione = (CheckBoxPreference) findPreference("vibrazione_abilitata");
        set_vibrazione.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        //-----------------------------

        SwitchPreference notifiche_scorta = (SwitchPreference) findPreference("notifiche_scorta_abilitate");
        notifiche_scorta.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        ListPreference pillole_scorta = (ListPreference) findPreference("list_numero_pillole_avviso_scorta");
        pillole_scorta.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        ListPreference ripetizione_scorta = (ListPreference) findPreference("tempo_scorta");
        ripetizione_scorta.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        //-----------------------------

        SwitchPreference notifiche_SMSAVVISO = (SwitchPreference) findPreference("notifiche_SMSAVVISO_abilitate");
        notifiche_SMSAVVISO.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        ListPreference tempo_smsavviso = (ListPreference) findPreference("minuti_smsmavviso");
        tempo_smsavviso.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        //-----------------------------

        SwitchPreference notifiche_assunzione = (SwitchPreference) findPreference("notifiche_assunzione_farmaci");
        notifiche_assunzione.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        ListPreference numero_promemoria_farmaco = (ListPreference) findPreference("numero_promemoria_farmaco");
        numero_promemoria_farmaco.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        ListPreference frequenza_ripetizione = (ListPreference) findPreference("tempo_ripetizione_promemoria");
        frequenza_ripetizione.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });


    }

}
