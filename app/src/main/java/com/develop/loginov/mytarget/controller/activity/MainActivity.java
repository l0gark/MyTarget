package com.develop.loginov.mytarget.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.fragment.SimpleQuestionFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textAttention;
    private Button buttonNext;

    private SimpleQuestionFragment[] fragments;
    private int activeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAttention = findViewById(R.id.activity_main__attention);
        buttonNext = findViewById(R.id.activity_main__button_next);

        fragments = new SimpleQuestionFragment[4];
        fragments[0] = (SimpleQuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_name__question1);
        fragments[1] = (SimpleQuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_name__question2);
        fragments[2] = (SimpleQuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_name__question3);
        fragments[3] = (SimpleQuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_name__question4);

        activeIndex = -1;

        String[] array = getResources().getStringArray(R.array.answers_sample);
        List<String> answers = Arrays.asList(array);
        for (int i = 0; i < fragments.length; i++) {
            fragments[i].setAnswers(answers);
            final int x = i;
            fragments[i].setOnClickListener(v -> {
                if(x == activeIndex){
                    return;
                }

                for (final SimpleQuestionFragment fragment : fragments) {
                    fragment.hide();
                }
                activeIndex = x;
                fragments[activeIndex].active();

                textAttention.setVisibility(View.GONE);
                buttonNext.setVisibility(View.GONE);
            });
        }
    }


    @Override
    public void onBackPressed() {
        if(activeIndex < 0){
            super.onBackPressed();
            return;
        }
        activeIndex = -1;
        for (final SimpleQuestionFragment fragment : fragments) {
            fragment.show();
        }

        textAttention.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.VISIBLE);
    }
}
