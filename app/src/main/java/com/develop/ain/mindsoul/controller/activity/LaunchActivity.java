package com.develop.ain.mindsoul.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.develop.ain.mindsoul.R;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        findViewById(R.id.activity_launch__next).setOnClickListener(v -> {
            final Intent intent = new Intent(LaunchActivity.this, TargetActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
