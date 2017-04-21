package com.example.app.nossasaudeapp.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DadosPessoa extends RealmObject {

    @PrimaryKey
    private long id;

    private String name;

    private String birthday;

    private String sex;

    private String bloodType;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
}