package com.develop.loginov.mytarget.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.application.App;
import com.develop.loginov.mytarget.database.AnswerDAO;
import com.develop.loginov.mytarget.database.TargetDAO;
import com.develop.loginov.mytarget.model.Answer;
import com.develop.loginov.mytarget.model.Competition;
import com.develop.loginov.mytarget.model.Target;
import com.develop.loginov.mytarget.model.TestIterator;

import java.util.List;
import java.util.Set;

public class TestActivity extends AppCompatActivity {
    public static final String[] QUESTIONS_ARGS = {"question1",
                                                   "question2",
                                                   "question3",
                                                   "question4"};

    private TextView textAnswer1;
    private TextView textAnswer2;
    private String[][] matrix;
    private Competition competition;
    private String targetName;
    private long targetId;
    private long targetTime;
    private AlertDialog cancelDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Bundle extras = getIntent().getExtras();
        if (extras == null) {
            finish();
            return;
        }
        textAnswer1 = findViewById(R.id.activity_test__answer1);
        textAnswer2 = findViewById(R.id.activity_test__answer2);
        final CardView cardView1 = findViewById(R.id.activity_test__card1);
        final CardView cardView2 = findViewById(R.id.activity_test__card2);
        final ViewGroup transitionContainer = findViewById(R.id.activity_test__transition_container);
        final TextView textTarget = findViewById(R.id.activity_test__target);


        matrix = new String[4][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = extras.getStringArray(QUESTIONS_ARGS[i]);
        }
        targetName = extras.getString(TargetActivity.TARGET_ARG);
        targetId = extras.getLong(TargetActivity.TARGET_ID_ARG);
        targetTime = extras.getLong(TargetActivity.TARGET_TIME_ARG);

        final TestIterator iterator = new TestIterator(matrix);
        competition = new Competition(matrix.length, iterator.next(), iterator.next());

        textAnswer1.setText(competition.getAnswer1());
        textAnswer2.setText(competition.getAnswer2());
        textTarget.setText(targetName);

        cardView1.setOnClickListener(v -> clickAnswer(iterator, transitionContainer, true));
        cardView2.setOnClickListener(v -> clickAnswer(iterator, transitionContainer, false));

        cancelDialog = createDialog();
    }

    private void clickAnswer(@NonNull final TestIterator iterator,
                             @NonNull final ViewGroup transitionContainer, final boolean first) {
        int memberIndex = first ? iterator.getCurrentIndex(competition.getAnswer1()) : iterator.getCurrentIndex(
                competition.getAnswer2());

        if (iterator.hasNext()) {
//            TransitionManager.beginDelayedTransition(transitionContainer,
//                                                     new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_OUT_IN).setDuration(
//                                                             500).setInterpolator(new DecelerateInterpolator()));
            if (first) {
                competition.winFirst(iterator.next(), memberIndex);
                textAnswer2.setText(competition.getAnswer2());
                Log.d("TEST_TAG", "win " + competition.getAnswer1() + " index = " + memberIndex);
            } else {
                competition.winSecond(iterator.next(), memberIndex);
                textAnswer1.setText(competition.getAnswer1());
                Log.d("TEST_TAG", "win " + competition.getAnswer2() + " index = " + memberIndex);
            }

        } else {
            if (first) {
                competition.winFirst("END", memberIndex);
            } else {
                competition.winSecond("END", memberIndex);
            }
            toResults(matrix, competition.getWinners(), competition.getWinnerIndex());
        }
    }

    private void toResults(final String[][] matrix, final Set<String> winners,
                           final int winnerIndex) {
        final boolean[][] winnerMatrix = new boolean[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            winnerMatrix[i] = new boolean[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++) {
                winnerMatrix[i][j] = winners.contains(matrix[i][j]);
            }
        }

        Log.d("TEST_TAG", "winner - " + winnerIndex);
        final int winnerArg;
        switch (winnerIndex) {
            case 0:
                winnerArg = 2;
                break;
            case 1:
                winnerArg = 4;
                break;
            case 2:
                winnerArg = 1;
                break;
            case 3:
                winnerArg = 3;
                break;
            default:
                winnerArg = 2;
        }

        //update Target Data
        new Thread(() -> {
            final TargetDAO targetDAO = App.getInstance().getDataBase().targetDAO();
            final AnswerDAO answerDao = App.getInstance().getDataBase().answerDAO();
            final Target target = targetDAO.getTargetByName(targetName, targetTime);
            if (target == null) {
                Looper.prepare();
                Toast.makeText(TestActivity.this,
                               "Что-то пошло не так!",
                               Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }
            final List<Answer> answers = answerDao.getAnswersById(target.getId());
            for (final Answer answer : answers) {
                answer.setSelected(winners.contains(answer.getAnswer()));
                answerDao.update(answer);
            }
            target.setWinner(winnerArg);
            targetDAO.update(target);
        }).start();

        // Send data by intent
        final Intent intent = getIntent();
        intent.setClass(this, ResultActivity.class);
        for (int i = 0; i < matrix.length; i++) {
            intent.putExtra(ResultActivity.BOLD_ARGS[i], winnerMatrix[i]);
        }

        intent.putExtra(ResultActivity.WINNER_ARG, winnerArg);
        intent.putExtra(ResultActivity.AFTER_TEST_ARG, true);
        finish();
        startActivity(intent);
    }

    private AlertDialog createDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.test_exit_title).setMessage(R.string.test_exit_message).setPositiveButton(
                R.string.ok,
                (dialog, which) -> finish()).setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }

    @Override
    public void onBackPressed() {
        cancelDialog.show();
    }
}
