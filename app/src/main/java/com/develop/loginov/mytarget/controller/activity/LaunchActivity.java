package com.develop.loginov.mytarget.controller.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.develop.loginov.mytarget.model.Target;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = new Intent(this, TargetActivity.class);
        startActivity(intent);
        finish();
    }
}
