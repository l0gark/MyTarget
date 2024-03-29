package com.develop.ain.mindsoul.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.develop.ain.mindsoul.R;
import com.develop.ain.mindsoul.controller.application.App;
import com.develop.ain.mindsoul.controller.fragment.FeedBackFragment;
import com.develop.ain.mindsoul.controller.fragment.ResultFragment;
import com.develop.ain.mindsoul.controller.fragment.TargetListFragment;
import com.develop.ain.mindsoul.database.AnswerDAO;
import com.develop.ain.mindsoul.dialog.HelpAnswerDialog;
import com.develop.ain.mindsoul.dialog.NavigationMenuDialog;
import com.develop.ain.mindsoul.model.Answer;
import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.Iterator;
import java.util.List;

import static com.develop.ain.mindsoul.controller.activity.TargetActivity.TARGET_ARG;
import static com.develop.ain.mindsoul.controller.activity.TargetActivity.TARGET_ID_ARG;

public class ResultActivity extends AppCompatActivity implements NavigationMenuDialog.OnNavigationItemClickListener {
    public static final String[] BOLD_ARGS = {"bold1", "bold2", "bold3", "bold4"};
    public static final String WINNER_ARG = "probability";
    public static final String AFTER_TEST_ARG = "after_test";

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
        final String[][] matrix = new String[4][5];
//        final boolean[][] boldObjects = new boolean[4][5];
        final int[][] countWinObjects = new int[4][5];
        assert extras != null;
        boolean isAfterTest = extras.getBoolean(AFTER_TEST_ARG, false);

        String target = extras.getString(TARGET_ARG, "");
        final long id = extras.getLong(TARGET_ID_ARG, -1);

        if (isAfterTest) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i] = extras.getStringArray(TestActivity.QUESTIONS_ARGS[i]);
//                boldObjects[i] = extras.getBooleanArray(BOLD_ARGS[i]);
                countWinObjects[i] = extras.getIntArray(BOLD_ARGS[i]);
                if (fragments[i] != null) {
//                    fragments[i].setResults(matrix[i], boldObjects[i]);
                    fragments[i].setResults(matrix[i], countWinObjects[i]);
                }
            }
        } else {
            new Thread(() -> {
                final AnswerDAO answerDAO = App.getInstance().getDataBase().answerDAO();
                final List<Answer> answersDB = answerDAO.getAnswersById(id);
                if (answersDB == null || answersDB.size() != 20) {
                    return;
                }

                final Iterator<Answer> iterator = answersDB.iterator();
                for (int i = 0; i < matrix.length; i++) {
                    for (int j = 0; j < matrix[i].length; j++) {
                        final Answer answer = iterator.next();
                        matrix[i][j] = answer.getAnswer();
//                        boldObjects[i][j] = answer.isSelected();
                        countWinObjects[i][j] = answer.getWinCount();
                        if (fragments[i] != null) {
//                            fragments[i].setResults(matrix[i], boldObjects[i]);
                            fragments[i].setResults(matrix[i], countWinObjects[i]);
                        }
                    }
                }
            }).start();
        }

        final TextView textResult = findViewById(R.id.activity_result__target_done);
        final TextView textTarget = findViewById(R.id.activity_result__target);
        final BottomAppBar bottomAppBar = findViewById(R.id.activity_result__bottom_app_bar);

        if (getSupportActionBar() == null) {
            setSupportActionBar(bottomAppBar);
        }

        int winner = extras.getInt(WINNER_ARG);
        final String result;
        final int resourceWhy;
        switch (winner) {
            case 1:
                result = getResources().getString(R.string.result01);
                resourceWhy = R.string.why1;
                textResult.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            case 2:
                result = getResources().getString(R.string.result02);
                resourceWhy = R.string.why2;
                textResult.setTextColor(getResources().getColor(R.color.blue));
                break;
            case 3:
                result = getResources().getString(R.string.result03);
                resourceWhy = R.string.why3;
                textResult.setTextColor(getResources().getColor(R.color.dark_gray));
                break;
            default:
                result = getResources().getString(R.string.result04);
                resourceWhy = R.string.why4;
                textResult.setTextColor(getResources().getColor(R.color.green));
        }


        target = refactorTarget(target);
        textResult.setText(result);
        textTarget.setText(target);

        findViewById(R.id.activity_result__button_reset).setOnClickListener(v -> {
            final Intent intent = new Intent(ResultActivity.this, TargetActivity.class);
            finish();
            startActivity(intent);
        });

        findViewById(R.id.activity_result__button_why).setOnClickListener(v -> {
            final HelpAnswerDialog dialogFragment = HelpAnswerDialog.newInstance(resourceWhy);
            dialogFragment.setClearText(true);
            dialogFragment.show(getSupportFragmentManager(), "TARGET_LIST_TAG");
        });

        findViewById(R.id.activity_result__fab_targets).setOnClickListener(v -> {
            DialogFragment dialogFragment = TargetListFragment.newInstance("l0gark");
            dialogFragment.show(getSupportFragmentManager(), "TARGET_LIST_TAG");
        });
    }

    private String refactorTarget(final String target) {
        int count = 0;
        for (int i = 0; i < target.length(); i++) {
            if (target.charAt(i) == '\n') {
                count++;
            }
        }
        String answer = target;
        while (count > 4) {
            answer = target.replace("\n", " ");
            count--;
        }
        return answer;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        final Intent intent = new Intent(this, TargetActivity.class);
        startActivity(intent);
        finish();
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
        //TODO navigation menu
    }
}
