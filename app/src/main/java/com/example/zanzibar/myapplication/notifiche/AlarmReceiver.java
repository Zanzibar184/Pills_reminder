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
import android.util.Log;
import android.widget.Toast;

import com.example.zanzibar.myapplication.ProvaNotifica;
import com.example.zanzibar.myapplication.R;

import java.util.Date;
import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;



public class AlarmReceiver extends BroadcastReceiver {

    static int COUNTER = 0;

    public String CHANNEL_ID = "Channel_ID";
    public String contentText = "Hai un farmaco da prendere";
    public String contentTitle = "Scorri per confermare l'assunzione del farmaco!";
    public String contentTicker = "New Message Alert!";

    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {

        COUNTER++;

        Log.i("counter notifica", COUNTER+"");


        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(context, ProvaNotifica.class);
        Bundle b = intent.getExtras();
        String title_notification = b.getString("titolo");
        String content_notification = b.getString("contenuto");
        int request_code = b.getInt("req_code");
        contentTitle = title_notification;
        contentText = content_notification;
        PendingIntent contentIntent = PendingIntent.getActivity(context,request_code,resultIntent,0,b);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ProvaNotifica.class);
        stackBuilder.addNextIntent(resultIntent);

        Notification.Builder builder = new Notification.Builder(context);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        Notification notification = builder.setContentTitle(contentTitle)
                .setSmallIcon(R.drawable.ic_add_alert_black_24dp)
                .setContentText(contentText)
                .setTicker(contentTicker)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setContentIntent(contentIntent).build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );
            nm.createNotificationChannel(channel);
        }

        Log.i("dati in alarmrevceir()",  "reqcode" + request_code);


        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        nm.notify(m, notification);

        if(COUNTER >= 1) {
            int alarmId = intent.getExtras().getInt("req_code");
            PendingIntent alarmIntent;
            alarmIntent = PendingIntent.getBroadcast(context, alarmId, new Intent(context, AlarmReceiver.class),
                    0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(alarmIntent);
            Log.e("Alarm","Cancellata Notifica");
        }


    }
}
