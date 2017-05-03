package com.example.app.nossasaudeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Consulta;
import com.example.app.nossasaudeapp.data.Doenca;
import com.example.app.nossasaudeapp.util.RealmUtil;

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

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_consulta);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSalvarConsulta)
    public void onViewClicked() {
        if ("".equals(txtconsulta.getText().toString())) {
            Snackbar.make(dadosconsulta,"Preencha o nome da consulta.",Snackbar.LENGTH_SHORT).show();
        }else if ("".equals(txtdesconsulta.getText().toString())) {
            Snackbar.make(dadosconsulta,"Preencha uma descrição.",Snackbar.LENGTH_SHORT).show();
        } else {
            salvarconsulta();
        }
    }

    private void salvarconsulta() {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Consulta consulta = new Consulta();
                consulta.setNome(txtconsulta.getText().toString());
                consulta.setDescricao(txtdesconsulta.getText().toString());

                consulta.setId(RealmUtil.returnId(consulta));

                realm.copyToRealmOrUpdate(consulta);
            }
        });

        Toast.makeText(this, "Consulta Salva", Toast.LENGTH_SHORT);
        startActivity(new Intent(this, ConsultaActivity.class));
    }
}
