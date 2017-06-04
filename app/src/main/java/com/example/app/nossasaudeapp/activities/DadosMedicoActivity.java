package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Medico;
import com.example.app.nossasaudeapp.util.RealmUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class DadosMedicoActivity extends AppCompatActivity {

    @BindView(R.id.txtmedico)
    EditText txtmedico;
    @BindView(R.id.txtespecializacao)
    EditText txtespecializacao;
    @BindView(R.id.btnSalvarmedico)
    Button btnSalvarmedico;
    @BindView(R.id.dadosmedico)
    ConstraintLayout dadosmedico;

    private long id;

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_medico);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getLongExtra("NOTIFICATION_ID", 0);

        if (id != 0) {
            fillMedicoDataOnFields(id);
        }
    }

    private void fillMedicoDataOnFields(long id) {
        Medico medico = realm.where(Medico.class).equalTo("id", id).findFirst();
        txtmedico.setText(medico.getNome());
        txtespecializacao.setText(medico.getEspecializacao());
    }

    @OnClick(R.id.btnSalvarmedico)
    public void onViewClicked() {
        if ("".equals(txtmedico.getText().toString())) {
            Snackbar.make(dadosmedico, "Preencha um nome.", Snackbar.LENGTH_SHORT).show();
        } else if ("".equals(txtespecializacao.getText().toString())) {
            Snackbar.make(dadosmedico, "Preencha uma especialização.", Snackbar.LENGTH_SHORT).show();
        } else {
            salvarMedico();
        }
    }

    private void salvarMedico() {

        final Medico medico = new Medico();

        if (id == 0) {
            id = RealmUtil.returnId(medico);
        }

        medico.setId(id);
        medico.setNome(txtmedico.getText().toString());
        medico.setEspecializacao(txtespecializacao.getText().toString());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) { realm.copyToRealmOrUpdate(medico); }
        });

        Toast.makeText(this, "Medico Salvo", Toast.LENGTH_SHORT);
        startActivity(new Intent(this, MedicoActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
