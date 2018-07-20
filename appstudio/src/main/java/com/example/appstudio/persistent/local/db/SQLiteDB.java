package com.example.appstudio.persistent.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appstudio.data.API;

public class SQLiteDB {
    private Context context;
    private static SQLiteDB instance;

    public static SQLiteDB getInstance() {
        if (instance == null)
            instance = new SQLiteDB();
        return instance;
    }

    public static SQLiteDB init(Context context) {
        SQLiteDB db = getInstance();
        db.context = context;
        return db;
    }

    public void invoke(API api){

    }

    private SQLiteDatabase createDB(String name){
        return null;
    }

    public void query(){

    }
}
