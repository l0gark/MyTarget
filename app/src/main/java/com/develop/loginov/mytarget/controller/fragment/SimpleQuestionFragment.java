package com.develop.loginov.mytarget.controller.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.develop.loginov.mytarget.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SimpleQuestionFragment extends Fragment {
    private View view;
    private ListView listView;
    private FloatingActionButton fab;
    private Button buttonHelp;
    private Button buttonDone;

    private String question;
    private List<String> answers;

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        question = "Hello, i'm Vitya";
        if (context != null && attrs != null) {

            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                                                                   R.styleable.QuestionFragment);

            if (typedArray.hasValue(R.styleable.QuestionFragment_name)) {
                question = typedArray.getString(R.styleable.QuestionFragment_name);
            }

            typedArray.recycle();
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_question, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        final TextView textQuestion = view.findViewById(R.id.fragment_simple_question__name);
        listView = view.findViewById(R.id.fragment_simple_question__list);
        fab = view.findViewById(R.id.fragment_simple_question__fab);
        buttonDone = view.findViewById(R.id.fragment_simple_question__button_done);
        buttonHelp = view.findViewById(R.id.fragment_simple_question__button_help);

        textQuestion.setText(question);
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
        assert getContext() != null;
        if (listView != null) {
            final ListAdapter adapter = new ArrayAdapter<>(getContext(),
                                                           R.layout.simple_text_item,
                                                           answers);
            listView.setAdapter(adapter);
        }
    }

    public void setOnClickListener(final View.OnClickListener onClickListener) {
        if (onClickListener != null && fab != null) {
            fab.setOnClickListener(onClickListener);
        }
    }

    public void hide() {
        view.setVisibility(View.GONE);
    }

    @SuppressLint("RestrictedApi")
    public void show() {
        view.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    public void active() {
        show();
        fab.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        buttonDone.setVisibility(View.VISIBLE);
        buttonHelp.setVisibility(View.VISIBLE);
    }


    @SuppressLint("RestrictedApi")
    public void disactive() {
        show();
        fab.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        buttonDone.setVisibility(View.GONE);
        buttonHelp.setVisibility(View.GONE);
    }

    public void setEnabled(boolean enabled) {
        fab.setEnabled(enabled);
    }

}
