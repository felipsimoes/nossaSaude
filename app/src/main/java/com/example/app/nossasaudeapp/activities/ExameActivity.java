package com.example.app.nossasaudeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.example.app.nossasaudeapp.adapter.ExameAdapter;
import com.example.app.nossasaudeapp.data.Exame;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class ExameActivity extends AppCompatActivity {

    @BindView(R.id.lvexames)
    ListView lvexames;
    @BindView(R.id.btnaddexame)
    Button btnaddexame;
    @BindView(R.id.dadosexamelist)
    ConstraintLayout dadosexamelist;
    @BindView(R.id.empty_view_exames)
    RelativeLayout emptyViewExames;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.exam);

        lvexames.setEmptyView(emptyViewExames);

        List<Exame> listexames = new ArrayList<Exame>();
        RealmResults<Exame> exameRealmList = realm.where(Exame.class).findAll();
        listexames.addAll(exameRealmList);
        final ExameAdapter adapter = new ExameAdapter(exameRealmList);

        lvexames.setAdapter(adapter);

        final Context context = this.getBaseContext();

        lvexames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, ExameViewActivity.class);
                Exame exame = adapter.getItem(position);
                if (exame != null) {
                    intent.putExtra("NOTIFICATION_ID", exame.getId());
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

    @OnClick(R.id.btnaddexame)
    public void onViewClicked() {
        startActivity(new Intent(this, DadosExameActivity.class));
    }
}
