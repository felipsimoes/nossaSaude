package com.example.app.nossasaudeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.app.nossasaudeapp.Adapter.ConsultaAdapter;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Consulta;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConsultaActivity extends AppCompatActivity {

    @BindView(R.id.lvconsulta)
    ListView lvconsulta;
    @BindView(R.id.btnaddconsulta)
    Button btnaddconsulta;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        ButterKnife.bind(this);

        List<Consulta> listConsulta = new ArrayList<Consulta>();
        RealmResults<Consulta> consultaRealmList = realm.where(Consulta.class).findAll();
        listConsulta.addAll(consultaRealmList);
        final ConsultaAdapter adapter = new ConsultaAdapter(consultaRealmList);

        lvconsulta.setAdapter(adapter);

        final Context context = this.getBaseContext();

        lvconsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, DadosConsultaActivity.class);
                Consulta consulta = adapter.getItem(position);
                if (consulta != null) {
                    intent.putExtra("id", consulta.getId());
                }
                startActivity(intent);
            }
        });

    }

    @OnClick(R.id.btnaddconsulta)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosConsultaActivity.class));
    }
}
