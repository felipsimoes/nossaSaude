package com.example.app.nossasaudeapp.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.app.nossasaudeapp.AlarmReceiver;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.util.DateAndTimeUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedicamentoActivity extends AppCompatActivity {

    @BindView(R.id.medNome)
    EditText medNome;
    @BindView(R.id.button)
    Button btnAdd;
    @BindView(R.id.medHora)
    TextView medHora;
    AlarmManager alarmManager;
    @BindView(R.id.medData)
    TextView medData;
    private PendingIntent pendingIntent;
    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicamento);
        ButterKnife.bind(this);

        final Context context = this;

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        calendar = Calendar.getInstance();

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

    }

    private void validateInput() {
        if (medNome.getText().toString().trim().isEmpty()) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "Por favor, insira um nome", Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            saveMedicamento();
        }
    }

    private void saveMedicamento() {
        Log.d("Time", DateAndTimeUtil.toStringReadableTime(calendar, this));

        final Intent myIntent = new Intent(this, AlarmReceiver.class);
        myIntent.putExtra("extra", "yes");
        pendingIntent = PendingIntent.getBroadcast(MedicamentoActivity.this, 0,
                myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick(R.id.medHora)
    public void timePicker() {
        TimePickerDialog TimePicker = new TimePickerDialog(MedicamentoActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                medHora.setText(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
        TimePicker.show();
    }

    @OnClick(R.id.button)
    public void btnAddClick() {
        validateInput();
    }

    @OnClick(R.id.medData)
    public void datePicker() {
        DatePickerDialog DatePicker = new DatePickerDialog(getBaseContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                medData.setText(DateAndTimeUtil.toStringReadableDate(calendar));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }
}
