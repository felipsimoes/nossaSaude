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

public class MedicoActivity extends AppCompatActivity {

    @BindView(R.id.lvmedico)
    ListView lvmedico;
    @BindView(R.id.btnaddmedico)
    Button btnaddmedico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnaddmedico)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosMedicoActivity.class));
    }
}
