package com.example.app.nossasaudeapp.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

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

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_medico);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSalvarmedico)
    public void onViewClicked() {
        if ("".equals(txtmedico.getText().toString())) {
            Snackbar.make(dadosmedico,"Preencha um nome.",Snackbar.LENGTH_SHORT).show();
        }
        if ("".equals(txtespecializacao.getText().toString())) {
            Snackbar.make(dadosmedico,"Preencha uma especialização.",Snackbar.LENGTH_SHORT).show();
        } else {
            salvarmedico();
        }
    }

    private void salvarmedico() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Medico medico = new Medico();
                medico.setNome(txtmedico.getText().toString());
                medico.setEspecializacao(txtespecializacao.getText().toString());

                medico.setId(RealmUtil.returnId(medico));

                realm.copyToRealmOrUpdate(medico);
            }
        });
    }
}
