package com.develop.loginov.mytarget.controller.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.develop.loginov.mytarget.R;
import com.google.android.material.bottomappbar.BottomAppBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomAppBar bottomAppBar = findViewById(R.id.activity_main__bottom_app_bar);
        setSupportActionBar(bottomAppBar);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        String s = "";
        switch (item.getItemId()) {
            case R.id.app_bar_fav:
                s = "fav";
                break;
            case R.id.app_bar_profile:
                s = "profile";
                break;
        }
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        return true;
    }
}
