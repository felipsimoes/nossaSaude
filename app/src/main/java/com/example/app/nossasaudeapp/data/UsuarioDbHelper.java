package com.example.app.nossasaudeapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.app.nossasaudeapp.data.UsuarioContract.UsuarioEntry;

/**
 * Created by Felipe on 09/03/2017.
 */

public class UsuarioDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = UsuarioDbHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "nossasaude.db";
    public static final int DATABASE_VERSION = 1;
    private Context mContext;

    public UsuarioDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String TEXT_TYPE = " TEXT";
        final String INTEGER_TYPE = " INTEGER";
        final String FLOAT_TYPE = " REAL";
        final String BLOB_TYPE = " BLOB";
        final String COMMA_SEPARATOR = ",";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsuarioEntry.TABLE_NAME);
        onCreate(db);
    }
}
