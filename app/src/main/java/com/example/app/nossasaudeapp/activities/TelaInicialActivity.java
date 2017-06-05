package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.adapter.RemindersAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class TelaInicialActivity extends AppCompatActivity {

    @BindView(R.id.lvTelaInicial)
    RecyclerView lvTelaInicial;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        realm = Realm.getDefaultInstance();

        final RemindersAdapter adapter = new RemindersAdapter(realm);
        lvTelaInicial.setLayoutManager((new LinearLayoutManager(this)));
        lvTelaInicial.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu:
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
