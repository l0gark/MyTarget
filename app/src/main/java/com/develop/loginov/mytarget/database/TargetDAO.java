package com.develop.loginov.mytarget.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.develop.loginov.mytarget.model.Answer;
import com.develop.loginov.mytarget.model.Target;

import java.util.List;

@Dao
public abstract class TargetDAO {

    @Query("SELECT * FROM targets WHERE name= :targetName LIMIT 1")
    public abstract Target getTargetByName(final String targetName);

    @Query("SELECT * FROM targets WHERE id= :id LIMIT 1")
    public abstract Target getTargetById(final int id);

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
