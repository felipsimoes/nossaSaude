package com.example.app.nossasaudeapp.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.app.nossasaudeapp.AlarmReceiver;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.activities.DadosMedicamentoActivity;
import com.example.app.nossasaudeapp.data.Reminder;

import java.util.Calendar;

import io.realm.Realm;

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

    public static void setNextAlarm(Context context, Reminder reminder) {
        Calendar calendar = DateAndTimeUtil.parseDateAndTime(reminder.getDateAndTime());
        calendar.set(Calendar.SECOND, 0);

        switch ((int) reminder.getRepeatType()) {
            case Reminder.HOURLY:
                calendar.add(Calendar.HOUR, (int) reminder.getInterval());
                break;
            case Reminder.DAILY:
                calendar.add(Calendar.DATE, (int) reminder.getInterval());
                break;
            case Reminder.WEEKLY:
                calendar.add(Calendar.WEEK_OF_YEAR, (int) reminder.getInterval());
                break;
            case Reminder.MONTHLY:
                calendar.add(Calendar.MONTH, (int) reminder.getInterval());
                break;
            case Reminder.YEARLY:
                calendar.add(Calendar.YEAR, (int) reminder.getInterval());
                break;
            case Reminder.SPECIFIC_DAYS:
                Calendar weekCalendar = (Calendar) calendar.clone();
                weekCalendar.add(Calendar.DATE, 1);
                for (int i = 0; i < 7; i++) {
                    int position = (i + (weekCalendar.get(Calendar.DAY_OF_WEEK) - 1)) % 7;
                    //parse de byte para boolean
                    if (reminder.getDaysOfWeek()[position] != 0) {
                        calendar.add(Calendar.DATE, i + 1);
                        break;
                    }
                }
                break;
        }

        Realm realm = Realm.getDefaultInstance();
        final long id = reminder.getId();
        final Calendar c = calendar;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Reminder r = realm.where(Reminder.class).equalTo("id", id).findFirst();
                r.setDateAndTime(DateAndTimeUtil.toStringDateAndTime(c));
                realm.copyToRealmOrUpdate(r);
            }
        });

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        setAlarm(context, alarmIntent, (int) reminder.getId(), calendar);
    }
}
