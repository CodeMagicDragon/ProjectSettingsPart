package com.part.project.projectsettingspart.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CardDao
{
    @Insert
    void insert(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);

    @Query("SELECT * FROM Card")
    Card[] getAll();

    @Query("SELECT name FROM Card")
    String[] getAllNames();

    @Query("SELECT set_name FROM Card")
    String[] getAllSetNames();

    @Query("SELECT * FROM Card WHERE id = :id")
    Card getById(int id);

    @Query("SELECT * FROM Card WHERE name = :name")
    Card getByName(String name);

    @Query("SELECT * FROM Card WHERE set_name = :set_name")
    Card[] getBySetName(String set_name);
}