package com.develop.loginov.mindsoul.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.develop.loginov.mindsoul.R;
import com.google.android.material.navigation.NavigationView;

public class NavigationMenuDialog extends DialogFragment {

    private OnNavigationItemClickListener onNavigationItemClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavigationView navigationView = view.findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            if (onNavigationItemClickListener != null) {
                onNavigationItemClickListener.clickItem(menuItem.getItemId());
                dismiss();
            }
            return true;
        });
    }

    public void setOnNavigationItemClickListener(
            final OnNavigationItemClickListener onNavigationItemClickListener) {
        this.onNavigationItemClickListener = onNavigationItemClickListener;
    }

    public interface OnNavigationItemClickListener {
        void clickItem(int itemId);
    }
}
