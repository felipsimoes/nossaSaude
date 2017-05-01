package com.example.app.nossasaudeapp.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.activities.DadosMedicamentoActivity;
import com.example.app.nossasaudeapp.activities.MedicamentoActivity;
import com.example.app.nossasaudeapp.data.Medicamento;

import io.realm.RealmObject;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtil {


    public static void createNotification(Context context, RealmObject realmObject) {

        final NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        if (realmObject instanceof Medicamento) {
            Medicamento medicamento = ((Medicamento) realmObject);
            int medId = (int) medicamento.getId();
            Intent viewIntent = new Intent(context, DadosMedicamentoActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context,
                    medId, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification mNotify  = new Notification.Builder(context)
                    .setContentTitle(medicamento.getNome())
                    .setContentText("Medicamento")
                    .setSmallIcon(R.drawable.ic_generic_notification)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(medId, mNotify);
        }
    }
}
