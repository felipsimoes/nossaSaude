package com.example.app.nossasaudeapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.app.nossasaudeapp.data.Medicamento;
import com.example.app.nossasaudeapp.util.NotificationUtil;

import io.realm.Realm;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int idMedicamento = intent.getIntExtra("id",0);
        Medicamento medicamento = Realm.getDefaultInstance().
                where(Medicamento.class).
                equalTo("id", idMedicamento).findFirst();

        NotificationUtil.createNotification(context, medicamento);
        Intent updateIntent = new Intent("BROADCAST_REFRESH");
        LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);

    }


}
