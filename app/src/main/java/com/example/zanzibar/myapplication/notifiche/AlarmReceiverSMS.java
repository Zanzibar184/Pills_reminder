package com.example.zanzibar.myapplication.notifiche;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;

import com.example.zanzibar.myapplication.R;

import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class AlarmReceiverSMS extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("3485794576", null, "sta morendo corri", null, null);

    }
}
