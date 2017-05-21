package com.example.app.nossasaudeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.app.nossasaudeapp.Adapter.MedicoAdapter;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Medico;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MedicoActivity extends AppCompatActivity {

    @BindView(R.id.lvmedico)
    ListView lvmedico;
    @BindView(R.id.btnaddmedico)
    Button btnaddmedico;
    @BindView(R.id.empty_view_medicos)
    RelativeLayout emptyViewMedicos;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);
        ButterKnife.bind(this);

        lvmedico.setEmptyView(emptyViewMedicos);

        List<Medico> listmedicos = new ArrayList<Medico>();
        RealmResults<Medico> medicoRealmList = realm.where(Medico.class).findAll();
        listmedicos.addAll(medicoRealmList);
        final MedicoAdapter adapter = new MedicoAdapter(medicoRealmList);

        lvmedico.setAdapter(adapter);

        final Context context = this.getBaseContext();

        lvmedico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, DadosMedicoActivity.class);
                Medico medico = adapter.getItem(position);
                if (medico != null) {
                    intent.putExtra("id", medico.getId());
                }
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btnaddmedico)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosMedicoActivity.class));
    }
}
