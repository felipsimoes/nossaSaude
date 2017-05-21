package com.example.app.nossasaudeapp.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.activities.DadosMedicamentoActivity;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmUtil {

    public static void setAlarm(Context context, Intent intent, int id, Calendar calendar) {

        intent.putExtra("id", id);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long repeatingMiliseconds = 0;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                repeatingMiliseconds, pendingIntent);

    }

    public static void cancelAlarm(Context context, Intent intent, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
