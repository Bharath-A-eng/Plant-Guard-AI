package com.rymec.plantguardai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "plant_guard.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_HISTORY = "scan_history";

    public static final String COL_ID = "id";
    public static final String COL_IMAGE = "image";
    public static final String COL_PLANT = "plant";
    public static final String COL_DISEASE = "disease";
    public static final String COL_CONFIDENCE = "confidence";
    public static final String COL_DATE = "date";
    public static final String COL_TIME = "time";

    public HistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_HISTORY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_IMAGE + " TEXT," +
                COL_PLANT + " TEXT," +
                COL_DISEASE + " TEXT," +
                COL_CONFIDENCE + " REAL," +
                COL_DATE + " TEXT," +
                COL_TIME + " TEXT)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        onCreate(db);

    }
}