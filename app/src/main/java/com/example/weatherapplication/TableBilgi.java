package com.example.weatherapplication;

import android.provider.BaseColumns;

public class TableBilgi {

    public static final class KonumEntry implements BaseColumns{
        public static final String TABLE_NAME = "konumlar";

        public static final String COLUMN_ID = "konum_id";
        public static final String COLUMN_KONUM = "konum_text";
    }
}
