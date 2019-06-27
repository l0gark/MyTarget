package com.develop.loginov.mytarget.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.develop.loginov.mytarget.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

public class NavigationMenuDialog extends BottomSheetDialogFragment {

    OnNavigationItemClickListener onNavigationItemClickListener;

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
