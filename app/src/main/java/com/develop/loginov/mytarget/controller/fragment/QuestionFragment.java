package com.develop.loginov.mytarget.controller.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.loginov.mytarget.AnswerAdapter;
import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.dialog.HelpAnswerDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QuestionFragment extends Fragment {

    private NestedScrollView scrollView;
    private View view;
    private RecyclerView listView;
    private AnswerAdapter adapter;
    private FloatingActionButton fab;
    private Button buttonHelp;
    private Button buttonDone;

    private String question;

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        question = "Hello, i'm empty!";
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
        scrollView = view.findViewById(R.id.fragment_simple_question__nested_scroll_view);
        listView = view.findViewById(R.id.fragment_simple_question__list);
        fab = view.findViewById(R.id.fragment_simple_question__fab);
        buttonDone = view.findViewById(R.id.fragment_simple_question__button_done);
        buttonHelp = view.findViewById(R.id.fragment_simple_question__button_help);

        textQuestion.setText(question);
        adapter = new AnswerAdapter();
        String[] array = getResources().getStringArray(R.array.answers_sample);
        adapter.setAnswers(array);
        listView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(),
                                                                RecyclerView.VERTICAL,
                                                                false);
        lm.setAutoMeasureEnabled(true);
        listView.setLayoutManager(lm);
        listView.setNestedScrollingEnabled(false);
        listView.setHasFixedSize(true);

        buttonHelp.setOnClickListener(v -> new HelpAnswerDialog().show(getChildFragmentManager(),
                                                                       "Help"));
        buttonDone.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });


    }

    public void setAnswers(final String[] answers) {
        final String[] array = new String[answers.length];
        System.arraycopy(answers, 0, array, 0, answers.length);
        adapter.setAnswers(array);
    }

    public String[] getAnswers() {
        return adapter.getAnswers();
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
        scrollView.setVisibility(View.VISIBLE);
    }


    @SuppressLint("RestrictedApi")
    public void disActive() {
        show();
        fab.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

    }

    public void setEnabled(boolean enabled) {
        fab.setEnabled(enabled);
    }

}
