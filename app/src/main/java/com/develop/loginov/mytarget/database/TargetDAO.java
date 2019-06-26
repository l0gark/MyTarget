package com.develop.loginov.mytarget.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.develop.loginov.mytarget.model.Target;

import java.util.List;


@Dao
public abstract class TargetDAO {
    @Query("SELECT * FROM target where name= :targetName")
    public abstract List<Target> getWebcamsByCity(final String targetName);

    @Insert
    public abstract void insert(final Target target);

    @Update
    public abstract int update(final Target target);

    @Transaction
    public void insertOrUpdate(final Target target) {
        if (update(target) == 0) {
            insert(target);
        }
    }

    @Transaction
    public void insertOrUpdateList(final List<Target> targets) {
        for (final Target target : targets) {
            insertOrUpdate(target);
        }
    }
}
