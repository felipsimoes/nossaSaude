package com.example.app.nossasaudeapp.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.app.nossasaudeapp.AlarmReceiver;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Exame;
import com.example.app.nossasaudeapp.data.Medicamento;
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
    EditText tituloExame;
    @BindView(R.id.txtdesexame)
    EditText descricaoExame;
    @BindView(R.id.btnSalvarExame)
    Button btnSalvarExame;
    @BindView(R.id.dadosexame)
    ConstraintLayout dadosExameLayout;
    @BindView(R.id.exameHora)
    TextView horaExame;
    @BindView(R.id.exameData)
    TextView dataExame;
    AlarmManager alarmManager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Calendar calendar;
    private Realm realm = Realm.getDefaultInstance();
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_exame);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.exam);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        Intent intent = getIntent();
        id = intent.getLongExtra("NOTIFICATION_ID", 0);

        if (id != 0) {
            fillExameDataOnFields(id);
        }
    }

    private void fillExameDataOnFields(long id) {
        Exame exame = realm.where(Exame.class).equalTo("id", id).findFirst();
        tituloExame.setText(exame.getNome());
        descricaoExame.setText(exame.getDescricao());

        calendar = DateAndTimeUtil.parseDateAndTime(exame.getReminder().getDateAndTime());

        dataExame.setText(DateAndTimeUtil.toStringReadableDate(calendar));
        horaExame.setText(DateAndTimeUtil.toStringReadableTime(calendar, this));
    }

    @OnClick(R.id.btnSalvarExame)
    public void onViewClicked() {
        if (tituloExame.getText().toString().isEmpty()) {
            Snackbar.make(dadosExameLayout, "Preencha o nome do exame.", Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Exame Salvo", Toast.LENGTH_SHORT).show();
            salvarExame();
        }
    }

    private void salvarExame() {
        Log.d("Time", DateAndTimeUtil.toStringReadableTime(calendar, this));

        final Exame exame = new Exame();
        Reminder reminder = new Reminder();

        if (id == 0) {
            id = RealmUtil.returnId(exame);
            reminder.setId(RealmUtil.returnId(reminder));
        }else {
            Exame exameReminder = realm.where(Exame.class).equalTo("id", id).findFirst();
            reminder.setId(exameReminder.getReminder().getId());
        }

        exame.setId(id);
        exame.setNome(tituloExame.getText().toString());
        exame.setDescricao(descricaoExame.getText().toString());

        reminder.setOriginClass(Reminder.EXAME);
        reminder.setDateAndTime(DateAndTimeUtil.toStringDateAndTime(calendar));

        exame.setReminder(reminder);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(exame);
            }
        });

        final Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        AlarmUtil.setAlarm(this, alarmIntent, (int) exame.getReminder().getId(), calendar);

        Toast.makeText(this, "Exame Salvo", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ExameActivity.class));
    }

    @OnClick(R.id.exameHora)
    public void timePicker() {
        TimePickerDialog TimePicker = new TimePickerDialog(DadosExameActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                horaExame.setText(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
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
                dataExame.setText(DateAndTimeUtil.toStringReadableDate(calendar));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
