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

public class ConsultaActivity extends AppCompatActivity {

    @BindView(R.id.lvconsulta)
    ListView lvconsulta;
    @BindView(R.id.btnaddconsulta)
    Button btnaddconsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnaddconsulta)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosConsultaActivity.class));
    }
}
