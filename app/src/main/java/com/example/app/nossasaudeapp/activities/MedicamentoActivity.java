package com.example.app.nossasaudeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.app.nossasaudeapp.MedicamentosAdapter;
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

import static android.widget.Toast.LENGTH_SHORT;
import static java.util.Collections.addAll;

public class ListaMedicamentosActivity extends AppCompatActivity {

    @BindView(R.id.lvmedicamento)
    ListView lvmedicamento;
    @BindView(R.id.btnaddmedicamento)
    Button btnAddMedicamento;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medicamento);
        ButterKnife.bind(this);

        List<Medicamento> listMedicamentos = new ArrayList<Medicamento>();
        RealmResults<Medicamento> medicamentoRealmList = realm.where(Medicamento.class).findAll();
        listMedicamentos.addAll(medicamentoRealmList);
        final MedicamentosAdapter adapter = new MedicamentosAdapter(medicamentoRealmList);

        lvmedicamento.setAdapter(adapter);

        final Context context = this.getBaseContext();

        lvmedicamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, MedicamentoActivity.class);
                Medicamento medicamento = adapter.getItem(position);
                if (medicamento != null) {
                    intent.putExtra("id", medicamento.getId());
                }
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btnaddmedicamento)
    public void setBtnAddMedicamento() {
        startActivity(new Intent(this, MedicamentoActivity.class));
    }
}
