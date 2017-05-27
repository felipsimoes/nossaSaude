package com.example.app.nossasaudeapp.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Reminder extends RealmObject {

    // Reminder types
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 2;

    // Repetition types
    public static final int DOES_NOT_REPEAT = 0;
    public static final int HOURLY = 1;
    public static final int DAILY = 2;
    public static final int WEEKLY = 3;
    public static final int MONTHLY = 4;
    public static final int YEARLY = 5;
    public static final int SPECIFIC_DAYS = 6;
    public static final int ADVANCED = 7;

    public static final long MEDICAMENTO = 1;
    public static final long CONSULTA = 2;
    public static final long EXAME = 3;

    @PrimaryKey
    private long id;
    private long originClass;
    private String dateAndTime;
    private long repeatType;
    private String foreverState;
    private long numberToShow;
    private long numberShown;
    private byte[] daysOfWeek;
    private long interval;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginClass() {
        return originClass;
    }

    public void setOriginClass(long originClass) {
        this.originClass = originClass;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public long getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(long repeatType) {
        this.repeatType = repeatType;
    }

    public String getForeverState() {
        return foreverState;
    }

    public void setForeverState(String foreverState) {
        this.foreverState = foreverState;
    }

    public long getNumberToShow() {
        return numberToShow;
    }

    public void setNumberToShow(long numberToShow) {
        this.numberToShow = numberToShow;
    }

    public long getNumberShown() {
        return numberShown;
    }

    public void setNumberShown(long numberShown) { this.numberShown = numberShown; }

    public byte[] getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(byte[] daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
