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
import com.example.app.nossasaudeapp.data.Exame;
import com.example.app.nossasaudeapp.util.RealmUtil;

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

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_exame);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSalvarExame)
    public void onViewClicked() {
        if ("".equals(txtexame.getText().toString())) {
            Snackbar.make(dadosexame,"Preencha o nome do exame.",Snackbar.LENGTH_SHORT).show();
        }else if ("".equals(txtdesexame.getText().toString())) {
            Snackbar.make(dadosexame,"Preencha uma descrição.",Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Exame Salvo", Toast.LENGTH_SHORT);
            salvarexame();
        }
    }

    private void salvarexame() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Exame exame = new Exame();
                exame.setNome(txtexame.getText().toString());
                exame.setDescricao(txtdesexame.getText().toString());

                exame.setId(RealmUtil.returnId(exame));

                realm.copyToRealmOrUpdate(exame);
            }
        });
        //Toast.makeText(this, "Exame Salvo", Toast.LENGTH_SHORT);
        //Snackbar.make(dadosexame,"Exame Salvo.",Snackbar.LENGTH_SHORT).show();
        startActivity(new Intent(this, ExameActivity.class));
    }
}
