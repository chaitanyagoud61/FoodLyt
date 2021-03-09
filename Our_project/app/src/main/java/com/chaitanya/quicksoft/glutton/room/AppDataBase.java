package com.chaitanya.quicksoft.glutton.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LoginTable_entity.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract LoginTableDao loginTableDao();
}
