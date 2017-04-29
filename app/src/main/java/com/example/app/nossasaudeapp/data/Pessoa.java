package com.example.app.nossasaudeapp.data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import static android.R.attr.name;

public class Pessoa extends RealmObject {

    @PrimaryKey
    private long id;

    private DadosPessoa dadosPessoa;

    private RealmList<Medicamento> medicamentos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DadosPessoa getDadosPessoa() {
        return dadosPessoa;
    }

    public void setDadosPessoa(DadosPessoa dadosPessoa) {
        this.dadosPessoa = dadosPessoa;
    }

    public RealmList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(RealmList<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public Pessoa() {
        dadosPessoa = new DadosPessoa();
    }

    @Override
    public String toString() {
        return (" " + this.dadosPessoa.getName()) ;
    }
}
