package com.develop.loginov.mytarget.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.application.App;
import com.develop.loginov.mytarget.controller.fragment.FeedBackFragment;
import com.develop.loginov.mytarget.controller.fragment.QuestionFragment;
import com.develop.loginov.mytarget.controller.fragment.TargetListFragment;
import com.develop.loginov.mytarget.database.AnswerDAO;
import com.develop.loginov.mytarget.database.TargetDAO;
import com.develop.loginov.mytarget.dialog.NavigationMenuDialog;
import com.develop.loginov.mytarget.dialog.QuestionDialogFragment;
import com.develop.loginov.mytarget.helper.FragmentHelper;
import com.develop.loginov.mytarget.model.Answer;
import com.develop.loginov.mytarget.model.Target;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Iterator;
import java.util.List;

public class TargetActivity extends AppCompatActivity implements NavigationMenuDialog.OnNavigationItemClickListener {
    public static final String TARGET_ARG = "TARGET";
    public static final String TARGET_ID_ARG = "TARGET_ID";
    private boolean oldTarget = false;
    private long id = -1;

    private EditText editName;
    private TextView textAttention;
    private Button buttonNext;

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
        editName = findViewById(R.id.activity_target__target_name_edit_text);
        FloatingActionButton fabTargets = findViewById(R.id.activity_target__fab_targets);
        BottomAppBar bottomAppBar = findViewById(R.id.activity_target__bottom_app_bar);

        if (getSupportActionBar() == null) {
            setSupportActionBar(bottomAppBar);
        }

        fragments = new QuestionFragment[4];
        fragments[0] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_target__question1);
        fragments[1] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_target__question2);
        fragments[2] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_target__question3);
        fragments[3] = (QuestionFragment) getSupportFragmentManager().findFragmentById(R.id.activity_target__question4);
        dialogFragment = new QuestionDialogFragment();


        answers = new String[4][5];
        setEnabled(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String target = extras.getString(TARGET_ARG);
            final long id = extras.getLong(TARGET_ID_ARG);

            new Thread(() -> {
                final AnswerDAO answerDAO = App.getInstance().getDataBase().answerDAO();
                final List<Answer> answersDB = answerDAO.getAnswersById(id);
                if (answersDB == null || answersDB.size() != 20) {
                    return;
                }
                final Iterator<Answer> iterator = answersDB.iterator();
                for (int i = 0; i < answers.length; i++) {
                    for (int j = 0; j < answers[i].length && iterator.hasNext(); j++) {
                        answers[i][j] = iterator.next().getAnswer();
                    }
                }
                oldTarget = true;
                this.id = id;
            }).start();
            editName.setText(target);
            setEnabled(target != null && target.length() != 0);
        }

        for (int i = 0; i < fragments.length; i++) {
            final int x = i;
            fragments[i].setOnClickListener(v -> {
                dialogFragment.setAnswers(answers[x]);
                dialogFragment.setOnDoneClickListener(() -> {
                    boolean done = true;
                    for (int j = 0; j < answers[x].length; j++) {
                        done &= !TextUtils.isEmpty(answers[x][j]);
                    }
                    fragments[x].setDone(done);
                });

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
                System.arraycopy(answers[i],
                                 0,
                                 answersTotalData,
                                 i * answers[0].length,
                                 answers[i].length);
            }

            for (final String answersTotalDatum : answersTotalData) {
                if (answersTotalDatum == null) {
                    Toast.makeText(TargetActivity.this,
                                   "Введите все ответы ",
                                   Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            final String targetName = editName.getText().toString();
            saveTarget(targetName, answersTotalData);

            intent.putExtra(TARGET_ARG, targetName);
            startActivity(intent);
            finish();
        });

        editName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                editName.setHint("");
            } else {
                editName.setHint(R.string.my_target);
            }
        });

        fabTargets.setOnClickListener(v -> {
            DialogFragment dialogFragment = TargetListFragment.newInstance("l0gark");
            dialogFragment.show(getSupportFragmentManager(), "TARGET_LIST_TAG");
        });

        editName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) {
                    setEnabled(false);
                } else {
                    setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                final DialogFragment dialogFragment = FeedBackFragment.newInstance();
                dialogFragment.show(getSupportFragmentManager(), "FEED_BACK_TAG");
                break;
        }
        return true;
    }

    @Override
    public void clickItem(int itemId) {
        //TODO menu
    }

    private void saveTarget(@NonNull final String targetName, @NonNull final String[] strAnswers) {
        new Thread(() -> {
            final TargetDAO targetDAO = App.getInstance().getDataBase().targetDAO();
            final AnswerDAO answerDAO = App.getInstance().getDataBase().answerDAO();

            final Target target = Target.of(targetName, System.currentTimeMillis());
            //create DAO

            if (oldTarget) {
                target.setId(id);
            }

            long ownerId = Target.save(target, targetDAO);
            final Answer[] answers = new Answer[strAnswers.length];
            for (int i = 0; i < strAnswers.length; i++) {
                answers[i] = new Answer(strAnswers[i], ownerId, i);
            }
            answerDAO.insertAll(answers);
        }).start();
    }

    @Override
    public void onBackPressed() {
        for (int i = 0; i < answers.length; i++) {
            boolean done = true;
            for (int j = 0; j < answers[i].length; j++) {
                done &= !TextUtils.isEmpty(answers[i][j]);
            }
            fragments[i].setDone(done);
        }
        super.onBackPressed();
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
