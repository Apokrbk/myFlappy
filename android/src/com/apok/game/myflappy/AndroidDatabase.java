package com.apok.game.myflappy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

/**
 * Created by Apok on 26.02.2017.
 */

public class AndroidDatabase extends MainDatabase {
    protected SQLiteOpenHelper db_connection;

    public AndroidDatabase(Context context)
    {
        db_connection = new SQLiteOpenHelper(context, database_name, null, version) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String query = "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                        COLUMN_SCORE + " INTEGER " +
                        ");";
                db.execSQL(query);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS 'highscores';");
                onCreate(db);
            }
        };
    }

    @Override
    public void execute(String sql) {
        SQLiteDatabase stmt = db_connection.getWritableDatabase();
        stmt.execSQL(sql);
    }

    @Override
    public void addToDatabase(int score) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        String query = "SELECT * FROM " + TABLE_NAME+" WHERE "+ COLUMN_SCORE+"="+score+";";
        SQLiteDatabase stmt = db_connection.getWritableDatabase();
        Cursor c = stmt.rawQuery(query, null);
        if(c.isAfterLast())
            stmt.insert(TABLE_NAME, null, values);
    }

    @Override
    public int getFirst() {
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY "+ COLUMN_SCORE +" DESC LIMIT 3";
        SQLiteDatabase stmt = db_connection.getWritableDatabase();
        Cursor c = stmt.rawQuery(query, null);
        c.moveToFirst();
        if(c.isAfterLast())
            return 0;
        int lowest=c.getInt(c.getColumnIndex(COLUMN_SCORE));
        while(!c.isAfterLast())
        {
            if(c.getInt(c.getColumnIndex(COLUMN_SCORE)) < lowest)
                lowest = c.getInt(c.getColumnIndex(COLUMN_SCORE));
            c.moveToNext();
        }
        return lowest;
    }


    public String databaseToString()
    {
        String result = "";
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY "+ COLUMN_SCORE +" DESC LIMIT 3";
        SQLiteDatabase stmt = db_connection.getWritableDatabase();
        Cursor c = stmt.rawQuery(query, null);
        c.moveToFirst();
        int i=1;
        while(!c.isAfterLast())
        {
            if(c.getString(c.getColumnIndex(COLUMN_SCORE))!= null)
            {
                result+= "\n"+i+".    " + c.getString(c.getColumnIndex(COLUMN_SCORE));
            }
            c.moveToNext();
            i++;
        }
        stmt.close();
        return result;
    }

}
