package com.example.app.nossasaudeapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Felipe on 01/04/2017.
 */

public class Medicamento extends RealmObject {

    @PrimaryKey

    private long id;

    private String nome;

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

}
