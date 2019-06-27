package com.develop.loginov.mytarget.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.develop.loginov.mytarget.model.Answer;

import java.util.List;

@Dao
public abstract class AnswerDAO {
    @Query("SELECT * FROM answers WHERE ownerId= :targetId LIMIT 20")
    public abstract List<Answer> getAnswersById(final int targetId);

    @Insert
    public abstract void insert(final Answer answer);

    @Update
    public abstract int update(final Answer answer);

    @Transaction
    public void insertOrUpdate(final Answer answer) {
        if (update(answer) == 0) {
            insert(answer);
        }
    }
}
