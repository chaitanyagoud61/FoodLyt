package com.chaitanya.quicksoft.glutton.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LoginTableDao  {

    @Query("select * from logintable_entity")
    LoginTable_entity getAll();

    @Insert
    void insert(LoginTable_entity loginTable_entity);

    @Update
    void update(LoginTable_entity loginTable_entity);

    @Query("DELETE FROM logintable_entity")
    void DeleteTable();

}
