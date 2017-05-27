package com.example.app.nossasaudeapp.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.app.nossasaudeapp.AlarmReceiver;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Medicamento;
import com.example.app.nossasaudeapp.data.Reminder;
import com.example.app.nossasaudeapp.dialogs.AdvancedRepeatSelector;
import com.example.app.nossasaudeapp.dialogs.DaysOfWeekSelector;
import com.example.app.nossasaudeapp.dialogs.RepeatSelector;
import com.example.app.nossasaudeapp.util.AlarmUtil;
import com.example.app.nossasaudeapp.util.DateAndTimeUtil;
import com.example.app.nossasaudeapp.util.RealmUtil;
import com.example.app.nossasaudeapp.util.TextFormatUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class DadosMedicamentoActivity extends AppCompatActivity
        implements AdvancedRepeatSelector.AdvancedRepeatSelectionListener,
        DaysOfWeekSelector.DaysOfWeekSelectionListener, RepeatSelector.RepeatSelectionListener {

    @BindView(R.id.medNome) EditText medNome;
    @BindView(R.id.button) Button btnAdd;
    @BindView(R.id.medHora) TextView medHora;
    @BindView(R.id.medData) TextView medData;
    @BindView(R.id.spinnerUnidadeMedicamento) Spinner spinnerUnidadeMedicamento;
    @BindView(R.id.qtDoseMedicamento) EditText qtDoseMedicamento;
    @BindView(R.id.myCoordinatorLayout) CoordinatorLayout myCoordinatorLayout;
    @BindView(R.id.repeat_day) TextView repeatText;
    @BindView(R.id.forever_row) LinearLayout foreverRow;
    @BindView(R.id.switch_toggle) SwitchCompat foreverSwitch;
    @BindView(R.id.show) TextView showText;
    @BindView(R.id.show_times_number) EditText showTimesNumber;
    @BindView(R.id.times) TextView timesText;
    @BindView(R.id.bottom_row) LinearLayout bottomRow;

    AlarmManager alarmManager;
    Realm realm = Realm.getDefaultInstance();
    private Calendar calendar;
    private boolean[] daysOfWeek = new boolean[7];
    private int timesShown = 0;
    private int timesToShow = 1;
    private int repeatType;
    private int interval = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_medicamento);
        ButterKnife.bind(this);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        repeatType = Reminder.DOES_NOT_REPEAT;

        Intent intent = getIntent();
        long id = intent.getLongExtra("NOTIFICATION_ID", 0);

        if (id != 0) {
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
        showTimesNumber.setText(String.valueOf(medicamento.getReminder().getNumberToShow()));


        timesShown = (int) medicamento.getReminder().getNumberShown();
        repeatType = (int) medicamento.getReminder().getRepeatType();
        interval = (int) medicamento.getReminder().getInterval();

        showText.setText(getString(R.string.times_shown_edit,
                medicamento.getReminder().getNumberShown()));
        timesText.setVisibility(View.VISIBLE);

        if (medicamento.getReminder().getRepeatType() != Reminder.DOES_NOT_REPEAT) {
            if ((int) medicamento.getReminder().getInterval() > 1) {
                repeatText.setText(TextFormatUtil.formatAdvancedRepeatText(this, repeatType, interval));
            } else {
                repeatText.setText(getResources().getStringArray(R.array.repeat_array)[(int) medicamento.getReminder().getRepeatType()]);
            }
            showFrequency(true);
        }

        if (medicamento.getReminder().getRepeatType() == Reminder.SPECIFIC_DAYS) {
            daysOfWeek = RealmUtil.convertToBoolArray(medicamento.getReminder().getDaysOfWeek());
            repeatText.setText(TextFormatUtil.formatDaysOfWeekText(this, daysOfWeek));
        }

        if (Boolean.parseBoolean(medicamento.getReminder().getForeverState())) {
            foreverSwitch.setChecked(true);
            bottomRow.setVisibility(View.GONE);
        }
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
        if (showTimesNumber.getText().toString().isEmpty()) {
            showTimesNumber.setText("1");
        }
        timesToShow = Integer.parseInt(showTimesNumber.getText().toString());
        if (repeatType == Reminder.DOES_NOT_REPEAT) {
            timesToShow = timesShown + 1;
        }
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
        reminder.setRepeatType(repeatType);
        reminder.setForeverState(Boolean.toString(foreverSwitch.isChecked()));
        reminder.setNumberToShow(timesToShow);
        reminder.setNumberShown(timesShown);
        reminder.setInterval(interval);
        ;

        reminder.setDateAndTime(DateAndTimeUtil.toStringDateAndTime(calendar));

        if (repeatType == Reminder.SPECIFIC_DAYS) {
            reminder.setDaysOfWeek(RealmUtil.convertToByteArray(daysOfWeek));
        }

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

    public void showFrequency(boolean show) {
        if (show) {
            foreverRow.setVisibility(View.VISIBLE);
            bottomRow.setVisibility(View.VISIBLE);


        } else {
            foreverSwitch.setChecked(false);
            foreverRow.setVisibility(View.GONE);
            bottomRow.setVisibility(View.GONE);
        }
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

    @OnClick(R.id.repeat_row)
    public void repeatSelector() {
        DialogFragment dialog = new RepeatSelector();
        dialog.show(getSupportFragmentManager(), "RepeatSelector");
    }

    @Override
    public void onRepeatSelection(DialogFragment dialog, int which, String repeatText) {
        interval = 1;
        repeatType = which;
        this.repeatText.setText(repeatText);
        if (which == Reminder.DOES_NOT_REPEAT) {
            showFrequency(false);
        } else {
            showFrequency(true);
        }
    }

    @Override
    public void onDaysOfWeekSelected(boolean[] days) {
        repeatText.setText(TextFormatUtil.formatDaysOfWeekText(this, days));
        daysOfWeek = days;
        repeatType = Reminder.SPECIFIC_DAYS;
        showFrequency(true);
    }

    @Override
    public void onAdvancedRepeatSelection(int type, int interval, String repeatText) {
        repeatType = type;
        this.interval = interval;
        this.repeatText.setText(repeatText);
        showFrequency(true);
    }

    @OnClick(R.id.forever_row)
    public void onViewClicked() {
        foreverSwitch.toggle();
        if (foreverSwitch.isChecked()) {
            bottomRow.setVisibility(View.GONE);
        } else {
            bottomRow.setVisibility(View.VISIBLE);
        }
    }
}
