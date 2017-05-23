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
import com.example.app.nossasaudeapp.data.Exame;
import com.example.app.nossasaudeapp.data.Reminder;
import com.example.app.nossasaudeapp.util.AlarmUtil;
import com.example.app.nossasaudeapp.util.DateAndTimeUtil;
import com.example.app.nossasaudeapp.util.RealmUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class DadosExameActivity extends AppCompatActivity {

    @BindView(R.id.txtexame)
    EditText txtexame;
    @BindView(R.id.txtdesexame)
    EditText txtdesexame;
    @BindView(R.id.btnSalvarExame)
    Button btnSalvarExame;
    @BindView(R.id.dadosexame)
    ConstraintLayout dadosexame;
    @BindView(R.id.exameHora)
    TextView exameHora;
    @BindView(R.id.exameData)
    TextView exameData;
    AlarmManager alarmManager;
    private Calendar calendar;

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_exame);
        ButterKnife.bind(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        Intent intent = getIntent();
        long id = intent.getLongExtra("NOTIFICATION_ID", 0);

        if(id != 0 ) {
            fillExameDataOnFields(id);

        }
    }

    private void fillExameDataOnFields(long id) {
        Exame exame = realm.where(Exame.class).equalTo("id", id).findFirst();
        txtexame.setText(exame.getNome());
    }

    @OnClick(R.id.btnSalvarExame)
    public void onViewClicked() {
        if ("".equals(txtexame.getText().toString())) {
            Snackbar.make(dadosexame, "Preencha o nome do exame.", Snackbar.LENGTH_SHORT).show();
        } else if ("".equals(txtdesexame.getText().toString())) {
            Snackbar.make(dadosexame, "Preencha uma descrição.", Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Exame Salvo", Toast.LENGTH_SHORT);
            salvarExame();
        }
    }

    private void salvarExame() {
        Log.d("Time", DateAndTimeUtil.toStringReadableTime(calendar, this));

        final Exame exame = new Exame();
        exame.setNome(txtexame.getText().toString());
        exame.setDescricao(txtdesexame.getText().toString());
        exame.setId(RealmUtil.returnId(exame));

        Reminder reminder = new Reminder();
        reminder.setId(RealmUtil.returnId(reminder));
        reminder.setOriginClass(Reminder.EXAME);
        exame.setReminder(reminder);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(exame);
            }
        });

        final Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        AlarmUtil.setAlarm(this, alarmIntent, (int) exame.getReminder().getId(), calendar);

        Toast.makeText(this, "Exame Salvo", Toast.LENGTH_SHORT);
        //Snackbar.make(dadosexame,"Exame Salvo.",Snackbar.LENGTH_SHORT).show();
        startActivity(new Intent(this, ExameActivity.class));
    }

    @OnClick(R.id.exameHora)
    public void timePicker() {
        TimePickerDialog TimePicker = new TimePickerDialog(DadosExameActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                exameHora.setText(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
        TimePicker.show();
    }

    @OnClick(R.id.exameData)
    public void datePicker(View view) {
        DatePickerDialog DatePicker = new DatePickerDialog(DadosExameActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                exameData.setText(DateAndTimeUtil.toStringReadableDate(calendar));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }
}
