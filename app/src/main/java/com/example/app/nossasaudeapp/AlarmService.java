package com.example.app.nossasaudeapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.app.nossasaudeapp.activities.MedicamentoActivity;

/**
 * Created by Felipe on 04/04/2017.
 */

public class AlarmService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MyActivity", "In the service");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("HERE", "ON START");
        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), MedicamentoActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("TITLE")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.notification_template_icon_bg)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        mNM.notify(0, mNotify);
        Log.d("HERE", "NOTIFICACAO SETADA");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
