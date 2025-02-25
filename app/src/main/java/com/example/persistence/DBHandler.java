package com.example.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    // Constants for compile-time checking of database creation below.
    private static final String DB_NAME = "namesdb";
    // This may be used for migration in the future.
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "names";
    private static final String ID_COL = "id";
    private static final String FIRST_COL = "first";
    private static final String LAST_COL = "last";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // This method is called to create the database if it.does not yet@Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME + " ("
                        + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FIRST_COL + " TEXT,"
                        + LAST_COL + " TEXT)";
        db.execSQL(query);
    }

    // Add a single name.
    public void add(Name n) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRST_COL, n.getFirst());
        values.put(LAST_COL, n.getLast());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Get the whole list of names.
    public ArrayList<Name> getNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        // This will be the result.
        ArrayList<Name> names = new ArrayList<>();
        Cursor cursor =
                db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(new Name(cursor.getString(1),
                        cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This is probably NOT what you want! Users will want their
        // data to be migrated to the new database.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}