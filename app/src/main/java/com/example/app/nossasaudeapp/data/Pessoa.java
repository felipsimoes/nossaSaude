package com.example.app.nossasaudeapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Felipe on 27/03/2017.
 */

public class Pessoa extends RealmObject {

    @PrimaryKey
    private int _ID;

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Pessoa() {}
    public Pessoa(int id, String name){
        this._ID = id;
        this.name = name;
    }
}
