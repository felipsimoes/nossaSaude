package com.example.app.nossasaudeapp.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.activities.DadosConsultaActivity;
import com.example.app.nossasaudeapp.activities.DadosExameActivity;
import com.example.app.nossasaudeapp.activities.DadosMedicamentoActivity;
import com.example.app.nossasaudeapp.activities.MedicamentoActivity;
import com.example.app.nossasaudeapp.data.Consulta;
import com.example.app.nossasaudeapp.data.Exame;
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
            viewIntent.putExtra("NOTIFICATION_ID", (long)medId);
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
            Log.d("Notification", Medicamento.class.toString());
        }
        else if (realmObject instanceof Consulta) {
            Consulta consulta = ((Consulta) realmObject);
            int consId = (int) consulta.getId();
            Intent viewIntent = new Intent(context, DadosConsultaActivity.class);
            viewIntent.putExtra("NOTIFICATION_ID", (long) consId);
            PendingIntent pIntent = PendingIntent.getActivity(context,
                    consId, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification mNotify = new Notification.Builder(context)
                    .setContentTitle(consulta.getNome())
                    .setContentText("Consulta")
                    .setSmallIcon(R.drawable.ic_generic_notification)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(consId, mNotify);
            Log.d("Notification", Consulta.class.toString());
        }
        else if (realmObject instanceof Exame) {
            Exame exame = ((Exame) realmObject);
            int exameId = (int) exame.getId();
            Intent viewIntent = new Intent(context, DadosExameActivity.class);
            viewIntent.putExtra("NOTIFICATION_ID", (long) exameId);
            PendingIntent pIntent = PendingIntent.getActivity(context,
                    exameId, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification mNotify = new Notification.Builder(context)
                    .setContentTitle(exame.getNome())
                    .setContentTitle("Exame")
                    .setSmallIcon(R.drawable.ic_generic_notification)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(exameId, mNotify);
            Log.d("Notification", Exame.class.toString());
        }
    }

    public static void cancelNotification(Context context, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationId);
    }
}
