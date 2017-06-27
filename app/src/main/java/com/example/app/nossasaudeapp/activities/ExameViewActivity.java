package com.example.app.nossasaudeapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.app.nossasaudeapp.AlarmReceiver;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Exame;
import com.example.app.nossasaudeapp.util.AlarmUtil;
import com.example.app.nossasaudeapp.util.DateAndTimeUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class ExameViewActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nome_exame_view)
    TextView nomeExameView;
    @BindView(R.id.descricao_exame_view)
    TextView descricaoExameView;
    @BindView(R.id.hora_exame_view)
    TextView horaExameView;
    @BindView(R.id.data_exame_view)
    TextView dataExameView;

    Realm realm = Realm.getDefaultInstance();
    private Exame exame;
    private long id;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame_view);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.exam);

        Intent intent = getIntent();
        id = intent.getLongExtra("NOTIFICATION_ID", 0);

        exame = realm.where(Exame.class).equalTo("id", id).findFirst();
        fillExameDados();
    }

    private void fillExameDados() {
        nomeExameView.setText(exame.getNome());
        descricaoExameView.setText(exame.getDescricao());

        calendar = DateAndTimeUtil.parseDateAndTime(exame.getReminder().getDateAndTime());

        dataExameView.setText(DateAndTimeUtil.toStringReadableDate(calendar));
        horaExameView.setText(DateAndTimeUtil.toStringReadableTime(calendar, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_medicamento_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                confirmDelete();
                return true;
            case R.id.action_edit:
                actionEdit();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void actionEdit() {
        Intent intent = new Intent(this, DadosExameActivity.class);
        intent.putExtra("NOTIFICATION_ID", exame.getId());
        startActivity(intent);
        finish();
    }

    public void confirmDelete() {
        new AlertDialog.Builder(this, R.style.Dialog)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        actionDelete();
                    }
                })
                .setNegativeButton(R.string.no, null).show();
    }

    private void actionDelete() {
        final Exame exame =
                realm.where(Exame.class).equalTo("id", id).findFirst();

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        AlarmUtil.cancelAlarm(this, alarmIntent, (int) exame.getReminder().getId());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                exame.deleteFromRealm();
            }
        });
        startActivity(new Intent(this, ExameActivity.class));
    }
}
