package com.example.app.nossasaudeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;

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
