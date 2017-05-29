package com.example.app.nossasaudeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.app.nossasaudeapp.activities.DadosMedicamentoActivity;
import com.example.app.nossasaudeapp.data.Medicamento;
import com.example.app.nossasaudeapp.util.AlarmUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MedicamentoViewActivity extends AppCompatActivity {

    Realm realm = Realm.getDefaultInstance();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nome_medicamento_view)
    TextView nomeMedicamentoView;
    @BindView(R.id.spinner_medicamento_view)
    TextView spinnerMedicamentoView;
    @BindView(R.id.dose_medicamento_view)
    TextView doseMedicamentoView;
    @BindView(R.id.repeat_medicamento_view)
    TextView repeatMedicamentoView;

    private Medicamento medicamento;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_view);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        Intent intent = getIntent();
        id = intent.getLongExtra("NOTIFICATION_ID", 0);

        medicamento = realm.where(Medicamento.class).equalTo("id", id).findFirst();
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
        Intent intent = new Intent(this, DadosMedicamentoActivity.class);
        intent.putExtra("NOTIFICATION_ID", medicamento.getId());
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
        final Medicamento medicamento =
                realm.where(Medicamento.class).equalTo("id", id).findFirst();

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        AlarmUtil.cancelAlarm(this, alarmIntent, (int) medicamento.getReminder().getId());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                medicamento.deleteFromRealm();
            }
        });
        finish();
    }
}
