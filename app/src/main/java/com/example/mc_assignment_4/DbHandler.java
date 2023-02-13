package com.example.mc_assignment_4;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends  SQLiteOpenHelper {

    private static final String DATABASE_NAME = "qurandb";
    private static final String TABLE_NAME = "quranmetadata";
    private static final String COLUMN_NUM = "num";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_PARAH = "parah";
    private static final String COLUMN_SURAH = "surah";
    private static final String COLUMN_TRANSLATION = "translation";
    Context context;

    public DbHandler(Context context) {super(context, DATABASE_NAME, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + COLUMN_NUM + " TEXT PRIMARY KEY,"
                + COLUMN_TEXT + " TEXT,"
                + COLUMN_PARAH + " TEXT,"
                + COLUMN_SURAH + " TEXT,"
                + COLUMN_TRANSLATION + " TEXT)";
        db.execSQL(sql);

    }

    public void fillData()
    {
                AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("QuranMetaData.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String[]> rows = new ArrayList<>();
        String line = null;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            rows.add(line.split(","));
        }

        SQLiteDatabase db = this.getWritableDatabase();
        String insertRow = "INSERT INTO quranmetadata (num,text,parah,surah,translation) VALUES (?,?,?,?,?);";
        SQLiteStatement statement = db.compileStatement(insertRow);
        for (String[] row : rows) {
            statement.clearBindings();
            for (int i = 0; i < row.length; i++) {
                statement.bindString(i + 1, row[i]);
            }
            statement.executeInsert();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public ArrayList<String> searchSurah(String parah, String surah) {
        ArrayList<String> surahText = new ArrayList<>();
        String sql = "SELECT text FROM " + TABLE_NAME + " where parah="+parah+" AND surah="+surah;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                surahText.add(cursor.getString(0));


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return surahText;
    }

    public ArrayList<String> searchTranslation(String parah, String surah) {
        ArrayList<String> translationText = new ArrayList<>();
        String sql = "SELECT translation FROM " + TABLE_NAME + " where parah="+parah+" AND surah="+surah;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                translationText.add(cursor.getString(0));


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return translationText;
    }
}