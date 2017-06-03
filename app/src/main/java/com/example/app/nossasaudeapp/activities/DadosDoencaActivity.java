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
import com.example.app.nossasaudeapp.data.Doenca;
import com.example.app.nossasaudeapp.data.Medico;
import com.example.app.nossasaudeapp.util.RealmUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class DadosDoencaActivity extends AppCompatActivity {

    @BindView(R.id.txtdoenca)
    EditText txtdoenca;
    @BindView(R.id.txtdesdoenca)
    EditText txtdesdoenca;
    @BindView(R.id.btnSalvarDoenca)
    Button btnSalvarDoenca;
    @BindView(R.id.dadosdoenca)
    ConstraintLayout dadosdoenca;

    private long id;

    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_doenca);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        id = intent.getLongExtra("NOTIFICATION_ID", 0);

        if (id != 0) {
            fillDoencaDataOnFields(id);
        }
    }

    private void fillDoencaDataOnFields(long id) {
        Doenca doenca = realm.where(Doenca.class).equalTo("id", id).findFirst();
        txtdoenca.setText(doenca.getNome());
        txtdesdoenca.setText(doenca.getNome());
    }

    @OnClick(R.id.btnSalvarDoenca)
    public void onViewClicked() {

        if ("".equals(txtdoenca.getText().toString())) {
            Snackbar.make(dadosdoenca, "Preencha o nome da doença.", Snackbar.LENGTH_SHORT).show();
        } else if ("".equals(txtdesdoenca.getText().toString())) {
            Snackbar.make(dadosdoenca, "Preencha uma descrição.", Snackbar.LENGTH_SHORT).show();
        } else {
            salvardoenca();
        }
    }

    private void salvardoenca() {

        final Doenca doenca = new Doenca();

        if (id == 0) {
            id = RealmUtil.returnId(doenca);
        }
        doenca.setId(id);
        doenca.setNome(txtdoenca.getText().toString());
        doenca.setDescricao(txtdesdoenca.getText().toString());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) { realm.copyToRealmOrUpdate(doenca); }
        });

        Toast.makeText(this, "Doença Salva", Toast.LENGTH_SHORT);
        startActivity(new Intent(this, DoencaActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
