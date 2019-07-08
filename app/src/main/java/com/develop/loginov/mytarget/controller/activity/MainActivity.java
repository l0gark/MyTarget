package com.develop.loginov.mytarget.controller.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.fragment.FeedBackFragment;
import com.develop.loginov.mytarget.controller.fragment.TargetListFragment;
import com.develop.loginov.mytarget.dialog.NavigationMenuDialog;
import com.develop.loginov.mytarget.helper.FragmentHelper;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements NavigationMenuDialog.OnNavigationItemClickListener {
    private static final String LOGIN = "l0gark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomAppBar bottomAppBar = findViewById(R.id.activity_main__bottom_app_bar);
        final FloatingActionButton floatingActionButton = findViewById(R.id.activity_main__fab);
        floatingActionButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TargetActivity.class));
        });
        setSupportActionBar(bottomAppBar);

        FragmentHelper.changeFragment(TargetListFragment.newInstance(LOGIN),
                                      getSupportFragmentManager(),
                                      R.id.activity_main__container);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
//        getMenuInflater().inflate(R.menu.bottom_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_money:
                break;
            case R.id.app_bar_profile:
                break;
            case android.R.id.home:
                NavigationMenuDialog bottomSheetDialogFragment = new NavigationMenuDialog();
                bottomSheetDialogFragment.setOnNavigationItemClickListener(this);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "BOTTOM_MENU_TAG");
                break;
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void clickItem(int itemId) {

        switch (itemId) {
            case R.id.nav_menu__targets:
                FragmentHelper.changeFragment(TargetListFragment.newInstance(LOGIN),
                                              getSupportFragmentManager(),
                                              R.id.activity_main__container);

                break;
            case R.id.nav_menu__feedback:
                FragmentHelper.changeFragment(FeedBackFragment.newInstance(),
                                              getSupportFragmentManager(),
                                              R.id.activity_main__container);
                break;
        }
    }
}
