package com.example.app.nossasaudeapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicamentoActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    @BindView(R.id.medNome) EditText medNome;
    @BindView(R.id.timepicker) TimePicker timepicker;
    @BindView(R.id.button) Button btnAdd;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicamento);
        ButterKnife.bind(this);


        final Intent myIntent = new Intent(this, AlarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance();

//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.tipo_remedio_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
//        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
//                R.array.frequencia_array, android.R.layout.simple_spinner_item);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner1.setAdapter(adapter1);

        Button btn = (Button) findViewById(R.id.button);

        final TimePicker timePicker = (TimePicker) findViewById(R.id.timepicker);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                final int hour = timePicker.getCurrentHour();
                final int minute = timePicker.getCurrentMinute();

                String minute_string = String.valueOf(minute);
                String hour_string = String.valueOf(hour);

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                Log.d("HORA", hour_string);
                Log.d("MINUTOS", minute_string);

                myIntent.putExtra("extra", "yes");
                pendingIntent = PendingIntent.getBroadcast(MedicamentoActivity.this, 0,
                        myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

    }
}
