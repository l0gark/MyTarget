package com.develop.loginov.mytarget.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.fragment.ResultFragment;

public class ResultActivity extends AppCompatActivity {
    public static final String[] BOLD_ARGS = {"bold1", "bold2", "bold3", "bold4"};
    private ResultFragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        fragments[0] = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.activity_result__question1);
        fragments[1] = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.activity_result__question2);
        fragments[2] = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.activity_result__question3);
        fragments[3] = (ResultFragment) getSupportFragmentManager().findFragmentById(R.id.activity_result__question4);

        final Bundle extras = getIntent().getExtras();
        final String[][] matrix = new String[4][];
        final boolean[][] boldObjects = new boolean[4][];
        assert extras != null;
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = extras.getStringArray(TestActivity.QUESTIONS_ARGS[i]);
            boldObjects[i] = extras.getBooleanArray(BOLD_ARGS[i]);
            fragments[i].setResults(matrix[i], boldObjects[i]);
        }

        findViewById(R.id.activity_result__button_reset).setOnClickListener(v -> {
            final Intent intent = getIntent();
            intent.setClass(ResultActivity.this, TestActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.activity_result__button_why).setOnClickListener(v -> {
            Toast.makeText(ResultActivity.this, "Потому что !", Toast.LENGTH_SHORT).show();
        });
    }
}
