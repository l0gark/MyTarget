package com.develop.loginov.mytarget.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.develop.loginov.mytarget.model.Answer;
import com.develop.loginov.mytarget.model.Target;

@Database(entities = {Target.class, Answer.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TargetDAO targetDAO();
    public abstract AnswerDAO answerDAO();
}
