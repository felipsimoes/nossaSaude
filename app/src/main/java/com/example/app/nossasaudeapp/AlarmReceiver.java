package com.example.app.nossasaudeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

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
