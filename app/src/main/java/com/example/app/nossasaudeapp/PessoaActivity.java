package com.example.app.nossasaudeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.nossasaudeapp.data.Pessoa;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class PessoaActivity extends AppCompatActivity {

    public static final String LOG_TAG = "PESSOA_ACTIVITY";
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        ListView listaPessoas = (ListView) findViewById(R.id.lvpessoa);

        final List<Pessoa> pessoas = new ArrayList<Pessoa>();
        pessoas.add(new Pessoa(1, "Jose"));

        final ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(this,
                android.R.layout.simple_list_item_1, pessoas);

        listaPessoas.setAdapter(adapter);

        final Context context = this;
        Button btnAdd = (Button) findViewById(R.id.btnaddpessoa);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Pessoa p = new Pessoa();
                        p.setName(((TextView) findViewById(R.id.txtnomepessoa)).getText().toString());

                        Number num = (realm.where(Pessoa.class).max("id"));
                        long id = num == null ? 1 : ((long) num ) + 1;

                        Log.d(LOG_TAG, String.valueOf(id));
                        p.setId(id);
                        realm.copyToRealm(p);

                        adapter.add(p);
                    }
                });
            }
        });

        listaPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {

                String item = ((TextView)view).getText().toString();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Pessoa pessoaPorID = realm.where(Pessoa.class).equalTo("id", id).findFirst();
                        Log.d(LOG_TAG, "ID - ".concat(Long.toString(pessoaPorID.getId())));
                    }
                });

                Intent i = new Intent(PessoaActivity.this,DadosPessoaActivity.class);

                startActivity(i);

                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
            }
        });
    }


}
