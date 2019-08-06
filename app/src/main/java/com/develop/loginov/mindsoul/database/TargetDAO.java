package com.develop.loginov.mindsoul.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.develop.loginov.mindsoul.model.Target;

import java.util.List;

@Dao
public abstract class TargetDAO {

    @Query("SELECT * FROM targets WHERE name= :targetName LIMIT 1")
    public abstract Target getTargetByName(final String targetName);

    @Query("SELECT * FROM targets WHERE id= :id LIMIT 1")
    public abstract Target getTargetById(final long id);

    @Query("SELECT * FROM targets WHERE name= :targetName AND time= :time LIMIT 1")
    public abstract Target getTargetByName(final String targetName, final long time);

    @Query("SELECT * FROM targets")
    public abstract List<Target> getTargets();

    @Insert
    public abstract long insert(final Target target);

    @Update
    public abstract int update(final Target target);


    @Transaction
    public long insertOrUpdate(final Target target) {
        if (update(target) == 0) {
            return insert(target);
        }
        return -1;
    }

    @Transaction
    public void insertOrUpdateList(final List<Target> targets) {
        for (final Target target : targets) {
            insertOrUpdate(target);
        }
    }

}
