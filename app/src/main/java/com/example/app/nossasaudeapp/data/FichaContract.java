package com.example.app.nossasaudeapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Felipe on 13/03/2017.
 */

public class FichaContract {

    private FichaContract() {}

    private static final String CONTENT_AUTHORITY = "com.example.app.nossasaude";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FICHAS = "fichas";

    public final static class FichaEntry implements BaseColumns {

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FICHAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FICHAS;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FICHAS);

        public static final String TABLE_NAME = "fichas";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FICHA_ALERGIA = "alergia";
        public static final String COLUMN_FICHA_DOADOR = "doador";
        public static final String COLUMN_FICHA_PESO = "peso";
        public static final String COLUMN_FICHA_ALTURA = "altura";
    }
}
