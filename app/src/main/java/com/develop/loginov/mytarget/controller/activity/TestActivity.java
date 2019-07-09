package com.develop.loginov.mytarget.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.transition.TransitionManager;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.application.App;
import com.develop.loginov.mytarget.database.TargetDAO;
import com.develop.loginov.mytarget.model.Competition;
import com.develop.loginov.mytarget.model.Target;
import com.develop.loginov.mytarget.model.TestIterator;
import com.transitionseverywhere.ChangeText;

import java.util.Arrays;
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

        final TestIterator iterator = new TestIterator(matrix);
        competition = new Competition(matrix.length, iterator.next(), iterator.next());

        textAnswer1.setText(competition.getAnswer1());
        textAnswer2.setText(competition.getAnswer2());
        textTarget.setText(targetName);

        cardView1.setOnClickListener(v -> clickAnswer(iterator,
                                                      transitionContainer,
                                                      true));
        cardView2.setOnClickListener(v -> clickAnswer(iterator,
                                                      transitionContainer,
                                                      false));
    }

    private void clickAnswer(@NonNull final TestIterator iterator,
                             @NonNull final ViewGroup transitionContainer, final boolean first) {
        int memberIndex = first ? iterator.getCurrentIndex(competition.getAnswer1()) : iterator.getCurrentIndex(competition.getAnswer2()) ;

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
        final int probability;
        switch (winnerIndex) {
            case 0:
                probability = 100;
                break;
            case 1:
            case 2:
            case 3:
                probability = 0;
                break;
            default:
                probability = 0;
        }

        //update Target Data
        new Thread(() -> {
            final TargetDAO targetDAO = App.getInstance().getDataBase().targetDAO();
            final Target target = targetDAO.getTargetByName(targetName);
            target.setProbability(probability);
            targetDAO.update(target);
        }).start();

        // Send data by intent
        final Intent intent = new Intent(this, ResultActivity.class);
        for (int i = 0; i < matrix.length; i++) {
            intent.putExtra(QUESTIONS_ARGS[i], matrix[i]);
            intent.putExtra(ResultActivity.BOLD_ARGS[i], winnerMatrix[i]);
        }

        intent.putExtra(ResultActivity.PROBABILITY_ARG, probability);
        intent.putExtra(TargetActivity.TARGET_ARG, targetName);
        finish();
        startActivity(intent);
    }
}
