package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.example.app.nossasaudeapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExameActivity extends AppCompatActivity {

    @BindView(R.id.lvexames)
    ListView lvexames;
    @BindView(R.id.btnaddexame)
    Button btnaddexame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnaddexame)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosExameActivity.class));
    }
}
