package com.example.app.nossasaudeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
