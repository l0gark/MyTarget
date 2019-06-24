package com.develop.loginov.mytarget.controller.activity;

import android.content.Intent;
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
import com.develop.loginov.mytarget.controller.fragment.QuestionFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.develop.loginov.mytarget.helper.KeyBoardHelper.hideKeyBoard;

public class TargetActivity extends AppCompatActivity {
    public static final String TARGET_ARG = "TARGET";

    private EditText editName;
    private TextView textAttention;
    private TextView textName;
    private Button buttonNext;
    private ViewSwitcher viewSwitcher;

    private QuestionFragment[] fragments;
    private int activeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        textAttention = findViewById(R.id.activity_target__attention);
        buttonNext = findViewById(R.id.activity_target__button_next);
        final FloatingActionButton fabOk = findViewById(R.id.activity_target__fab_ok);
        viewSwitcher = findViewById(R.id.activity_target__view_switcher);
        textName = findViewById(R.id.activity_target__target_name_text);
        editName = findViewById(R.id.activity_target__target_name_edit_text);

        fragments = new QuestionFragment[4];
        fragments[0] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_target__question1);
        fragments[1] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_target__question2);
        fragments[2] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_target__question3);
        fragments[3] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_target__question4);

        activeIndex = -1;

        String[] answers = getResources().getStringArray(R.array.answers_sample);
        for (int i = 0; i < fragments.length; i++) {
            fragments[i].setAnswers(answers);
            final int x = i;
            fragments[i].setOnClickListener(v -> {
                if (x == activeIndex) {
                    return;
                }

                for (final QuestionFragment fragment : fragments) {
                    fragment.hide();
                }
                activeIndex = x;
                fragments[activeIndex].active();

                textAttention.setVisibility(View.GONE);
                buttonNext.setVisibility(View.GONE);
            });
        }

        buttonNext.setOnClickListener(v -> {
            //TODO Save target
            final Intent intent = new Intent(this, TestActivity.class);
            for (int i = 0; i < fragments.length; i++) {
                intent.putExtra(TestActivity.QUESTIONS_ARGS[i], fragments[i].getAnswers());
            }
            intent.putExtra(TARGET_ARG, textName.getText().toString());
            startActivity(intent);
            finish();
        });

        fabOk.setOnClickListener(v -> {
            if (TextUtils.isEmpty(editName.getText())) {
                return;
            }
            textName.setText(editName.getText().toString());
            viewSwitcher.showNext();
            hideKeyBoard(this);
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
        for (final QuestionFragment fragment : fragments) {
            fragment.disActive();
        }

        textAttention.setVisibility(View.VISIBLE);
        buttonNext.setVisibility(View.VISIBLE);
    }

    private void setEnabled(boolean enabled) {
        textAttention.setEnabled(enabled);
        buttonNext.setEnabled(enabled);
        for (final QuestionFragment fragment : fragments) {
            fragment.setEnabled(enabled);
        }
    }
}
