package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app.nossasaudeapp.R;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    TextView txtdia, txtmes, txtano;
    @BindView(R.id.imgBtnDadosPessoais)
    LinearLayout imgBtnDadosPessoais;
    @BindView(R.id.imgBtnFichaTecnica)
    LinearLayout imgBtnFichaTecnica;
    @BindView(R.id.imgBtnMedicamentos)
    LinearLayout imgBtnMedicamentos;
    @BindView(R.id.imgBtnConfiguracoes)
    LinearLayout imgBtnConfiguracoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        txtdia = (TextView) findViewById(R.id.txtdiaM);
        txtmes = (TextView) findViewById(R.id.txtmesM);
        txtano = (TextView) findViewById(R.id.txtanoM);

        long date = System.currentTimeMillis();
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat mes = new SimpleDateFormat("MM");
        SimpleDateFormat ano = new SimpleDateFormat("yyyy");
        String dateString1 = dia.format(date);
        String dateString2 = mes.format(date);
        String dateString3 = ano.format(date);
        txtdia.setText(dateString1);
        txtmes.setText(retornaMes(Integer.parseInt(dateString2)));
        txtano.setText(dateString3);

        RelativeLayout lLogin = (RelativeLayout) findViewById(R.id.login);
        lLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin(v);
            }
        });

    }

    public String retornaMes(int mes) {

        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
                "Setembro", "Outubro", "Novembro", "Dezembro",};
        return meses[mes - 1];
    }

    public void startLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.imgBtnDadosPessoais, R.id.imgBtnFichaTecnica, R.id.imgBtnMedicamentos, R.id.imgBtnConfiguracoes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBtnDadosPessoais:
                startActivity(new Intent(this, DadosPessoaActivity.class));
                break;
            case R.id.imgBtnFichaTecnica:
                startActivity(new Intent(this, FichaActivity.class));
                break;
            case R.id.imgBtnMedicamentos:
                startActivity(new Intent(this, MedicamentoActivity.class));
                break;
            case R.id.imgBtnConfiguracoes:
                startActivity(new Intent(this, ConfiguracaoActivity.class));
                break;
        }
    }
}
