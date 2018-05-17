package com.example.zanzibar.myapplication.Database_cure;

import android.app.Application;
import android.content.Context;

public class Pills_reminder extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        Pills_reminder.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return Pills_reminder.context;
    }
}
