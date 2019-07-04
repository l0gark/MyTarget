package com.develop.loginov.mytarget.database;

import android.text.TextUtils;

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
    public abstract List<Answer> getAnswersById(final long targetId);

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

    @Transaction
    public void insertAll(final Answer[] answers) {
        if (answers.length != 20) {
            throw new IllegalArgumentException("Array length must be 20");
        }
        for (final Answer answer: answers) {
            insertOrUpdate(answer);
        }
    }

}
