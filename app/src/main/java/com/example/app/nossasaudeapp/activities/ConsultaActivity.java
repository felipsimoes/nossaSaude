package com.example.app.nossasaudeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.adapter.ConsultaAdapter;
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
    @BindView(R.id.empty_view_consultas)
    RelativeLayout emptyViewConsultas;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.appointment);

        lvconsulta.setEmptyView(emptyViewConsultas);

        List<Consulta> listConsulta = new ArrayList<Consulta>();
        RealmResults<Consulta> consultaRealmList = realm.where(Consulta.class).findAll();
        listConsulta.addAll(consultaRealmList);
        final ConsultaAdapter adapter = new ConsultaAdapter(consultaRealmList);

        lvconsulta.setAdapter(adapter);

        final Context context = this.getBaseContext();

        lvconsulta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ConsultaViewActivity.class);
                Consulta consulta = adapter.getItem(position);
                if (consulta != null) {
                    intent.putExtra("NOTIFICATION_ID", consulta.getId());
                }
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @OnClick(R.id.btnaddconsulta)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosConsultaActivity.class));
    }
}
