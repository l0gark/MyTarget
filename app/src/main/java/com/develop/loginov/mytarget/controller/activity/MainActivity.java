package com.develop.loginov.mytarget.controller.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.controller.fragment.TargetListFragment;
import com.develop.loginov.mytarget.dialog.NavigationMenuDialog;
import com.develop.loginov.mytarget.helper.FragmentHelper;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MainActivity extends AppCompatActivity implements NavigationMenuDialog.OnNavigationItemClickListener {

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
        switch (item.getItemId()) {
            case R.id.app_bar_money:
                break;
            case R.id.app_bar_profile:
                break;
            case android.R.id.home:
                BottomSheetDialogFragment bottomSheetDialogFragment = new NavigationMenuDialog();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "BOTTOM_MENU_TAG");
                break;
        }
        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void clickItem(int itemId) {
        final String login = getResources().getString(R.string.login_sample);
        switch (itemId) {
            case R.id.nav_menu__targets:
                FragmentHelper.changeFragment(TargetListFragment.newInstance(login),
                                              getSupportFragmentManager(),
                                              R.id.activity_main__container);
                break;
            case R.id.nav_menu__connection:
                FragmentHelper.changeFragment(TargetListFragment.newInstance(login),
                                              getSupportFragmentManager(),
                                              R.id.activity_main__container);
                break;
            case R.id.nav_menu__question:
                FragmentHelper.changeFragment(TargetListFragment.newInstance(login),
                                              getSupportFragmentManager(),
                                              R.id.activity_main__container);
                break;
        }
    }
}
