package com.develop.ain.mindsoul.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.develop.ain.mindsoul.model.Answer;
import com.develop.ain.mindsoul.model.Target;

@Database(entities = {Target.class, Answer.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TargetDAO targetDAO();
    public abstract AnswerDAO answerDAO();
}
