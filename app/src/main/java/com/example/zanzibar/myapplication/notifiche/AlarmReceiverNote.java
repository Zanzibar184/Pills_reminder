package com.example.zanzibar.myapplication.notifiche;

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
import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.MODE_PRIVATE;


public class AlarmReceiverNote extends BroadcastReceiver {

    static int COUNTER = 0;

    public String CHANNEL_ID = "Channel_ID";
    public String contentText = "prova";
    public String contentTitle = "Hai una nota!";
    public String contentTicker = "New Message Alert!";

    //NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(context, NotificaNota.class);

        Bundle b = intent.getExtras();
        String content_notification = b.getString("contenuto");
        int request_code = b.getInt("req_code");
        String key = b.getString("key");
        contentText = content_notification;

        String titolo = b.getString("titolo_nota");
        String contenuto = b.getString("contenuto_nota");
        String data = b.getString("data_nota");
        String ora = b.getString("orario_nota");
        int tipo = b.getInt("tipo_nota");

        resultIntent.putExtra("titolo", titolo);
        resultIntent.putExtra("contenuto", contenuto);
        resultIntent.putExtra("data", data);
        resultIntent.putExtra("ora", ora);
        resultIntent.putExtra("tipo", tipo);

        PendingIntent contentIntent = PendingIntent.getActivity(context,request_code,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT,b);

        SharedPreferences prefs_notif = context.getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
        boolean notifiche_note = prefs_notif.getBoolean("imposta_notifiche_note",false);

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

        //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Random r = new Random();
        int random_value = r.nextInt();
        while(random_value<0) {
            random_value = r.nextInt();
        }
        if(notifiche_note) {
            nm.notify(random_value, notification);
        }

    }
}
