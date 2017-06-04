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

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Medico;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class MedicoViewActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nome_medico_view)
    TextView nomeMedicoView;
    @BindView(R.id.especializacao_medico_view)
    TextView especializacaoMedicoView;

    Realm realm = Realm.getDefaultInstance();
    private Medico medico;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico_view);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        Intent intent = getIntent();
        id = intent.getLongExtra("NOTIFICATION_ID", 0);

        medico = realm.where(Medico.class).equalTo("id", id).findFirst();
        fillMedicoDados();
    }

    private void fillMedicoDados() {
        nomeMedicoView.setText(medico.getNome());
        especializacaoMedicoView.setText(medico.getEspecializacao());
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
        Intent intent = new Intent(this, DadosMedicoActivity.class);
        intent.putExtra("NOTIFICATION_ID", medico.getId());
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
        final Medico medico =
                realm.where(Medico.class).equalTo("id", id).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                medico.deleteFromRealm();
            }
        });
        finish();
    }
}
