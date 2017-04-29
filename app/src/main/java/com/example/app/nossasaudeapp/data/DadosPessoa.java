package com.example.app.nossasaudeapp.data;

import io.realm.RealmObject;

public class DadosPessoa extends RealmObject {

    private String name;

    private String birthday;

    private String sex;

    private long bloodType;

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

    public long getBloodType() {
        return bloodType;
    }

    public void setBloodType(long bloodType) {
        this.bloodType = bloodType;
    }
}