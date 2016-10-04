package com.whatsfordinner.whatsfordinner;

import static android.provider.BaseColumns._ID;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "whatsfordinner.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBConstants.TABLE_GROCERIES + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConstants.GROCERIES_ITEM_NAME + " TEXT NOT NULL, "
                + DBConstants.GROCERIES_QUANTITY + " REAL, "
                + DBConstants.GROCERIES_UNIT + " TEXT);");

        db.execSQL("CREATE TABLE " + DBConstants.TABLE_RECIPES + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBConstants.RECIPES_NAME + " TEXT NOT NULL, "
                + DBConstants.RECIPES_IMG_URL + " TEXT, "
                + DBConstants.RECIPES_IMG_PATH + " TEXT, "
                + DBConstants.RECIPES_INGREDIENTS_LIST + " TEXT, "
                + DBConstants.RECIPES_DIRECTIONS + " TEXT, "
                + DBConstants.RECIPES_COUNT_AVAILABLE + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // When Android detects you’re referencing an old database (based on
        // the version number), it will call the onUpgrade( ) method
        deleteAllTablesAndReCreate(db);
    }

    private void deleteAllTablesAndReCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + DBConstants.TABLE_RECIPES + ";");
        db.execSQL("DROP TABLE " + DBConstants.TABLE_GROCERIES + ";");
        onCreate(db);
    }
}
