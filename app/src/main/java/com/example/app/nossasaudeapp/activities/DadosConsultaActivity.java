package com.example.app.nossasaudeapp.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.nossasaudeapp.AlarmReceiver;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Consulta;
import com.example.app.nossasaudeapp.data.Reminder;
import com.example.app.nossasaudeapp.util.AlarmUtil;
import com.example.app.nossasaudeapp.util.DateAndTimeUtil;
import com.example.app.nossasaudeapp.util.RealmUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class DadosConsultaActivity extends AppCompatActivity {

    @BindView(R.id.txtconsulta)
    EditText txtconsulta;
    @BindView(R.id.txtdesconsulta)
    EditText txtdesconsulta;
    @BindView(R.id.btnSalvarConsulta)
    Button btnSalvarConsulta;
    @BindView(R.id.dadosconsulta)
    ConstraintLayout dadosconsulta;
    @BindView(R.id.consHora)
    TextView consHora;
    @BindView(R.id.consData)
    TextView consData;
    AlarmManager alarmManager;
    private Calendar calendar;

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_consulta);
        ButterKnife.bind(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        Intent intent = getIntent();
        long id = intent.getLongExtra("NOTIFICATION_ID", 0);

        if(id != 0 ) {
            fillConsultaDataOnFields(id);

        }
    }

    private void fillConsultaDataOnFields(long id) {

        Consulta consulta = realm.where(Consulta.class).equalTo("id", id).findFirst();
        txtconsulta.setText(consulta.getNome());
    }

    @OnClick(R.id.btnSalvarConsulta)
    public void onViewClicked() {
        if ("".equals(txtconsulta.getText().toString())) {
            Snackbar.make(dadosconsulta, "Preencha o nome da consulta.", Snackbar.LENGTH_SHORT).show();
        } else if ("".equals(txtdesconsulta.getText().toString())) {
            Snackbar.make(dadosconsulta, "Preencha uma descrição.", Snackbar.LENGTH_SHORT).show();
        } else {
            salvarConsulta();
        }
    }

    private void salvarConsulta() {
        Log.d("Time", DateAndTimeUtil.toStringReadableTime(calendar, this));

        final Consulta consulta = new Consulta();
        consulta.setId(RealmUtil.returnId(consulta));
        consulta.setNome(txtconsulta.getText().toString());
        consulta.setDescricao(txtdesconsulta.getText().toString());

        Reminder reminder = new Reminder();
        reminder.setId(RealmUtil.returnId(reminder));
        reminder.setOriginClass(Reminder.CONSULTA);
        consulta.setReminder(reminder);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(consulta);
            }
        });

        final Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        AlarmUtil.setAlarm(this, alarmIntent, (int) consulta.getReminder().getId(), calendar);


        Toast.makeText(this, "Consulta Salva", Toast.LENGTH_SHORT);
        startActivity(new Intent(this, ConsultaActivity.class));
    }

    @OnClick(R.id.consHora)
    public void timePicker() {
        TimePickerDialog TimePicker = new TimePickerDialog(DadosConsultaActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                consHora.setText(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
        TimePicker.show();
    }

    @OnClick(R.id.consData)
    public void datePicker(View view) {
        DatePickerDialog DatePicker = new DatePickerDialog(DadosConsultaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                consData.setText(DateAndTimeUtil.toStringReadableDate(calendar));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }
}
