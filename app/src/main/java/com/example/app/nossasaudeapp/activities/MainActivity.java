package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app.nossasaudeapp.R;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    TextView txtdia, txtmes, txtano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtdia = (TextView)findViewById(R.id.txtdiaM);
        txtmes = (TextView)findViewById(R.id.txtmesM);
        txtano = (TextView)findViewById(R.id.txtanoM);

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

        LinearLayout lMedicamento = (LinearLayout) findViewById(R.id.image_button_3);
        lMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMedicamento(v);
            }
        });

        LinearLayout lPessoa = (LinearLayout) findViewById(R.id.image_button_1);
        lPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPessoa(v);
            }
        });

        LinearLayout lConfiguracao = (LinearLayout) findViewById(R.id.image_button_4);
        lConfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConfiguracao(v);
            }
        });

        LinearLayout lFicha = (LinearLayout) findViewById(R.id.image_button_2);
        lFicha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFicha(v);
            }
        });

        RelativeLayout lLogin = (RelativeLayout) findViewById(R.id.login);
        lLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin(v);
            }
        });

    }

    public String retornaMes(int mes){

        String[] meses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto",
                "Setembro","Outubro","Novembro","Dezembro",};
        return meses[mes-1];
    }

    public void startMedicamento(View view){
        Intent intent = new Intent(this,MedicamentoActivity.class);
        startActivity(intent);
    }

    public void startPessoa(View view){
        Intent intent = new Intent(this,PessoaActivity.class);
        startActivity(intent);
    }

    public void startConfiguracao(View view){
        Intent intent = new Intent(this,ConfiguracaoActivity.class);
        startActivity(intent);
    }

    public void startFicha(View view){
        Intent intent = new Intent(this,FichaActivity.class);
        startActivity(intent);
    }

    public void startLogin(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
