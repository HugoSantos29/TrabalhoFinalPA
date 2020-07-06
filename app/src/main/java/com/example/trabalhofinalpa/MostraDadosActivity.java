package com.example.trabalhofinalpa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MostraDadosActivity extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "basedados.db";
    public static final String TABLE_NAME = "pessoa";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "NOME";
    public static final String COLUMN_3 = "IDADE";
    public static final String COLUMN_4 = "CONTATO";
    public static final String COLUMN_5 = "MORADA";



    public MostraDadosActivity(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT, IDADE INTEGER," +
                " CONTATO INTEGER, MORADA TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String nome, String idade, String contato, String morada) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2, nome);
        contentValues.put(COLUMN_3, idade);
        contentValues.put(COLUMN_4, contato);
        contentValues.put(COLUMN_5, morada);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;

    }

    public boolean updateData(String id, String nome, String idade, String contato, String morada) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1, id);
        contentValues.put(COLUMN_2, nome);
        contentValues.put(COLUMN_3, idade);
        contentValues.put(COLUMN_4, contato);
        contentValues.put(COLUMN_5, morada);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] { id });

    }
}
