package com.apok.game.myflappy;


/**
 * Created by Apok on 26.02.2017.
 */

public abstract class MainDatabase {

    protected static String database_name = "highscores";
    protected static MainDatabase instance = null;
    protected static int version = 1;
    public static final String TABLE_NAME = "highscores";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SCORE = "_score";


    public abstract String databaseToString();
    public abstract void execute(String sql);
    public abstract void addToDatabase(int score);
    public abstract int getFirst();


    public void onCreate()
    {

    }

    public void onUpgrade()
    {

    }

    public boolean isEmpty()
    {
        return false;
    }

}
