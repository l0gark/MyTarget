package com.develop.loginov.mytarget.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.develop.loginov.mytarget.model.Target;

@Database(entities = Target.class, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TargetDAO targetDAO();
}
