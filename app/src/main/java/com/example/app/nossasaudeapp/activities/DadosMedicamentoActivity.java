package com.example.app.nossasaudeapp.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.app.nossasaudeapp.AlarmReceiver;
import com.example.app.nossasaudeapp.R;
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

public class DadosMedicamentoActivity extends AppCompatActivity {

    @BindView(R.id.medNome)
    EditText medNome;
    @BindView(R.id.button)
    Button btnAdd;
    @BindView(R.id.medHora)
    TextView medHora;
    AlarmManager alarmManager;
    @BindView(R.id.medData)
    TextView medData;
    @BindView(R.id.spinnerUnidadeMedicamento)
    Spinner spinnerUnidadeMedicamento;
    @BindView(R.id.qtDoseMedicamento)
    EditText qtDoseMedicamento;
    @BindView(R.id.vibrarSwitch)
    Switch vibrarSwitch;
    @BindView(R.id.myCoordinatorLayout)
    CoordinatorLayout myCoordinatorLayout;
    private Calendar calendar;
    Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_medicamento);
        ButterKnife.bind(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        Intent intent = getIntent();
        long id = intent.getLongExtra("NOTIFICATION_ID", 0);

        if(id != 0 ) {
            fillMedicamentoDataOnFields(id);

        } else {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.unidade_remedio_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUnidadeMedicamento.setAdapter(adapter);
        }
    }

    private void fillMedicamentoDataOnFields(long id) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Medicamento medicamento = realm.where(Medicamento.class).equalTo("id", id).findFirst();
        medNome.setText(medicamento.getNome());
    }

    private void validateInput() {
        if (medNome.getText().toString().trim().isEmpty()) {
            Snackbar.make(myCoordinatorLayout,
                    "Por favor, insira um nome", Snackbar.LENGTH_SHORT)
                    .show();
        }
//        if (spinnerUnidadeMedicamento.getSelectedItemId() == -1) {
//            Snackbar.make(myCoordinatorLayout,
//                    "Por favor, selecione a unidade de medicamento", Snackbar.LENGTH_SHORT)
//                    .show();
//        }
        else {
            saveMedicamento();
        }
    }

    private void saveMedicamento() {
        Log.d("Time", DateAndTimeUtil.toStringReadableTime(calendar, this));

        final Medicamento medicamento = new Medicamento();
        medicamento.setId(RealmUtil.returnId(medicamento));
        medicamento.setNome(medNome.getText().toString());

        Reminder reminder = new Reminder();
        reminder.setId(RealmUtil.returnId(reminder));
        reminder.setOriginClass(Reminder.MEDICAMENTO);
        medicamento.setReminder(reminder);

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(medicamento);
            }
        });

        final Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        AlarmUtil.setAlarm(this, alarmIntent, (int) medicamento.getReminder().getId(), calendar);

        Toast.makeText(this, "Medicamento Salvo", Toast.LENGTH_SHORT);
        startActivity(new Intent(this, MedicamentoActivity.class));
    }

    @OnClick(R.id.medHora)
    public void timePicker() {
        TimePickerDialog TimePicker = new TimePickerDialog(DadosMedicamentoActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
    public void datePicker(View view) {
        DatePickerDialog DatePicker = new DatePickerDialog(DadosMedicamentoActivity.this, new DatePickerDialog.OnDateSetListener() {
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
