package com.develop.loginov.mytarget.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.model.Competition;
import com.develop.loginov.mytarget.model.TestIterator;

import java.util.Iterator;

public class TestActivity extends AppCompatActivity {
    public static final String[] QUESTIONS_ARGS = {"question1",
                                                   "question2",
                                                   "question3",
                                                   "question4"};

    private TextView textAnswer1;
    private TextView textAnswer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }
        textAnswer1 = findViewById(R.id.activity_test__answer1);
        textAnswer2 = findViewById(R.id.activity_test__answer2);


        final String[][] matrix = new String[4][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = bundle.getStringArray(QUESTIONS_ARGS[i]);
        }

        final TestIterator iterator = new TestIterator(matrix);

        final Competition competition = new Competition(matrix.length,
                                                        iterator.next(),
                                                        iterator.next());
        textAnswer1.setText(competition.getAnswer1());
        textAnswer2.setText(competition.getAnswer2());

        findViewById(R.id.activity_test__card1).setOnClickListener(v -> {
            int memberIndex = iterator.getCurrentIndex();
            competition.winFirst(iterator.next(), memberIndex);
            textAnswer2.setText(competition.getAnswer2());
            if (!iterator.hasNext()) {
                Toast.makeText(TestActivity.this, "Выйграл " + competition.getWinnerIndex(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        findViewById(R.id.activity_test__card2).setOnClickListener(v -> {
            int memberIndex = iterator.getCurrentIndex();
            competition.winSecond(iterator.next(), memberIndex);
            textAnswer1.setText(competition.getAnswer1());
            if (!iterator.hasNext()) {
                Toast.makeText(TestActivity.this, "Выйграл " + competition.getWinnerIndex(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
