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

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PessoaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa);

        ListView lv = (ListView) findViewById(R.id.lvpessoa);

        Pessoa[] items = {
                new Pessoa(1, "Jose"),
                new Pessoa(2, "Pedro"),
        };

        ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(this,
                android.R.layout.simple_list_item_1, items);

        lv.setAdapter(adapter);

        final Context context = this;
        Button btnAdd = (Button) findViewById(R.id.btnaddpessoa);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Realm.init(getBaseContext());
//                RealmConfiguration myConfig = new RealmConfiguration.Builder()
//                        .name("myrealm.realm")
//                        .inMemory()
//                        .build();

                Realm realm = Realm.getDefaultInstance();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Log.d("TESTE", "Começando");
                        Pessoa p = new Pessoa();
                        p.setName(((TextView) findViewById(R.id.txtnomepessoa)).getText().toString());
                        p.set_ID(1);
                        realm.copyToRealm(p);
                        Log.d("TESTE","FINALIZANDO");
                    }
                });
            }
        });
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                String item = ((TextView)view).getText().toString();
//                Intent i = new Intent(PessoaActivity.this,DadosPessoaActivity.class);
//                startActivity(i);
//                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
//            }
//        });
    }


}
