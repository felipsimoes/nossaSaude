package com.example.app.nossasaudeapp.util;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmUtil {

    private static Realm realm = Realm.getDefaultInstance();

    public static long returnId(RealmObject realmObject) {
        Number num = (realm.where(realmObject.getClass()).max("id"));
        long id = num == null ? 1 : ((long) num ) + 1;
        return id;
    }
}
