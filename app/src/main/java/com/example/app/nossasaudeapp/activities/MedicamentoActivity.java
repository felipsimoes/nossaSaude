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

public class MedicamentoActivity extends AppCompatActivity {

    @BindView(R.id.lvmedicamento)
    ListView lvmedicamento;
    @BindView(R.id.btnaddmedicamento)
    Button btnaddmedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnaddmedicamento)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosMedicamentoActivity.class));
    }
}
