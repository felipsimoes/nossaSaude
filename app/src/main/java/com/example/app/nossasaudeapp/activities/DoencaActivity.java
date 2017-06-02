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

import com.example.app.nossasaudeapp.Adapter.DoencaAdapter;
import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.Doenca;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class DoencaActivity extends AppCompatActivity {

    @BindView(R.id.lvdoenca)
    ListView lvdoenca;
    @BindView(R.id.btnadddoenca)
    Button btnadddoenca;
    @BindView(R.id.empty_view_doencas)
    RelativeLayout emptyViewDoencas;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doenca);
        ButterKnife.bind(this);

        lvdoenca.setEmptyView(emptyViewDoencas);

        List<Doenca> listdoenca = new ArrayList<Doenca>();
        RealmResults<Doenca> doencaRealmList = realm.where(Doenca.class).findAll();
        listdoenca.addAll(doencaRealmList);
        final DoencaAdapter adapter = new DoencaAdapter(doencaRealmList);

        lvdoenca.setAdapter(adapter);

        final Context context = this.getBaseContext();

        lvdoenca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, DoencaViewActivity.class);
                Doenca doenca = adapter.getItem(position);
                if (doenca != null) {
                    intent.putExtra("NOTIFICATION_ID", doenca.getId());
                }
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.btnadddoenca)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosDoencaActivity.class));
    }
}
