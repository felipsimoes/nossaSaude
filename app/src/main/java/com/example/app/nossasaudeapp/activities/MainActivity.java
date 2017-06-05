package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.app.nossasaudeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imgBtnMedicamentos)
    LinearLayout imgBtnMedicamentos;
    @BindView(R.id.imgBtnConsultas)
    LinearLayout imgBtnConsultas;
    @BindView(R.id.imgBtnMedicos)
    LinearLayout imgBtnMedicos;
    @BindView(R.id.imgBtnPessoa)
    LinearLayout imgBtnPessoa;
    @BindView(R.id.imgBtnDoenca)
    LinearLayout imgBtnDoenca;
    @BindView(R.id.imgBtnExames)
    LinearLayout imgBtnExames;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @OnClick({R.id.imgBtnPessoa, R.id.imgBtnExames, R.id.imgBtnDoenca, R.id.imgBtnMedicos, R.id.imgBtnConsultas, R.id.imgBtnMedicamentos})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBtnPessoa:
                startActivity(new Intent(this, DadosPessoaActivity.class));
                break;
            case R.id.imgBtnExames:
                startActivity(new Intent(this, ExameActivity.class));
                break;
            case R.id.imgBtnMedicamentos:
                startActivity(new Intent(this, MedicamentoActivity.class));
                break;
            case R.id.imgBtnDoenca:
                startActivity(new Intent(this, DoencaActivity.class));
                break;
            case R.id.imgBtnMedicos:
                startActivity(new Intent(this, MedicoActivity.class));
                break;
            case R.id.imgBtnConsultas:
                startActivity(new Intent(this, ConsultaActivity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TelaInicialActivity.class));
    }
}
