package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app.nossasaudeapp.R;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    TextView txtdata;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        txtdata = (TextView) findViewById(R.id.txtdatacompleta);

        long date = System.currentTimeMillis();
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        String dateString1 = dia.format(date);
        String dateString2 = mes.format(date);
        String dateString3 = ano.format(date);

        txtdata.setText("Hoje "+dateString1+" de "+retornaMes(Integer.parseInt(dateString2))+" de "+dateString3);
    }

    public String retornaMes(int mes) {
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
                "Setembro", "Outubro", "Novembro", "Dezembro",};
        return meses[mes - 1];
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
                startActivity(new Intent(this, DadosMedicamentoActivity.class));
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
}
