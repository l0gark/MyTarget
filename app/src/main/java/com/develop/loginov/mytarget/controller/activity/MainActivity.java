package com.develop.loginov.mytarget.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.fragment.QuestionFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private TextView textAttention;
    private TextView textName;
    private Button buttonNext;
    private FloatingActionButton fabOk;
    private ViewSwitcher viewSwitcher;

    private QuestionFragment[] fragments;
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

        fragments = new QuestionFragment[4];
        fragments[0] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_name__question1);
        fragments[1] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_name__question2);
        fragments[2] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_name__question3);
        fragments[3] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_name__question4);

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
            fragment.disactive();
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

    public static void hideKeyBoard(final Activity activity) {
        if (activity == null)
            return;

        hideKeyBoard(activity, activity.getWindow().getDecorView().getWindowToken());
    }

    public static void hideKeyBoard(Context context, IBinder binder) {
        if (context == null)
            return;
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binder, 0);
    }


}
