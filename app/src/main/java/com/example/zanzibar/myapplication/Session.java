package com.example.zanzibar.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Akshay Raj on 7/28/2016.
 * Snow Corporation Inc.
 * www.snowcorp.org
 */
public class Session {
    SharedPreferences pref;
    SharedPreferences prefsettings;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editorprefsettings;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "snow-intro-slider";
    private static final String PREF_SETTINGS_NAME = "ImpostazioniNotifiche";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public Session(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        prefsettings = context.getSharedPreferences(PREF_SETTINGS_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editorprefsettings = prefsettings.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        if (!isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
            editor.commit();
        } else {
            editorprefsettings.putBoolean("imposta_notifiche_app", true);
            editorprefsettings.putBoolean("imposta_notifiche_sms", true);
            editorprefsettings.putBoolean("imposta_notifiche_scorta_app", true);
            editorprefsettings.putBoolean("imposta_notifiche_farmaci", true);
            editorprefsettings.putString("minuti_smsavviso", "30 minuti");
            editorprefsettings.putString("pillole_scorta", "5 pillole");
            editorprefsettings.apply();
        }
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

}