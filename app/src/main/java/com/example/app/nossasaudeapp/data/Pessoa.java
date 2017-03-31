package com.example.app.nossasaudeapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Felipe on 27/03/2017.
 */

public class Pessoa extends RealmObject {

    @PrimaryKey
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pessoa() {}
    public Pessoa(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(this.id).concat(" " + this.name) ;
    }
}
