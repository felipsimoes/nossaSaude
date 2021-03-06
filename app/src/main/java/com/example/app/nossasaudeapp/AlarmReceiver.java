package com.example.app.nossasaudeapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.app.nossasaudeapp.data.Consulta;
import com.example.app.nossasaudeapp.data.Exame;
import com.example.app.nossasaudeapp.data.Medicamento;
import com.example.app.nossasaudeapp.data.Reminder;
import com.example.app.nossasaudeapp.util.AlarmUtil;
import com.example.app.nossasaudeapp.util.NotificationUtil;

import io.realm.Realm;
import io.realm.RealmObject;

import static io.realm.Realm.getDefaultInstance;

public class AlarmReceiver extends BroadcastReceiver {

    Realm realm = getDefaultInstance();
    @Override
    public void onReceive(Context context, Intent intent) {
        int idReminder = intent.getIntExtra("id",0);

        final Reminder reminder = realm.where(Reminder.class)
                .equalTo("id", idReminder).findFirst();

        RealmObject realmObject = null;

        switch ((int)reminder.getOriginClass()){
            case (int)Reminder.MEDICAMENTO:
                updateReminderNumberShown(reminder);
                realmObject = realm.where(Medicamento.class).
                        equalTo("reminder.id", reminder.getId()).findFirst();
                break;
            case (int)Reminder.CONSULTA:
                realmObject = realm.where(Consulta.class).
                        equalTo("reminder.id", reminder.getId()).findFirst();
                break;
            case (int)Reminder.EXAME:
                realmObject = realm.where(Exame.class).
                        equalTo("reminder.id", reminder.getId()).findFirst();
                break;
        }

        NotificationUtil.createNotification(context, realmObject);

        if (reminder.getNumberToShow() > reminder.getNumberShown() || Boolean.parseBoolean(reminder.getForeverState())) {
            AlarmUtil.setNextAlarm(context, reminder);
        }
        Intent updateIntent = new Intent("BROADCAST_REFRESH");
        LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);

    }

    private void updateReminderNumberShown(final Reminder reminder) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                reminder.setNumberShown(reminder.getNumberShown() + 1);
                realm.copyToRealmOrUpdate(reminder);
            }
        });
    }


}
