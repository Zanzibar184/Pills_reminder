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

import com.example.zanzibar.myapplication.Database.cure.Cura;
import com.example.zanzibar.myapplication.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;



public class AlarmReceiver extends BroadcastReceiver {


    public String CHANNEL_ID = "Channel_ID";
    public String contentText = "prova";
    public String contentTitle = "Hai un farmaco da prendere";
    public String contentTicker = "New Message Alert!";
    private Cura cura;

    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {


        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(context, NotificaAssunzione.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Bundle b = intent.getExtras();
        String content_notification = b.getString("contenuto");
        int request_code = b.getInt("req_code");
        int numero_giorni = b.getInt("n_giorni");
        String key = b.getString("key");
        String cura_string = b.getString("cura");
        cura = Cura.toCura(cura_string);
        contentText = content_notification;

        resultIntent.putExtra("prova_passaggio_parametri", "passaggio parametri alla classe ProvaNotifica");
        resultIntent.putExtra("stringa_cura", cura_string);

        PendingIntent contentIntent = PendingIntent.getActivity(context,request_code,resultIntent,0,b);

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



        Random r = new Random();
        int random_value = r.nextInt();
        while(random_value<0) {
            random_value = r.nextInt();
        }



        if(assumi_farmaco_notifica) {
            nm.notify(random_value, notification);

            boolean enable_sms = prefs_notif.getBoolean("imposta_notifiche_sms", false);

            if (enable_sms && (cura.getImportante() == 1)) {


                int minuti_smsavviso = getMinutiSMS(prefs_notif.getString("minuti_smsavviso", ""));

                Random rand = new Random();
                int req_value = r.nextInt();
                while (req_value < 0) {
                    req_value = rand.nextInt();
                }


                Intent intent_sms = new Intent(context, AlarmReceiverSMS.class);
                intent_sms.putExtra("cura_string", cura_string);
                intent_sms.putExtra("tempo_ritardo", prefs_notif.getString("minuti_smsavviso", ""));

                PendingIntent alarmIntent;
                alarmIntent = PendingIntent.getBroadcast(context, req_value, intent_sms, 0);

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis() + (minuti_smsavviso * 60 * 1000));

                AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmIntent);
            }

        }

        if(counter==numero_giorni) {
            int alarmId = intent.getExtras().getInt("req_code");
            PendingIntent alarmIntent;
            alarmIntent = PendingIntent.getBroadcast(context, alarmId, new Intent(context, AlarmReceiver.class),
                    0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(alarmIntent);
            Log.e("Alarm","Cancellata Notifica");
        }


    }


    public static int getMinutiSMS(String s) {
        int res = 0;
        switch (s) {
            case "1 minuto":
                res = 1;
                break;
            case "5 minuti":
                res = 5;
                break;
            case "10 minuti":
                res = 10;
                break;
            case "20 minuti":
                res = 20;
                break;
            case "30 minuti":
                res = 30;
                break;
            case "1 ora":
                res = 60;
                break;
            case "2 ore":
                res = 120;
                break;
        }
        return res;
    }

}
