package com.example.zanzibar.myapplication.notifiche;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.example.zanzibar.myapplication.Database.contatti.Contatti;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDAO;
import com.example.zanzibar.myapplication.Database.contatti.ContattiDao_DB;
import com.example.zanzibar.myapplication.Database.cure.Cura;

import java.util.List;


public class AlarmReceiverSMS extends BroadcastReceiver {
    ContattiDAO dao;
    List<Contatti> list_contatti;

    @Override
    public void onReceive(Context context, Intent intent) {
        dao = new ContattiDao_DB();
        dao.open();
        list_contatti = dao.getContattiImportanti();
        dao.close();

        String cura_raw = intent.getStringExtra("cura_string");
        Cura cura = Cura.toCura(cura_raw);
        String tempo_ritardo = intent.getStringExtra("tempo_ritardo");


        if(cura.getImportante() == 1) {

            SmsManager smsManager = SmsManager.getDefault();

            for (int i = 0; i < list_contatti.size(); i++) {
                smsManager.sendTextMessage(list_contatti.get(i).getNumero(), null,
                        "Pills Reminder Alert: il paziente non ha assunto " +
                                cura.getQuantità_assunzione() + " " + cura.getUnità_misura() + " di " + cura.getNome() + " entro " + tempo_ritardo
                        , null, null);
            }
        }

    }
}
