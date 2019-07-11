package com.develop.loginov.mytarget.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.fragment.FeedBackFragment;
import com.develop.loginov.mytarget.controller.fragment.ResultFragment;
import com.develop.loginov.mytarget.controller.fragment.TargetListFragment;
import com.develop.loginov.mytarget.dialog.NavigationMenuDialog;
import com.google.android.material.bottomappbar.BottomAppBar;

public class ResultActivity extends AppCompatActivity implements NavigationMenuDialog.OnNavigationItemClickListener {
    public static final String[] BOLD_ARGS = {"bold1", "bold2", "bold3", "bold4"};
    public static final String PROBABILITY_ARG = "probability";

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
        final String[][] matrix = new String[4][];
        final boolean[][] boldObjects = new boolean[4][];
        assert extras != null;
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = extras.getStringArray(TestActivity.QUESTIONS_ARGS[i]);
            boldObjects[i] = extras.getBooleanArray(BOLD_ARGS[i]);
            if (fragments[i] != null) {
                fragments[i].setResults(matrix[i], boldObjects[i]);
            }
        }
        final String target = extras.getString(TargetActivity.TARGET_ARG);

        final TextView textResult = findViewById(R.id.activity_result__target_done);
        final TextView textTarget = findViewById(R.id.activity_result__target);
        final BottomAppBar bottomAppBar = findViewById(R.id.activity_result__bottom_app_bar);

        if (getSupportActionBar() == null) {
            setSupportActionBar(bottomAppBar);
        }

        int probability = extras.getInt(PROBABILITY_ARG);
        textResult.setText(getResources().getString(R.string.target_done,
                                                    Integer.toString(probability)));
        textResult.setTextColor(getResources().getColor(R.color.blue));
        textTarget.setText(target);

        findViewById(R.id.activity_result__button_reset).setOnClickListener(v -> {
            final Intent intent = getIntent();
            intent.setClass(ResultActivity.this, TestActivity.class);
            finish();
            startActivity(intent);
        });

        findViewById(R.id.activity_result__button_why).setOnClickListener(v -> {
            Toast.makeText(ResultActivity.this, "Потому что !", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.activity_result__fab_targets).setOnClickListener(v ->{
            DialogFragment dialogFragment = TargetListFragment.newInstance("l0gark");
            dialogFragment.show(getSupportFragmentManager(), "TARGET_LIST_TAG");
        });
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
      //TODO menu
    }
}
