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
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.application.App;
import com.develop.loginov.mytarget.controller.fragment.QuestionFragment;
import com.develop.loginov.mytarget.database.AnswerDAO;
import com.develop.loginov.mytarget.database.TargetDAO;
import com.develop.loginov.mytarget.dialog.QuestionDialogFragment;
import com.develop.loginov.mytarget.model.Answer;
import com.develop.loginov.mytarget.model.Target;
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
    private QuestionDialogFragment dialogFragment;
    private String[][] answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
        dialogFragment = new QuestionDialogFragment();


        answers = new String[4][5];


        for (int i = 0; i < fragments.length; i++) {
            final int x = i;
            fragments[i].setOnClickListener(v -> {
                dialogFragment.setAnswers(answers[x]);
                dialogFragment.show(getSupportFragmentManager(), "Dialog");
            });
        }

        buttonNext.setOnClickListener(v -> {
            final String[] answersTotalData = new String[20];
            final Intent intent = new Intent(this, TestActivity.class);
            for (int i = 0; i < fragments.length; i++) {
                //put data in Intent
                intent.putExtra(TestActivity.QUESTIONS_ARGS[i], answers[i]);

                //save data to total array
                System.arraycopy(answers[i], 0, answersTotalData, i * answers[0].length, answers[i].length);
            }

            for (int i = 0; i < answersTotalData.length; i++){
                if(answersTotalData[i] == null){
                    Toast.makeText(TargetActivity.this, "Введите все ответы " + (++i), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            final String targetName = textName.getText().toString();
            saveTarget(targetName, answersTotalData);

            intent.putExtra(TARGET_ARG, targetName);
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

    private void saveTarget(@NonNull final String targetName, @NonNull final String[] strAnswers) {
        new Thread(() -> {
            final TargetDAO targetDAO = App.getInstance().getDataBase().targetDAO();
            final AnswerDAO answerDAO = App.getInstance().getDataBase().answerDAO();

            final Target target = Target.of(targetName, System.currentTimeMillis());
            //create DAO
            long ownerId = Target.save(target, targetDAO);
            final Answer[] answers = new Answer[strAnswers.length];
            for (int i = 0; i < strAnswers.length; i++) {
                answers[i] = new Answer(strAnswers[i], ownerId, i);
            }
            answerDAO.insertAll(answers);
        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setEnabled(boolean enabled) {
        textAttention.setEnabled(enabled);
        buttonNext.setEnabled(enabled);
        for (final QuestionFragment fragment : fragments) {
            fragment.setEnabled(enabled);
        }
    }
}
