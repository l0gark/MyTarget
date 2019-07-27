package com.develop.loginov.mindsoul.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String FIRST_ENTER_KEY = "FIRST_ENTER_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES,
                                                                   Context.MODE_PRIVATE);

        final boolean hasKey = sharedPreferences.getBoolean(FIRST_ENTER_KEY, false);
        if (hasKey) {
            final Intent intent = new Intent(this, TargetActivity.class);
            startActivity(intent);
        } else {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FIRST_ENTER_KEY, true);
            editor.apply();

            final Intent intent = new Intent(this, LaunchActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
