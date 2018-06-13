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

import com.example.zanzibar.myapplication.R;

import java.util.Date;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class AlarmReceiverScorte extends BroadcastReceiver {

    static int COUNTER = 0;

    public String CHANNEL_ID = "Channel_ID";
    public String contentText = "prova";
    public String contentTitle = "Avviso scorte farmaco";
    public String contentTicker = "New Message Alert!";

    //NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(context, NotificaAssunzione.class);

        Bundle b = intent.getExtras();
        String content_notification = b.getString("contenuto");
        int request_code = b.getInt("req_code");
        String key = b.getString("key");
        contentText = content_notification;
        PendingIntent contentIntent = PendingIntent.getActivity(context,request_code,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT,b);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificaAssunzione.class);
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

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        nm.notify(m, notification);

    }
}
