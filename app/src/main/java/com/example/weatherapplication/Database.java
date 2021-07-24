package com.example.weatherapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME ="Konum";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_KONUM_CREATE =
            "CREATE TABLE " + TableBilgi.KonumEntry.TABLE_NAME + " (" +
                    TableBilgi.KonumEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableBilgi.KonumEntry.COLUMN_KONUM + " TEXT " +
                    ")";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_KONUM_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableBilgi.KonumEntry.TABLE_NAME);

        onCreate(db);
    }

    public void addKonum(String konum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableBilgi.KonumEntry.COLUMN_KONUM, konum.trim());

        long result = db.insert(TableBilgi.KonumEntry.TABLE_NAME, null, cv);

        if (result > -1)
            Log.i("Database", "Konum başarıyla kaydedildi");
        else
            Log.i("Database", "Konum kaydedilemedi");

        db.close();
    }

    public ArrayList<Konum> getKonumList() {
        ArrayList<Konum> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                TableBilgi.KonumEntry.COLUMN_ID,
                TableBilgi.KonumEntry.COLUMN_KONUM};

        Cursor c = db.query(TableBilgi.KonumEntry.TABLE_NAME, projection, null, null, null, null, null);
        while (c.moveToNext()) {
            data.add(new Konum(c.getString(c.getColumnIndex(TableBilgi.KonumEntry.COLUMN_ID)), c.getString(c.getColumnIndex(TableBilgi.KonumEntry.COLUMN_KONUM))));
        }
        c.close();
        db.close();

        return data;
    }

    public void deleteKonum(String konumID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableBilgi.KonumEntry.TABLE_NAME, TableBilgi.KonumEntry.COLUMN_ID + "=?", new String[]{konumID});
        db.close();
    }

}
