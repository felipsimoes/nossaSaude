package com.example.app.nossasaudeapp.util;

import java.util.Arrays;
import java.util.BitSet;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmUtil {

    private static Realm realm = Realm.getDefaultInstance();

    public static long returnId(RealmObject realmObject) {
        Number num = (realm.where(realmObject.getClass()).max("id"));
        long id = num == null ? 1 : ((long) num ) + 1;
        return id;
    }

    public static byte[] convertToByteArray(boolean[] booleans) {
        byte[] result = new byte[booleans.length/8];

        for (int i=0; i<result.length; i++) {
            int index = i*8;
            byte b = (byte)(
                    (booleans[index+0] ? 1<<7 : 0) +
                            (booleans[index+1] ? 1<<6 : 0) +
                            (booleans[index+2] ? 1<<5 : 0) +
                            (booleans[index+3] ? 1<<4 : 0) +
                            (booleans[index+4] ? 1<<3 : 0) +
                            (booleans[index+5] ? 1<<2 : 0) +
                            (booleans[index+6] ? 1<<1 : 0) +
                            (booleans[index+7] ? 1 : 0));
            result[i] = b;
        }

        return result;
    }

    public static boolean[] convertToBoolArray(byte[] byteArr) {

        int numberOfBits = (byteArr.length) * 8;

        boolean[] boolArr = new boolean[numberOfBits];

        int j =0;

        for(int i=0; i<byteArr.length; i++){

            Byte value = byteArr[i];

            boolArr[7+j] = ((value & 0x01) != 0);
            boolArr[6+j] = ((value & 0x02) != 0);
            boolArr[5+j] = ((value & 0x04) != 0);
            boolArr[4+j] = ((value & 0x08) != 0);
            boolArr[3+j] = ((value & 0x10) != 0);
            boolArr[2+j] = ((value & 0x20) != 0);
            boolArr[1+j] = ((value & 0x40) != 0);
            boolArr[0+j] = ((value & 0x80) != 0);

            j+= 8;
        }

        return boolArr;
    }
}
