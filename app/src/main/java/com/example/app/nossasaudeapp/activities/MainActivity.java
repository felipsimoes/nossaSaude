package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
    @BindView(R.id.btnTelaInicial)
    Button btnTelaInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.imgBtnPessoa, R.id.imgBtnExames, R.id.imgBtnDoenca, R.id.imgBtnMedicos, R.id.imgBtnConsultas, R.id.imgBtnMedicamentos, R.id.btnTelaInicial})
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
            case R.id.btnTelaInicial:
                startActivity(new Intent(this, TelaInicialActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TelaInicialActivity.class));
    }

}
