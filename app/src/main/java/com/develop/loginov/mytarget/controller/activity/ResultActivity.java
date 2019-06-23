package com.develop.loginov.mytarget.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.fragment.ResultFragment;

public class ResultActivity extends AppCompatActivity {
    public static final String[] BOLD_ARGS = {"bold1", "bold2", "bold3", "bold4"};
    public static final String PROBABILITY_ARG = "probability";

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final ResultFragment[] fragments = new ResultFragment[4];
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
        final String target = extras.getString(MainActivity.TARGET_ARG);

        final TextView textResult = findViewById(R.id.activity_result__target_done);
        final TextView textTarget = findViewById(R.id.activity_result__target);

        int probability = extras.getInt(PROBABILITY_ARG);
        if (probability > 0) {
            textResult.setText(getResources().getString(R.string.target_done,
                                                        Integer.toString(probability)));
            textResult.setTextColor(getResources().getColor(R.color.turquoise));
        } else {
            textResult.setText(getResources().getString(R.string.target_not_done,
                                                        Integer.toString(-probability)));
            textResult.setTextColor(getResources().getColor(R.color.blue));
        }
        textTarget.setText(target);

        findViewById(R.id.activity_result__button_reset).setOnClickListener(v -> {
            final Intent intent = getIntent();
            intent.setClass(ResultActivity.this, TestActivity.class);
            finish();
            startActivity(intent);
        });

        findViewById(R.id.activity_result__button_why).setOnClickListener(v -> {
            Toast.makeText(ResultActivity.this, "Потому что !", Toast.LENGTH_SHORT).show();
        });
    }
}
