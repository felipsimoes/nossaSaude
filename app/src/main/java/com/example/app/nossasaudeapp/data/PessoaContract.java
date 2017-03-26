package com.example.app.nossasaudeapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Felipe on 20/03/2017.
 */

public class PessoaContract {
    private PessoaContract() {}

    private static final String CONTENT_AUTHORITY = "com.example.app.nossasaude";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PESSOAS = "pessoas";

    public final static class PessoaEntry implements BaseColumns {
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PESSOAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE  + "/" + CONTENT_AUTHORITY + "/" + PATH_PESSOAS;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PESSOAS);

        public static final String TABLE_NAME = "pessoas";

        public static final String _ID = BaseColumns._ID;
    }
}
