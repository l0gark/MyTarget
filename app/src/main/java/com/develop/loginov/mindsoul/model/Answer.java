package com.develop.loginov.mindsoul.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.develop.loginov.mindsoul.database.AnswerDAO;

@Entity(tableName = "answers")
public class Answer {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long ownerId;
    private String answer;
    private int index;
    private boolean selected;

    public Answer(@NonNull final String answer, final long ownerId, final int index) {
        if (TextUtils.isEmpty(answer) || index < 0 || index >= 20) {
            throw new IllegalArgumentException("Invalid data for Answer : " + " answer = " + answer + " ownerId = " + ownerId + " index = " + index);
        }
        this.ownerId = ownerId;
        this.answer = answer;
        this.index = index;
        selected = false;
    }

    public static void save(@NonNull final Answer answer, @NonNull final AnswerDAO dao) {
        dao.insertOrUpdate(answer);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
