package com.develop.ain.mindsoul.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.develop.ain.mindsoul.database.AnswerDAO;

@Entity(tableName = "answers")
public class Answer {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long ownerId;
    private String answer;
    private int index;
    private boolean selected;
    private int winCount;

    public Answer(@NonNull final String answer, final long ownerId, final int index) {
        if (TextUtils.isEmpty(answer) || index < 0 || index >= 20) {
            throw new IllegalArgumentException("Invalid data for Answer : " + " answer = " + answer + " ownerId = " + ownerId + " index = " + index);
        }
        this.ownerId = ownerId;
        this.answer = answer;
        this.index = index;
        selected = false;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(@Nullable Integer winCount) {
        if (winCount == null) {
            winCount = 0;
        }
        this.winCount = winCount;
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
