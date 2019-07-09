package com.develop.loginov.mytarget.dialog;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.loginov.mytarget.R;
import com.develop.loginov.mytarget.adapter.AnswerAdapter;

public class QuestionDialogFragment extends DialogFragment {

    private String[] answers;
    private AnswerAdapter adapter;
    private OnDoneClickListener onDoneClickListener;

    public QuestionDialogFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView listView = view.findViewById(R.id.fragment_question_dialog__list);
        Button buttonDone = view.findViewById(R.id.fragment_question_dialog__button_done);
        Button buttonHelp = view.findViewById(R.id.fragment_question_dialog__button_help);

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

        buttonHelp.setOnClickListener(v -> new HelpAnswerDialog().show(getChildFragmentManager(),
                                                                       "Help"));
        buttonDone.setOnClickListener(v -> {
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

   public interface OnDoneClickListener {
        void doneClick();
    }
}
