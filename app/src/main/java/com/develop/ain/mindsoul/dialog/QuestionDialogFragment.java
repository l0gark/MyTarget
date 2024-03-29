package com.develop.ain.mindsoul.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.ain.mindsoul.R;
import com.develop.ain.mindsoul.adapter.AnswerAdapter;

public class QuestionDialogFragment extends DialogFragment {
    private static final String TYPE_ARG = "TYPE";

    private String[] answers;
    private AnswerAdapter adapter;
    private OnDoneClickListener onDoneClickListener;
    private int resourceId;
    private TextView textQuestion;

    public QuestionDialogFragment() {
    }

    public static QuestionDialogFragment newInstance(final int resourceId) {
        Bundle args = new Bundle();
        args.putInt(TYPE_ARG, resourceId);
        QuestionDialogFragment fragment = new QuestionDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.resourceId = getArguments().getInt(TYPE_ARG, 0);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView listView = view.findViewById(R.id.fragment_question_dialog__list);
        final Button buttonDone = view.findViewById(R.id.fragment_question_dialog__button_done);
        final Button buttonHelp = view.findViewById(R.id.fragment_question_dialog__button_help);
        textQuestion = view.findViewById(R.id.fragment_question_dialog__name);

        adapter = new AnswerAdapter();
        if (answers != null) {
            adapter.setAnswers(answers);
        }
        listView.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(),
                                                                RecyclerView.VERTICAL,
                                                                false);
        listView.setLayoutManager(lm);
        listView.setNestedScrollingEnabled(false);
        listView.setHasFixedSize(true);

        changeText();
        buttonHelp.setOnClickListener(v -> HelpAnswerDialog.newInstance(resourceId).show(
                getChildFragmentManager(),
                "Help"));
        buttonDone.setOnClickListener(v -> {
            for (final String answer : answers) {
                if (answer == null || answer.trim().replace("\n", "").length() == 0) {
                    Toast.makeText(getContext(), "Введите все ответы!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (onDoneClickListener != null) {
                onDoneClickListener.doneClick();
            }
            dismiss();
        });
    }

    public void setOnDoneClickListener(final OnDoneClickListener onDoneClickListener) {
        this.onDoneClickListener = onDoneClickListener;
    }

    public void setAnswers(@NonNull final String[] answers) {
        if (adapter != null) {
            adapter.setAnswers(answers);
        }
        this.answers = answers;
    }

    private void changeText() {
        if (textQuestion != null) {
            switch (resourceId) {
                case R.array.hints1:
                    textQuestion.setText(R.string.question1);
                    break;
                case R.array.hints2:
                    textQuestion.setText(R.string.question2);
                    break;
                case R.array.hints3:
                    textQuestion.setText(R.string.question3);
                    break;
                case R.array.hints4:
                    textQuestion.setText(R.string.question4);
                    break;
            }
        }
    }

    public void setResourceId(final int resourceId) {
        this.resourceId = resourceId;
        changeText();
    }

    public interface OnDoneClickListener {
        void doneClick();
    }
}
