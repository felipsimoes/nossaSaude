package com.example.app.nossasaudeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.app.nossasaudeapp.adapter.MedicamentosAdapter;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Medicamento;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MedicamentoActivity extends AppCompatActivity {

    @BindView(R.id.lvmedicamento)
    ListView lvmedicamento;
    @BindView(R.id.btnaddmedicamento)
    Button btnAddMedicamento;
    @BindView(R.id.empty_view_medicamentos)
    RelativeLayout emptyViewMedicamentos;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);
        ButterKnife.bind(this);

        final Context context = this.getBaseContext();

        lvmedicamento.setEmptyView(emptyViewMedicamentos);

        RealmResults<Medicamento> medicamentoRealmList = realm.where(Medicamento.class).findAll();
        final MedicamentosAdapter adapter = new MedicamentosAdapter(medicamentoRealmList);
        lvmedicamento.setAdapter(adapter);

        lvmedicamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, MedicamentoViewActivity.class);
                Medicamento medicamento = adapter.getItem(position);
                if (medicamento != null) {
                    intent.putExtra("NOTIFICATION_ID", medicamento.getId());
                }
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btnaddmedicamento)
    public void setBtnAddMedicamento() {
        startActivity(new Intent(this, DadosMedicamentoActivity.class));
    }
}
