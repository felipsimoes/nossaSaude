package com.example.app.nossasaudeapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Medicamento extends RealmObject {

    @PrimaryKey
    private long id;

    private String nome;

    private Reminder reminder;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Reminder getReminder() { return reminder; }

    public void setReminder(Reminder reminder) { this.reminder = reminder; }

    @Override
    public String toString() {
        return this.nome;
    }
}
