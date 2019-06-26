package com.develop.loginov.mytarget.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class Target {
    @PrimaryKey
    private String name;

    private String[][] answers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[][] getAnswers() {
        return answers;
    }

    public void setAnswers(String[][] answers) {
        this.answers = answers;
    }
}
