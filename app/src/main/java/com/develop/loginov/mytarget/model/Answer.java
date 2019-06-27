package com.develop.loginov.mytarget.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "answers")
public class Answer {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int ownerId;
    private String answer;
    private int index;

    public Answer(int id, int ownerId, String answer, int index) {
        this.id = id;
        this.ownerId = ownerId;
        this.answer = answer;
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
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
