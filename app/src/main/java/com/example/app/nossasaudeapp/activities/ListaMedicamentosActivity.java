package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Medicamento;
import com.example.app.nossasaudeapp.data.Pessoa;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static java.util.Collections.addAll;

public class ListaMedicamentosActivity extends AppCompatActivity {

    @BindView(R.id.lvmedicamento)
    ListView lvmedicamento;
    @BindView(R.id.btnaddmedicamento)
    Button btnAddMedicamento;
    @BindView(R.id.activity_dados_medicamento)
    RelativeLayout activityDadosMedicamento;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medicamento);
        ButterKnife.bind(this);

        Realm realm = Realm.getDefaultInstance();

        List<Medicamento> listMedicamentos = new ArrayList<Medicamento>();
        RealmResults<Medicamento> medicamentoRealmList = realm.where(Medicamento.class).findAll();
        listMedicamentos.addAll(medicamentoRealmList);
        final ArrayAdapter<Medicamento> adapter = new ArrayAdapter<Medicamento>(this,
                android.R.layout.simple_list_item_1, listMedicamentos);

        lvmedicamento.setAdapter(adapter);

    }

    @OnClick(R.id.btnaddmedicamento)
    public void setBtnAddMedicamento() {
        startActivity(new Intent(this, MedicamentoActivity.class));
    }
}
