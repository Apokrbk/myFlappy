package com.apok.game.myflappy.desktop;

import com.apok.game.myflappy.MainDatabase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Apok on 26.02.2017.
 */

public class DesktopDatabase extends MainDatabase {
    protected Connection db_connection;
    protected Statement stmt;
    protected boolean nodatabase=false;

    public DesktopDatabase()
    {
        loadDatabase();
    }


    @Override
    public String databaseToString() {
        return "empty";
    }

    public void execute(String sql){
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToDatabase(int score) {

    }

    @Override
    public int getFirst() {
        return 0;
    }


    public int executeUpdate(String sql){
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void loadDatabase(){
        File file = new File (database_name+".db");
        if(!file.exists())
            nodatabase=true;
        try {
            Class.forName("org.sqlite.JDBC");
            db_connection = DriverManager.getConnection("jdbc:sqlite:"+database_name+".db");
            stmt = db_connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void upgradeVersion() {
        execute("PRAGMA user_version="+version);
    }
    private boolean isNewDatabase() {
        return nodatabase;
    }

}
