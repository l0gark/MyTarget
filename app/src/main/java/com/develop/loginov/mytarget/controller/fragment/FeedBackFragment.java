package com.develop.loginov.mytarget.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.develop.loginov.mytarget.R;

public class FeedBackFragment extends DialogFragment {
    private static final String ORG_EMAIL = "l0gark@ya.ru";

    public static FeedBackFragment newInstance() {
        final Bundle args = new Bundle();
        final FeedBackFragment fragment = new FeedBackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public FeedBackFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getDialog() == null || getDialog().getWindow() == null){
            return;
        }
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().getWindow().setAttributes(p);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText textEmail = view.findViewById(R.id.fragment_feed_back__email);
        final EditText textMessage = view.findViewById(R.id.fragment_feed_back__message);
        view.findViewById(R.id.fragment_feed_back__send).setOnClickListener(v -> {
            if (TextUtils.isEmpty(textEmail.getText()) || TextUtils.isEmpty(textMessage.getText())) {
                Toast.makeText(getContext(), "Введите все данные!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidEmail(textEmail.getText())) {
                Toast.makeText(getContext(), "Некорректный email !", Toast.LENGTH_SHORT).show();
                return;
            }

            final String userEmail = textEmail.getText().toString();
            final String message = textMessage.getText().toString();


            final Intent intentEmail = new Intent(Intent.ACTION_SEND);
            intentEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{ORG_EMAIL});
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, userEmail);
            intentEmail.putExtra(Intent.EXTRA_TEXT, message);
            intentEmail.setType("message/rfc822");

            startActivity(Intent.createChooser(intentEmail, "Выберите email клиент :"));
        });

    }

    private static boolean isValidEmail(final CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
