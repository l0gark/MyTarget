package com.develop.loginov.mindsoul.controller.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.develop.loginov.mindsoul.R;
import com.develop.loginov.mindsoul.adapter.TargetAdapter;
import com.develop.loginov.mindsoul.controller.activity.ResultActivity;
import com.develop.loginov.mindsoul.controller.activity.TargetActivity;
import com.develop.loginov.mindsoul.controller.application.App;
import com.develop.loginov.mindsoul.model.Target;

import java.util.ArrayList;
import java.util.List;

public class TargetListFragment extends DialogFragment {
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

        if(getDialog() == null || getDialog().getWindow() == null){
            Log.d("TARGET_LIST_TAG", "Dialog attrs aren`t setting");
            return;
        }
        Log.d("TARGET_LIST_TAG", "Dialog attrs are setting");
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().getWindow().setAttributes(p);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        final TextView textLogin = view.findViewById(R.id.fragment_target_list__login);
        final RecyclerView recyclerView = view.findViewById(R.id.fragment_target_list__recycler_view);

//        textLogin.setText(login);
        final List<Target> targets = new ArrayList<>();
        final RecyclerView.Adapter adapter = new TargetAdapter(targets, (position, v) -> {
            final Target target = targets.get(position);
            final Intent intent = new Intent(getContext(), ResultActivity.class);

            intent.putExtra(TargetActivity.TARGET_ARG, target.getName());
            intent.putExtra(TargetActivity.TARGET_ID_ARG, target.getId());
            intent.putExtra(ResultActivity.WINNER_ARG, target.getWinner());

            startActivity(intent);
            dismiss();
        });
        setDataToList(targets, adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                                                              RecyclerView.VERTICAL,
                                                              false));
    }

    private void setDataToList(final @NonNull List<Target> targets,
                               final @NonNull RecyclerView.Adapter adapter) {
        new Thread(() -> Target.getData(targets,
                                        App.getInstance().getDataBase().targetDAO(),
                                        adapter)).start();
    }
}
