package com.develop.loginov.mytarget.controller.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.fragment.SimpleQuestionFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private TextView textAttention;
    private TextView textName;
    private Button buttonNext;
    private FloatingActionButton fabOk;
    private ViewSwitcher viewSwitcher;

    private SimpleQuestionFragment[] fragments;
    private int activeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textAttention = findViewById(R.id.activity_main__attention);
        buttonNext = findViewById(R.id.activity_main__button_next);
        fabOk = findViewById(R.id.activity_main__fab_ok);
        viewSwitcher = findViewById(R.id.activity_main__view_switcher);
        textName = findViewById(R.id.activity_main__target_name_text);
        editName = findViewById(R.id.activity_main__target_name_edit_text);

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
                if (x == activeIndex) {
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

        fabOk.setOnClickListener(v -> {
            if (TextUtils.isEmpty(editName.getText())) {
                return;
            }
            textName.setText(editName.getText().toString());
            viewSwitcher.showNext();
            setEnabled(true);
        });
        textName.setOnClickListener(v -> {
            setEnabled(false);
            viewSwitcher.showNext();
        });

        final Animation slideInLeft = AnimationUtils.loadAnimation(this,
                                                                   android.R.anim.slide_in_left);
        final Animation slideOutRight = AnimationUtils.loadAnimation(this,
                                                                     android.R.anim.slide_out_right);
        viewSwitcher.setInAnimation(slideInLeft);
        viewSwitcher.setOutAnimation(slideOutRight);

        setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (activeIndex < 0) {
            super.onBackPressed();
            return;
        }
        activeIndex = -1;
        for (final SimpleQuestionFragment fragment : fragments) {
            fragment.disactive();
        }

        textAttention.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.VISIBLE);
    }

    private void setEnabled(boolean enabled) {
        textAttention.setEnabled(enabled);
        buttonNext.setEnabled(enabled);
        for (final SimpleQuestionFragment fragment : fragments) {
            fragment.setEnabled(enabled);
        }
    }

}
