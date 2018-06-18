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

import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class AlarmReceiverSMS extends BroadcastReceiver {


    static int COUNTER = 0;

    public String CHANNEL_ID = "Channel_ID";
    public String contentText = "prova";
    public String contentTitle = "Hai un farmaco da prendere";
    public String contentTicker = "New Message Alert!";

    NotificationManager nm;


    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(context, NotificaScorta.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 200, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

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

        Log.i("AlarmSMS", "sms ricevuto!!");
        Intent intent_sms = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "748392"));
        intent.putExtra("sms_body", "corpo del messaggio");
        context.startActivity(intent_sms);

        //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        // nm.notify(random_value, notification);


        /*

        SharedPreferences prefs_notif = context.getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
        boolean sms_notifiche = prefs_notif.getBoolean("imposta_notifiche_sms",false);

        Log.i("entrata in AlarmSMS", "sono entrato!");

        if(sms_notifiche) {
            Log.i("sms", "sto per inviare un sms");
            /*
            Intent intent_sms = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "748392"));
            intent.putExtra("sms_body", "corpo del messaggio");
            context.startActivity(intent_sms);
            */
        //}

        /*
        COUNTER++;

        //Log.i("counter notifica", COUNTER+"");


        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(context, NotificaAssunzione.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle b = intent.getExtras();
        String content_notification = b.getString("contenuto");
        int request_code = b.getInt("req_code");
        int numero_giorni = b.getInt("n_giorni");
        String key = b.getString("key");
        String cura = b.getString("cura");
        contentText = content_notification;

        resultIntent.putExtra("prova_passaggio_parametri", "passaggio parametri alla classe ProvaNotifica");
        resultIntent.putExtra("stringa_cura", cura);

        PendingIntent contentIntent = PendingIntent.getActivity(context,request_code,resultIntent,0,b);

        //intent.putExtra("contatore_giorni", ++contatore_giorni);
        //Log.i("contatore giorni", contatore_giorni+"");

        SharedPreferences prefs = context.getSharedPreferences("ContatoreGiorniPreferenze", MODE_PRIVATE);
        int counter = prefs.getInt(key,0);

        SharedPreferences prefs_notif = context.getSharedPreferences("ImpostazioniNotifiche", MODE_PRIVATE);
        boolean assumi_farmaco_notifica = prefs_notif.getBoolean("imposta_notifiche_farmaci",false);

        counter = counter+1;

        SharedPreferences.Editor editor = context.getSharedPreferences("ContatoreGiorniPreferenze",MODE_PRIVATE).edit();
        editor.putInt(key, counter);
        editor.apply();

        Log.i("counter giorni", counter+"");
        Log.i("numero giorni", numero_giorni+"");

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

        Log.i("dati in alarmrevceir()",  "reqcode" + request_code);


        //int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        Random r = new Random();
        int random_value = r.nextInt();
        while(random_value<0) {
            random_value = r.nextInt();
        }
        if(assumi_farmaco_notifica) {
            nm.notify(random_value, notification);
        }

        if(counter==numero_giorni) {
            int alarmId = intent.getExtras().getInt("req_code");
            PendingIntent alarmIntent;
            alarmIntent = PendingIntent.getBroadcast(context, alarmId, new Intent(context, AlarmReceiverSMS.class),
                    0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(alarmIntent);
            Log.e("Alarm","Cancellata Notifica");
        }
        */


    }
}
