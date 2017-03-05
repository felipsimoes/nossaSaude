package com.example.app.nossasaudeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void startMenu(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void startCadastraUsuario(View v){
        Intent i = new Intent(this,CadastroUsuarioActivity.class);
        startActivity(i);
    }
}
