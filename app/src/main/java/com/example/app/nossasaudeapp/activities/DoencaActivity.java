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

public class DoencaActivity extends AppCompatActivity {

    @BindView(R.id.lvdoenca)
    ListView lvdoenca;
    @BindView(R.id.btnadddoenca)
    Button btnadddoenca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doenca);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnadddoenca)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosDoencaActivity.class));
    }
}
