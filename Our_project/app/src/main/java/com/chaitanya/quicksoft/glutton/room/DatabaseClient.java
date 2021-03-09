package com.chaitanya.quicksoft.glutton.room;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient mInstance;
    private AppDataBase appDatabase;

    public DatabaseClient(Context context) {
        this.context = context;

        appDatabase = Room.databaseBuilder(context, AppDataBase.class,"Glutton").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDataBase getAppDatabase() {
        return appDatabase;
    }
}
