package com.example.app.nossasaudeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.adapter.RemindersAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class TelaInicialActivity extends AppCompatActivity {

    @BindView(R.id.lvTelaInicial)
    RecyclerView lvTelaInicial;
    @BindView(R.id.btnTelaInicial)
    Button btnTelaInicial;
    @BindView(R.id.btnTelaMenu)
    Button btnTelaMenu;
    @BindView(R.id.empty_view_tela_inicial)
    RelativeLayout emptyViewTelaInicial;
    private Realm realm;
    private RemindersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        adapter = new RemindersAdapter(realm);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkAdapterIsEmpty();
            }
        });
        lvTelaInicial.setLayoutManager((new LinearLayoutManager(this)));
        lvTelaInicial.setAdapter(adapter);
        checkAdapterIsEmpty();
    }

    private void checkAdapterIsEmpty () {
        if (adapter.getItemCount() == 0) {
            emptyViewTelaInicial.setVisibility(View.VISIBLE);
        } else {
            emptyViewTelaInicial.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @OnClick({R.id.btnTelaMenu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnTelaMenu:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
