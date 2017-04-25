package com.example.app.nossasaudeapp.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app.nossasaudeapp.R;
import com.example.app.nossasaudeapp.data.DadosPessoa;
import com.example.app.nossasaudeapp.data.Pessoa;
import com.example.app.nossasaudeapp.util.DateAndTimeUtil;
import com.example.app.nossasaudeapp.util.RealmUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static android.R.attr.id;

public class DadosPessoaActivity extends AppCompatActivity {

    @BindView(R.id.nomeDadosPessoa)
    EditText nomeDadosPessoa;
    @BindView(R.id.dtNascimentoDadosPessoa)
    TextView dtNascimentoDadosPessoa;
    @BindView(R.id.radioGroupSexo)
    RadioGroup radioGroupSexo;
    @BindView(R.id.tipoSanguineo)
    TextView tipoSanguineo;
    @BindView(R.id.spinnerTipoSanguineo)
    Spinner spinnerTipoSanguineo;
    @BindView(R.id.btnSalvarDadosPessoa)
    Button btnSalvarDadosPessoa;
    @BindView(R.id.activity_dados_pessoa)
    RelativeLayout activityDadosPessoa;
    private Calendar calendar;

    public static final String LOG_TAG = "DADOS_PESSOA_ACTIVITY";
    public static final long ID_PERSON_PHONE_OWNER = 1;
    private Realm realm = Realm.getDefaultInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoa);
        ButterKnife.bind(this);

        calendar = Calendar.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_sanguineo_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoSanguineo.setAdapter(adapter);

        activityLoad();
    }

    private void activityLoad() {

        Pessoa owner = realm.where(Pessoa.class).equalTo("id", ID_PERSON_PHONE_OWNER).findFirst();
        if (owner.getDadosPessoa() != null) {
            DadosPessoa dadosPessoa = owner.getDadosPessoa();

            nomeDadosPessoa.setText(dadosPessoa.getName());
            dtNascimentoDadosPessoa.setText(dadosPessoa.getBirthday());

            if (dadosPessoa.getSex() != null) {
                if ( dadosPessoa.getSex().equals("Masculino"))
                    radioGroupSexo.check(R.id.radioButtonMale);
                else
                    radioGroupSexo.check(R.id.radioButtonFemale);
            }

//            spinnerTipoSanguineo.setSelection();
        }
    }

    @OnClick(R.id.dtNascimentoDadosPessoa)
    public void datePicker() {
        DatePickerDialog DatePicker = new DatePickerDialog(DadosPessoaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dtNascimentoDadosPessoa.setText(DateAndTimeUtil.toStringReadableDateBR(calendar));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        DatePicker.show();
    }

    @OnClick(R.id.btnSalvarDadosPessoa)
    public void savePersonData() {

        if(nomeDadosPessoa.getText().toString().trim() == null) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "Entre com seu nome, por favor", Snackbar.LENGTH_SHORT);
        }
        if(dtNascimentoDadosPessoa.getText().toString() == null) {

        }
        if(radioGroupSexo.getCheckedRadioButtonId() == -1) {
            Snackbar.make(findViewById(R.id.myCoordinatorLayout),
                    "Selecione seu sexo, por favor", Snackbar.LENGTH_SHORT);
        }
        else {
            saveDadosPessoa();
        }

    }

    public void saveDadosPessoa() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DadosPessoa dadosPessoa = realm.createObject(DadosPessoa.class);

                dadosPessoa.setName(nomeDadosPessoa.getText().toString());

                dadosPessoa.setBirthday(dtNascimentoDadosPessoa.getText().toString());

                RadioButton radioSexButton = (RadioButton)
                        findViewById(radioGroupSexo.getCheckedRadioButtonId());

                dadosPessoa.setSex(radioSexButton.getText().toString());

                dadosPessoa.setBloodType(spinnerTipoSanguineo.getSelectedItem().toString());

                Pessoa owner = realm.where(Pessoa.class).
                        equalTo("id", ID_PERSON_PHONE_OWNER).findFirst();

                owner.setDadosPessoa(dadosPessoa);

                Log.d(LOG_TAG, String.valueOf(id));

                realm.copyToRealmOrUpdate(owner);
            }
        });

        startActivity(new Intent(this, MainActivity.class));
    }
}
