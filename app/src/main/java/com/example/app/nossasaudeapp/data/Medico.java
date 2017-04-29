package com.example.app.nossasaudeapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DiegoGaldino on 29/04/2017.
 */

public class Medico extends RealmObject {

    @PrimaryKey
    private long id;
    private String nome;
    private String especializacao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(String especializacao) {
        this.especializacao = especializacao;
    }
}
