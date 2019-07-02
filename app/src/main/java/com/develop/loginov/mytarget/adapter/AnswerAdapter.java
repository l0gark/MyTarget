package com.develop.loginov.mytarget.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.loginov.mytarget.R;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    public static int text = 0;

    private static final int COUNT_ANSWERS = 5;
    private String[] answers;

    public AnswerAdapter() {
        answers = new String[COUNT_ANSWERS];
        for (int i = 0; i < COUNT_ANSWERS; i++) {
            answers[i] = "";
        }
    }

    public String[] getAnswers() {
        return answers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer, null, false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position >= COUNT_ANSWERS) {
            return;
        }

        holder.editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                holder.changeText(position);
                return false;
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return COUNT_ANSWERS;
    }

    public void setAnswers(String[] answers) {
        if (answers.length == COUNT_ANSWERS) {
            this.answers = answers;
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final EditText editText;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            editText = itemView.findViewById(R.id.item_answer__edit);
        }

        void changeText(int position) {
           final String text = editText.getText().toString();
            if (!TextUtils.isEmpty(text)) {
                answers[position] = text;
            }
        }
    }
}
