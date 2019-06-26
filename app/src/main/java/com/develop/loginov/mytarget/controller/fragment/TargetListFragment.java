package com.develop.loginov.mytarget.controller.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.develop.loginov.mytarget.R;

public class TargetListFragment extends Fragment {
    private static final String LOGIN_ARG = "LOGIN";
    private String login;

    public TargetListFragment() {
    }

    public static TargetListFragment newInstance(final String login) {
        TargetListFragment fragment = new TargetListFragment();
        Bundle args = new Bundle();
        args.putString(LOGIN_ARG, login);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            login = getArguments().getString(LOGIN_ARG);
        } else {
            login = "Пользователь не зарегистрирован!";
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView textLogin = view.findViewById(R.id.fragment_target_list__login);


        textLogin.setText(login);
    }

}
