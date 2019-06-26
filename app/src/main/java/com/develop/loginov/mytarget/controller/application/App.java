package com.develop.loginov.mytarget.controller.application;

import android.app.Application;

import androidx.room.Room;

import com.develop.loginov.mytarget.database.AppDataBase;

public class App extends Application {
    private static App instance;
    private AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = Room.databaseBuilder(this, AppDataBase.class, "database").build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDataBase() {
        return dataBase;
    }
}
